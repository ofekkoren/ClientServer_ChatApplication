
# Cross-platform Client-Server Real-time Messaging App :

## **About the project**	
- The project was developed with Android Studio (for the Android application) and MVC architecture (for the server side and ranks part).
- On the server side two Nuget packages were used: FirebaseAdmin and Microsoft.AspNetCore.SignalIR as well as Entity framework packages.
- The Android application was developed with API 31 using Room as local DB, Firebase Cloud Messaging for receiving notifications about new messages and chats, and Retrofit to send requests to the server.
- After downloading the project you can run the Android chat application by opening Android Studio, clicking on "file" and then "open" the folder in which you downloaded the project.
- The server side consists of two sub-projects and runs on two different ports:
-  - 5170 for the server side.
-  - 5189 for the rankings.
- **Each sub-project must run on the port stated above**
-  To examine the API server alone you are required to run the "chatWebApi" project as your startup project. 

-  To run the two sub-projects in parallel:
-   - Go to the *ChatApplciation* folder, and open in Visual Studio the solution on the project. In the solution explorer of the visual studio right click on the solution, click on properties, choose the option "Multiple startup projects" and choose both "ChatWebApi" (for server-side) and "ChatWebApp" (for Ranks) by changing the from "none" to "start" in the "Action" column.
- If you wish to check our Android client and current server with the web application of the chat from ex2 you can go to [the repository of ex2](https://github.com/TOMER-77/AP2-EX2) download ex2 as explained there, then go to the `clientSide` folder in the repository of ex2 and follow the instructions to run it. The web application should run on port 3000.
- When running the Android client with the server-side sub-projects together you can check the whole project. 
- Please run the server before running the Android or web applications.
- **Important!** If the database has problems and the application gets stuck after sending messages, adding new contacts, or trying to reach the ranks page please follow the next steps:
- - Click on "Tools", then click on "NuGet Package Manager" and then click on "Package Manager Console"
- - In the "Default project" option in the Package Manager Console choose `ChatWebApi` if the problem is with the server of `ChatWebApp` if the problem is with ranks. If the problem occurs in both cases repeat the next steps for both of the projects.
- - Type the next commands in the Package Manager Console:
- - - `EntityFrameworkCore\Add-Migration Testing` 
- - - `EntityFrameworkCore\Update-Database`
- - This shouldn't be happening but we write this just in case.
- A few notes about the Android application. When receiving a new message in a chat you currently looking on, the chat won't automatically scroll down, to view the message you just need to scroll down the chat. You will be able to notice that a new message was sent by a notification that will be displayed in the notifications bar and by a short toast message that will pop up at the bottom of the screen. Also when a new chat is added to your chat list (whether after adding a contact or being added by another contact) the chat will appear at the bottom of your chat list. For further explanations about the Android chat application please to the [app](https://github.com/ofekkoren/ap2-ex3/tree/main/app) directory in this repository.
- To check the ranks part please run the web application as explained above. The ranks are reachable from the web application.

**It is important for us to mention that we assumed that username is a global identifier for a user or contact (as we were asked in the first exercise). Therefore, while trying to add a new contact, if the user already has a contact with the same username (but might be from a different server), the action fails.**
