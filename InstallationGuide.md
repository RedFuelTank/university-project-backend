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
url: `https://gitlab.cs.ttu.ee/`

token: `-9sxx7VfH59rFysAYjTA`
##### Frontend
url: `https://gitlab.cs.ttu.ee/`

token: `pYdwQoBtjp99bYxBxoZL`

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

Now the gitlab-runner will be able to use lordi service.