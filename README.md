# Advanced Programming exercise 3:
Submitting:

Tomer Eligayev, id: 208668129

Ofek Koren, id: 207105933

## **About the project**	
- The project was developed with Android Studio (for the android application) and MVC architecture (for the server side and ranks part).
- On the server side two nuget packages were used: FirebaseAdmin and Microsoft.AspNetCore.SignalIR ans well as Entity framework packages.
- The Android application was devolped with API 31 using Room as local DB , Firebase Cloud Messaging for receiving notifications about new messages and chats and Retrofit to send requests to the server.- You can download the project using the command `git clone https://github.com/ofekkoren/ap2-ex3.git`
- After downloading the project you can run the android chat application by opening Android studio, clicking on "file" and then "open" the folder where you downloaded the project to.
- The server side consists of two sub-projects and runs on two different ports:
-  - 5170 for the server side.
-  - 5189 for the rankings.
- **Each sub-project must run on the port stated above**
-  To run the two sub-projects in parallel:
-   - Go to the *ChatApplciation* folder, open in visual studio the solution on the project. In the solution explorer of visual studio right click on the solution,click on properties,choose the option "Multiple startup projects" and choose both "ChatWebApi" (for server side) and "ChatWebApp" (for Ranks).
- If you wish to check our android client and current server with the web application of the chat from ex2 you can go to [the repository of ex2](https://github.com/TOMER-77/AP2-EX2) download ex2 as explaind there, then go to the `clientSide` folder in the reopository of ex2 and follow the instructions to run it. The web application should run on port 3000.
- When running the android client with the server side sub-projects together you can check the whole project. 
- Few notes about the android application. When receiving a new message in a chat you currently looking on, the chat won't automatically scroll down, to view the message you just need to scroll down the chat. You will be able to notice that a new message was sent by a notification that will be displayes in the notifications bar and by a short toast messae that will pop up in the bottom of the screen. Also when a new chat is added to your chat list (whether after adding a contact or being added by other contact) the chat will appear in the bottom of your chat list. For further explanations about the android chat application please to the [app](https://github.com/ofekkoren/ap2-ex3/tree/main/app) directory in this repository.
- To check the ranks part please run the web application as explained above. The ranks are reachble from the web application.

**It is important for us to mention that we assumed that username is a global identifier for a user or contact (as we were asked in the first exercise). Therefore, while trying to add a new contact, if the user already has a contact with the same username (but might be from a different server), the action fail.**
