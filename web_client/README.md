

# Client Side:

## **Running the project**	
The npm libraries which ware installed in this project ware React-Router-v6 and  npm i --save @microsoft/signalr.
//todooooooooooooooooooooooo - make sure signalr, downloading the project
- You can download the project using the commang `git clone https://github.com/TOMER-77/ap2-ex2.git`
- To run the project you can run the `npm start` command, which will open the client side. This will open the chat application on http://localhost:3000 to view in your browser.
- **There are 5 initial users signed-up to the chat. Their user names are: Ofek Koren, Tomer Eligayev, Avi Cohen, Shir Levi, Moti Luhim. The password of each one of them is "123456" followed by the first letter of their last name (in upper case).** 
- For example: the password of the user "Avi Cohen" is "123456C" .
- **To see all of our hard-coded db you can enter the "Services" directory in the chatWebApi project, and enter the  UserService file. The information is initialized in the constructor of the service.**

## **Explanation:**
Our program is a chat website, called "Best Web Chat!" which allows registered users to communicate with each other on our website, now adding them the option to keep their conversations as long as their servers are open. 

**Log-in screen:**
First, a log-in form is presented to the user asking for the user to log-in.
 - If the user has signed-up before, he fills his username and his password,  clicks on the "log-in" button and the chat screen is presented to him, enabling the user to continue chatting with his friends.
 - If the user hasn't signed-up before, he needs to click on the link to sign-up, which present him our second screen, the sign-up screen. 
 - We added the option to rate the app by clicking on the "rate us" link (explanation on the server side). 

**Sign-up screen:**
In this form the user is asked to fill a few details: First, the username he wants to have. The username will be his id in the chat system. Therefore the user must find username which isn't taken already by  an other signed-up user.
Moreover, the user is asked to pick a nickname and a password (and repeat it), and has the option to choose a profile image (but it is not required). In case that the user hasn't chose a profile image, a default profile image will be showed.
The password must contain at least 6 characters and must contain at least one character and one letter.
If all the fields have filled correctly then the user's chat screen is presented to the user.
We added also in this screen the option to rate the app by clicking on the "rate us" link. 

**Chat screen:**
The user will see his nickname and his profile picture (default picture in this exercise) on the top-left line.
- If the user has just registered, an empty window of chats will show up with the option to add a new chat with one of the signed-in users.
- If the user has signed-in, he will see all of the chats he was having before on the left and have the same option to add a new chat with one of the signed-in users.

In both cases, the user can see on the left all of the chats he is having, each chat holds the following information: the contact's nickname and profile image, the last message sent or received in the conversation the two where having (if there are any messages in the chat) and the time it was sent.
When clicking on one of the chats in the left screen, a conversation with the contact appears on the right screen (or an empty chat if it is a new conversation with the user). The user sees all of the messages in the conversation and is able to write a text message to his contact in the input text box. The conversation updates in real-time so that when a user receives a message from another user, he gets the message at the time it was sent to him.
