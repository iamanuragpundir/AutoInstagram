
# Insta-Bot

The project automates an Instagram chat with a targeted user till the next person send "bye"/"Bye" message. Automatic replies are fetched through an API call to chatbot API.



## Changes to be made

The project is build using maven and hence maven directory structure is being followed.
Make sure the user you want to chat with is in your follower list or messaging that person is not disabled due to privacy settings.

![insta-bot-resources](https://user-images.githubusercontent.com/57711764/128329643-998c08c9-8f93-4d77-8b98-b63fa9c31ced.PNG)
```bash
  Goto insta-bot/src/main/java/Resources.java
  enter username of the account which will behave as a bot
  enter password of that account
  enter the username of the person to chat with.
```
    
## Run Locally

Clone the project

```bash
  git clone https://link-to-project
```

Go to the project directory

```bash
  cd my-project
```

Install dependencies

```bash
  sudo apt update
  sudo apt install maven
```

Compile

```bash
  mvn clean compile
```
Run Application

```bash
  cd target/classes
  java AutoInstagram
```
  
