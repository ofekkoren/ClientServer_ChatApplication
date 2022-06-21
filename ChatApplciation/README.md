
# Advanced Programming exercise 2:
Submitting:

Tomer Eligayev, id: 208668129

Ofek Koren, id: 207105933

## **Running the project**	
Our project is divided into two sub-projects, "chatWebApp" project and "chatWebApi" project.
-  To examine the ranks part of the exercise you are required to and run the "chatWebApp" project as your startup project. 
- To examine the api server (part 3 of the exercise) you are required to and run the "chatWebApi" project as your startup project. 
- **There are 5 initial users signed-up to the chat. Their user names are: Ofek Koren, Tomer Eligayev, Avi Cohen, Shir Levi, Moti Luhim. The password of each one of them is "123456" followed by the first letter of their last name (in upper case).** 
- For example: the password of the user "Avi Cohen" is "123456C" .
- **To see all of our hard-coded db you can enter the "Services" directory in the chatWebApi project, and enter the  UserService file. The information is initialized in the constructor of the service.**
- It is important to mention that each service we user is initialized only once overall, although it might be seems differently. We define it in the program.cs file in the chatWebApi project.


## **Server Side:**

**ChatWebApp project:**
In this project you will see our ranks code, required for part 3 of this exercise.
To reach this screen on the app you can click on the "rate us" link on the log-in or sign-up screens.
In this screen the user will see all the ranks to our app, the time each rank was sent, it feedback and the user who've sent it. The user will also be exposed to the average rank of our app. Moreover, he/she will have the option to look for a rank in the existing ranks list, or to add a new review for our app. Each user can also edit or delete any review he/she wants.
- The user have the option to search for ranks according to the content of the rank.
- If the user want to add a new review he is required to enter his name, the rank for our app (a number between 1-5 only) and has the option to add feedback (a non-required field).
- Each user can rank the app only once (with the same username).

From the ranks screen the user will have the option to go back to the log-in or the sign-up screens.

**chatWebApi project:**

In this project you will meet our server and will be managed to see our DB - which for this exercise is a hard-coded data-base initialized as mentioned before at "Services" directory, in the "UserService " file.
**It is important for us to mention that we assumed that username is a global identifier for a user or contact (as we were asked in the first exercise). Therefore, while trying to add a new contact, if the user already has a contact with the same username (but might be from a different server), the action will be failed.**
Our DB is keeps few models:
- User model - contains the id (username) of a user, his name (nickname), password and list of conversations.
- Conversation model - contains id of the conversation (appending the two id's - the user's id first and the contact's id second), a list of messages and a field of contact.
- Message model - contains the message id (a number), content, created (the time the message was sent), and sent field (boolean field keeps the value true if the user was sending the message and false otherwise),
- Contact model - contains id (a number), username, name, server (the sever the contact is logged-to), last (the last message was sent in the conversation of the user and the contact), and lastdate (the time the last message was sent).
- ContactToJson model - uses us for sending information according to the required api in part 3 of the exercise.

We also have three services - contact service, user service and conversation service, each allows us to reach the db and commit some actions in it, or pulling data from it.

We have 5 controllers: the contact controller, transfer and invitations controllers are api controllers holding the requests according to the api required from us in part 3 of the exercise. The controllers hold the services as fields so they can reach data from the DB or apply changes on the data without knowing the inner logic of communication with the DB.
The LogIn and SignUp controllers are both manage logging into the website and signing up for the website respectively. Each controller receives the fields from the forms screens in the website, and does validation checks. If the user has entered valid fields in the log-in form than we set with session the current user to be the logged-in user, and correspondingly if the user has entered valid fields in the sign-up screen.
The user controller uses us for passing information from the db to react and vise versa.

From now on, we make sure the user does actions only if he/she is logged into the website, otherwise he/she will have to log-in again. 

We also allow the users on the website to write to eachother and recieve messages in real time using signalIR.
