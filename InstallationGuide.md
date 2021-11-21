## Initial setup
As a server we are using Ubuntu Server 20.04 LTS virtual machine on Amazon Web Services. All the following commands are supposed to be executed on the server, unless it is specifically stated otherwise. 

## Connect to the server
In order to continue you have to be able to connect to the server, meaning you have to have your public SSH key registered within the server prior to this. Once it is registered, you can continue. 

### Connect on Linux
In order to connect to the server on Linux you can use the following command in terminal 
```bash
ssh ubuntu@13.48.43.39
```

### Connect on Windows
In order to connect to the server on Windows you will have to install [PuTTY](https://www.chiark.greenend.org.uk/~sgtatham/putty/). Once it is installed, you can use internal tool called **PuTTYgen** in order to generate new SSH key pair or to convert the existing private key (.pem file) to the format required by **PuTTY** (.ppk file). 

Once you have your private key in the required format, you can access the server through **PuTTY**. 
1. Start PuTTY
2. In **Category** pane **Session** should be selected. There in **Public DNS** field you should insert `ubuntu@13.48.43.39`. Make sure the **Port** number is `22`. **Connection type** has to be `SSH`.
3. In **Category** pane expand **Connection**, then **SSH**, select **Auth**. There find **Private key file for authentication** field, click on the **Browse** button next to it and find your private key file (.ppk), then **Open**. 
4. (Optional) You should save your session for the future so that you would not need to do all of this again. In **Category** pane go back to **Session**, find **Saved sessions** field, type a name and press **Save**. After you can load this session quickly.
5. Press **Open** in **Session**, this will connect you to the server.

More detailed guide can be found [here](https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/putty.html).

## Allocating virtual memory
Angular builds can be very taxing on the server, therefore we need to allocate some space for virtual memory. 2 GB would be enough. You can use `htop` command in order to get idea of how much virtual memory the server currently has - it is indicated as **Swp** - swap space.

To create swap space we first need to create a swapfile with 2 GB size using following command
```bash
sudo fallocate -l 2G /swapfile
```

We should allow only root to read and write to the file, with this command
```bash
sudo chmod 600 /swapfile
```

Next we tell Linux it can use this file as swap space with following command
```bash
sudo mkswap /swapfile
```

Now we have to tell Linux to actually use it
```bash
sudo swapon /swapfile
```

At this point it is done, you can check with the following command if the swap space is correct
```bash
swapon --show
```

The only problem remaining is that after reboot this will not be saved, the Linux will revert to previous settings. To fix that, use the following command
```bash
echo '/swapfile none swap sw 0 0' | sudo tee -a /etc/fstab
```

Now everything is done, and you can safely proceed with the rest of the setup.

## Gitlab runner
For the server to be able to get code from the repository, it needs a Gitlab runner, only one will process both Frontend and Backend.
### Install Gitlab runner
First you will have to download the files using following command
```bash
curl -LJO "https://gitlab-runner-downloads.s3.amazonaws.com/latest/deb/gitlab-runner_amd64.deb"
```
You can then use the following command to install it on the server
```bash
dpkg -i gitlab-runner_amd64.deb
```

### Register runners
For this you will need to get **url** and **token** from both frontend and backend repositories. For each respective repository go to **Settings**, select **CI/CD** there, find **Runners** tab and Expand it. There you will find both url and token, which should be as follows:

##### Backend
* url: `https://gitlab.cs.ttu.ee/`
* token: `-9sxx7VfH59rFysAYjTA`
##### Frontend
* url: `https://gitlab.cs.ttu.ee/`
* token: `pYdwQoBtjp99bYxBxoZL`

You will have to run the following command twice, once for Backend and once for Frontend. Use the appropriate url and token for each. It will also ask for:
* description - enter info which repository this is
* tags - enter a tag to be used later to use specific runner
* executor - enter `shell`

### Configuring CI in repositories
For the server to automatically deploy new versions after commit to main branch, we need to add CI files to both repositories. Add them to the project root. Filename `.gitlab-ci.yml`.

##### Backend file content
```yaml
stages:
  - build
  - test
  - deploy

before_script:
  - export GRADLE_USER_HOME=`pwd`/.gradle

build lordi:
  stage: build
  cache:
    paths:
      - .gradle/wrapper
      - .gradle/caches
  artifacts:
    paths:
      - build/libs
  tags:
    - lordi-test
  script:
    - ./gradlew assemble

test heroes:
  stage: test
  tags:
    - lordi-test
  script:
    - ./gradlew check

deploy lordi:
  stage: deploy
  only:
    refs:
      - main
  tags:
    - lordi-test
  script:
    - mkdir -p ~/api-deployment # mkdir make folder api-deployment ~/ is under current user directory so for gitlab it would be /home/gitlab/api-deployment
    - rm -rf ~/api-deployment/* # rm remove -rf is recursive files from api-deployment
    - cp -r build/libs/. ~/api-deployment # cp - copy build/libs is where
    - sudo service lordi restart  # this requires sudo rights for gitlab user
```

##### Frontend file content
```yaml
stages:
  - build
  - deploy

build lordi:
  stage: build
  image: node:12-alpine
  cache:
    paths:
      - node_modules
  artifacts:
    paths:
      - dist
  tags:
    - lordi-test
  script:
    - npm install
    - npm run build

deploy lordi:
  stage: deploy
  only:
    refs:
      - main
  tags:
    - lordi-test
  script:
    - mkdir -p ~/front-deployment
    - rm -rf ~/front-deployment/*
    - cp -r dist/iti0302-lordi-frontend/. ~/front-deployment
```

At this point both will not work, as the server does not yet have **node.js** and **Java**. In backend CI file we are also referring to a service, that doesn't yet exist (**lordi**). Next step is to install them, create lordi service and allow gitlab-runner to use it.

## Install Java for backend
First we need to get all the info for available updates for the Ubuntu server using following command
```bash
sudo apt-get update
```
Next we actually have to upgrade our server with the following command
```bash
sudo apt-get upgrade
```
Finally, we can install the Java itself with this command
```bash
sudo apt-get install openjdk-11-jre openjdk-11-jdk
```

## Install Node.js
Firstly we once again have to get the required files for installation with the following command
```bash
sudo curl -sL https://deb.nodesource.com/setup_14.x | sudo -E bash -
```
And then we can actually install it with this
```bash
sudo apt-get install -y node.js
```

## Create lordi service, aka Define backend as Linux service
First we have to create the service file. For this - navigate to `system` directory using this command
```bash
cd /etc/systemd/system/
```
and there create the file
```bash
sudo touch lordi.service
```

Use the following command to edit the file
```bash
sudo nano lordi.service
```

and edit its contents to this

```
[Unit]
Description=lordi service
After=network.target

[Service]
Type=simple
User=gitlab-runner
WorkingDirectory=/home/gitlab-runner/api-deployment
ExecStart=/usr/bin/java -jar lordi-backend.jar
Restart=on-abort

[Install]
WantedBy=multi-user.target
```

Now we need to reload the configuration with the following command
```bash
sudo systemctl daemon-reload
```
and enable the service
```bash
sudo systemctl enable lordi
```

This will allow to start backend as a Linux service, it will also automatically restart once the server is rebooted. Since we are using the same service in backend **.gitlab-ci.yml**, the **deploy** stage will be able to start the backend. 

To check the service status you can use this command
```bash
sudo service lordi status
```

### Allow gitlab-runner to use this service
Currently, only root user can use this service, but we need to allow the gitlab-runner user to do so. Go into config with this command
```bash
sudo visudo
```
and in the end of the file add the following line
```bash
gitlab-runner ALL = NOPASSWD: /usr/sbin/service lordi *
```

Now the gitlab-runner will be able to use **lordi** service.

## Setup nginx 

Nginx is a web application which we will be using for our server. 

### Install nginx
You can install nginx to the server with the following command
```bash
sudo apt-get install nginx
```

Now we will be able to access our website using or public IPv4 - http://13.48.43.39/ - but as nginx is not currently configured, it will be a placeholder page.

For easier editing the config you should go to the directory where it is located with this command
```bash
cd /etc/nginx/sites-available
```
start editing it with following command
```bash
sudo nano default
```
and removing all the comments.

### Connect the frontend
The **nginx** will operate within `/var/www`, so we will create a symlink from our frontend directory to this one using the following command 
```bash
sudo ln -s /home/gitlab-runner/front-deployment/ /var/www/front-deployment
```
Now we can change the location of the root in the **nginx** config. Once again, navigate to `/etc/nginx/sites-available`, edit `default` and change root there from `root /var/www/html;` to `'`root /var/www/front-deployment;`.

After the change in the config we will have to restart the service. In the future it will have to be restarted after every config change with this command
```bash
sudo service nginx restart
```

http://13.48.43.39/ should now display our frontend

### Fixing breaking URLs on Frontend
The **nginx** will view whatever is after the / sign as a file to be accessed, and there are no such files, meaning if we will try using, for example, http://13.48.43.39/api/requests, we will get an error. To fix this we have to replace old `location /` in the **nginx** config file with this new one
```bash
    location / {
        index  index.html index.htm;
        if (!-e $request_filename){
          rewrite ^(.*)$ /index.html break;
        }
    }
```

### Backend reverse-proxy
For us to be able to access backend without specifying its port, we will have to add a reverse-proxy to the **nginx** config file. Note, that the following block will have to be located **before** the previous one in the file, as both are responsible for routing, and otherwise the first one will cover all the cases, including those that we are adding right now, effectively overriding it.
```bash
        location /api/ {
            proxy_pass   http://localhost:8080;
        }
```

### Final configuration file
As a result, the final configuration file should look like this
```bash
server {
        listen 80 default_server;
        listen [::]:80 default_server;

        root /var/www/front-deployment;

        server_name _;

        location /api/ {
            proxy_pass   http://localhost:8080;
        }

        location / {
            index  index.html index.htm;
            if (!-e $request_filename){
              rewrite ^(.*)$ /index.html break;
            }
        }
}
```

## Domain
To get a domain we used https://www.freenom.com/ service. It does not have a way to manually register a new user, so you have to actually register a domain first and in the process it will create a new user.

Sadly, website has a bug. If we check is a domain available, it will display a bunch of options, but if we will try to click on them, they will appear taken. 

To get around this issue we will have to enter full domain - not just `letotherrandomsdoit`, but, for example, `letotherrandomsdoit.ga` - this will actually allow us to take the domain and use it. 

After we've done this, we will have to register domain in the **AWS Route 53** by creating a hosted zone there with the domain name we've got. After we would need to create a new record there, of an **A type**, with the value being our public IPv4. 

Now we go back to https://www.freenom.com/ and in our cabinet in **Services** select **My Domains**, there find the required domain and press **Manage Domain**, there open **Management Tools**, **Nameservers**, select custom nameservers and write down them from Hosted zone we created in **AWS Route 53**, from the **NS** type record. 

After saving the changes we should be able to access our website with its domain name:
https://letotherrandomsdoit.ga/

## HTTPS setup
First we need to install certification bot using this command
```bash
sudo apt-get install python3-certbot-nginx
```

Next we have to get our nginx certified with the following command
```bash
sudo certbot --nginx
```
During the certification we will have to enter our email, agree to ToS and make a decision to share or not to share our email. After we would need to enter our domain name - `letotherrandomsdoit.ga` and finally decide whether to redirect HTTP traffic to HTTPS. We certainly want this, so option `2` should be selected.

After the installation is completed, the website should use HTTPS.