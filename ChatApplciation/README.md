

# Advanced Programming exercise 2:
Submitting:

Tomer Eligayev, id: 208668129

Ofek Koren, id: 207105933

## **Running the project**	
Our project is divided into two sub-projects, "chatWebApp" project and "chatWebApi" project.
-  To examine the ranks part alone you are required to and run the "chatWebApp" project as your startup project. 
- To examine the api server alone you are required to and run the "chatWebApi" project as your startup project. 
-  To run the two sub-projects in parallel:
-   - Go to the *ChatApplciation* folder, open in visual studio the solution on the project. In the solution explorer of visual studio right click on the solution,click on properties,choose the option "Multiple startup projects" and choose both "ChatWebApi" (for server side) and "ChatWebApp" (for Ranks).
- To check the project please sign up with new users to the chat.
- **Important!** if the database does problems and the application gets stuck after sending messages ,adding new contacts or trying to reach the ranks page please follow the next steps:
- - Click on "Tools", then click on "NuGet Package Manager" and then click on "Package Manager Console"
- - In "Default project" choose `ChatWebApi`if the problem is with the server of `ChatWebApp` if the problem is with ranks. If the problem occurs in both cases repeat the next steps for both of the projects.
- - Type the next commands in the Package Manager Console:
- - - `EntityFrameworkCore\Add-Migration Testing` 
- - - `EntityFrameworkCore\Update-Database`
- - This shouldn't be happening but we write this just in case.


## **Server Side:**
**chatWebApi project:**

In this project you will meet our server and will be managed to see our DB - which for this exercise is a hard-coded data-base initialized as mentioned before at "Services" directory, in the "UserService " file.
**It is important for us to mention that we assumed that username is a global identifier for a user or contact (as we were asked in the first exercise). Therefore, while trying to add a new contact, if the user already has a contact with the same username (but might be from a different server), the action will be failed.**
Our DB is keeps few models:
- User model - contains the id (username) of a user, his name (nickname), password and list of conversations.
- Conversation model - contains id of the conversation (appending the two id's - the user's id first and the contact's id second), a list of messages and a field of contact.
- Message model - contains the message id (a number), content, created (the time the message was sent), and sent field (boolean field keeps the value true if the user was sending the message and false otherwise),
- Contact model - contains id (a number), username, name, server (the sever the contact is logged-to), last (the last message was sent in the conversation of the user and the contact), and lastdate (the time the last message was sent).
- ContactToJson model - uses us for sending information according to the required api in part 3 of the exercise.
- A model which maps a user to it's firebase token.

We also have four services - contact service, user service , conversation service and FirebaseToken service. each allows us to reach the db and commit some actions in it, or pulling data from it.

We have 5 controllers: the contact controller, transfer and invitations controllers are api controllers holding the requests according to the api required from us in part 3 of the exercise. The controllers hold the services as fields so they can reach data from the DB or apply changes on the data without knowing the inner logic of communication with the DB.
The LogIn and SignUp controllers are both manage logging into the website and signing up for the website respectively. Each controller receives the fields from the forms screens in the website, and does validation checks. If the user has entered valid fields in the log-in form than we set with session the current user to be the logged-in user, and correspondingly if the user has entered valid fields in the sign-up screen.
The user controller uses us for passing information from the db to react and vise versa.

From now on, we make sure the user does actions only if he/she is logged into the website, otherwise he/she will have to log-in again. 

We also allow the users on the website to write to eachother and recieve messages in real time using signalIR for the web application and Firebase Cloud Messages for the android application.
