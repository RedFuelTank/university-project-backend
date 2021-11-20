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

#### Backend
url: `https://gitlab.cs.ttu.ee/`

token: `-9sxx7VfH59rFysAYjTA`
#### Frontend
url: `https://gitlab.cs.ttu.ee/`

token: `pYdwQoBtjp99bYxBxoZL`

You will have to run the following command twice, once for Backend and once for Frontend. Use the appropriate url and token for each. It will also ask for:
* description - enter info which repository this is
* tags - enter a tag to be used later to use specific runner
* executor - enter `shell`

### Configuring CI in repositories

