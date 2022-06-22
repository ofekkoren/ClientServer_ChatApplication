
# Client Side:

## **Running the project**	

- The Android application was devolped with API 31 using Room as local DB , Firebase Cloud Messaging for receiving notifications about new messages and chats and Retrofit to send requests to the server.
- After downloading the project you can run the android chat application by opening Android studio, clicking on "file" and then "open" the folder where you downloaded the project to.


## **Explanation:**
This android chat application allows registered users to communicate with each other on our application. It is also possible to communicate with users of the web version of the application (It is explained in the main readme of the project)

**Log-in screen:**
First, a log-in form is presented to the user asking for the user to log-in.
 - If the user has signed-up before, he fills his username and his password,  clicks on the "log-in" button and the chat screen is presented to him, enabling the user to continue chatting with his friends.
 - If the user hasn't signed-up before, he needs to click on the link to sign-up, which present him our second screen, the sign-up screen. 

**Sign-up screen:**
In this form the user is asked to fill a few details: First, the username he wants to have. The username will be his id in the chat system. Therefore the user must find username which isn't taken already by  an other signed-up user.
Moreover, the user is asked to pick a nickname and a password (and repeat it), and has the option to choose a profile image (but it is not required). In case that the user hasn't chose a profile image, a default profile image will be showed.
The profile picture is stored locally for the user and it will not be displayed for other users.
The password must contain at least 6 characters and must contain at least one character and one letter.
If all the fields have filled correctly then the user's chat screen is presented to the user.


**Contacts list screen + Conversation screen:**
The user will see his nickname and his profile picture (default picture if he didn't chose a picture) on the topline.
- If the user has just registered, an empty window of chats will show up with the option to add a new chat with one of the signed-in users.
- If the user has signed-in, he will see all of the chats he was having and will have the option to add a new chat.

For every contact of the current user the contacts list holds the following information: the contact's nickname and default profile image, the last message sent or received in the conversation the two where having (if there are any messages in the chat) and the time it was sent.
When clicking on one of the chats  a conversation with the contact appears . The user sees all of the messages in the conversation and is able to write a text message to his contact in the input text box. The conversation updates in real-time so that when a user receives a message from another user, he gets the message at the time it was sent to him.
When receiving a new message in a chat that the user is currently looking on, the chat won't automatically scroll down, to view the message the user need to scroll down the chat. The user will be able to notice that a new message was sent by a notification that will be displayes in the notifications bar and by a short toast messae that will pop up in the bottom of the screen. Also when a new chat is added to the user's chat list (whether after adding a contact or being added by other contact) the chat will appear in the bottom of your chat list.