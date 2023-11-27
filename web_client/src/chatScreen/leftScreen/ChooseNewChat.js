
import AddNewChat from './AddNewChat';
import { useState } from "react";
import {user} from "../../logIn/LogIn";

/**
 * The function gives the option to add a new chat to the user's chats array.
 * @param props include the username of the user currently logged-in,
 * the array of conversations the user is having and a setter to this array.
 */
function ChooseNewChat(props) {
    
    /**
     * The function addNewContact creates a new conversation that the logged-in
     * user wants to have and sets the array of conversations adding this new conversation.
    */
    async function addNewContact() {
        // Keeps the new contact's username that the user has typed.
        var newContactUsername = document.getElementById("contactName").value;
        var newContactNickname = document.getElementById("contactNickname").value;
        var newContactServer = document.getElementById("contactServer").value;

        // Keeps new conversation between the log-in user to the contact the user has chose,
        // or null if there is no such user.
        var responsee = await AddNewChat({
            logInUsername: props.logInUsername,
            conversationsList: props.conversationsList,
            newContact: newContactUsername, relevantInfo: props.relevantInfo, currentListOfChats: props.currentListOfChats,
            user: props.user, newContactNickname: newContactNickname, newContactServer: newContactServer
        });
        // If there is no such user, return and don't create anything.
        if (responsee === "false") {
            return;
        }
        let chatsArr = [...props.currentListOfChats];

        // Asking for the contact to add us as a contact.
        try{
        var response = await fetch('http://'+newContactServer+'/api/Invitations',
        {
          method: "POST",
          credentials: 'include',
          headers: {'Content-Type': 'application/json'},
          body: JSON.stringify({
            from: props.logInUsername,
            to: newContactUsername,
            fromServer: "localhost:5170"
          })  
        })
    }
    catch(err) {
        var invalidUser = "invalid user!"
        document.getElementById("validation").innerHTML = invalidUser;
        return;
    }
        // If the user is not registered, announce it is invalid username.
        if(!response || !(response.status === 201)) {
            var invalidUser = "invalid user!"
            document.getElementById("validation").innerHTML = invalidUser;
            return;
        }

        // Adding the new contact as our contact in the db.
        var response = await fetch('http://localhost:5170/api/contacts',
        {
          method: "POST",
          credentials: 'include',
          headers: {'Content-Type': 'application/json'},
          body: JSON.stringify({
            id: newContactUsername,
            name: newContactNickname,
            server: newContactServer
          })
        })
        if(response.status !== 201) {
            return;
        } 
        var invalidUser = "user added successfully!"
        document.getElementById("validation").innerHTML = invalidUser;

        // getting the list of conversations of the current user.
        var response = await fetch('http://localhost:5170/api/Users/GetAllConversationsOfUser',
        {
          method: "POST",
          credentials: 'include',
          headers: {'Content-Type': 'application/json'},
          body: JSON.stringify({
            id: props.logInUsername,
          })
        })
        if(!response.ok) {
            return;
        }
        var conversations = await response.json();
        props.listRef.current=conversations
        // Setting the array of conversations to present the new chat on the scree.
        props.setCurrentListOfChats(conversations);
    }

    /**
    * The function deletes the input text that the user has written and the messsage
    * he has received.
    */
    function deleteInput() {
        document.getElementById("contactName").value = "";
        document.getElementById("contactNickname").value = "";
        document.getElementById("contactServer").value = "";
        document.getElementById("validation").innerHTML = "";
    }

    return (
        <div className="col-4 leftScreen">
            <div className="modal fade" id="add-new-contact" tabIndex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div className="modal-dialog">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title" id="exampleModalLabel">Add new contact</h5>

                            <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close" onClick={deleteInput}></button>
                        </div>
                        <div className="modal-body">
                            <div className="form-floating">
                                <input type="text" className="form-control newContact" placeholder="Leave a comment here" id="contactName" required></input>
                                <label htmlFor="contactName">Contact's username</label>
                            </div>
                            <br></br>
                            <div className="form-floating">
                                <input type="text" className="form-control newContact" placeholder="Leave a comment here" id="contactNickname" required></input>
                                <label htmlFor="contactNickname">Contact's nickname</label>
                            </div>
                            <br></br>
                            <div className="form-floating">
                                <input type="text" className="form-control newContact" placeholder="Leave a comment here" id="contactServer" required></input>
                                <label htmlFor="contactServer">Contact's server</label>
                            </div>
                            <div id="validation"></div>
                        </div>
                        <div className="modal-footer">
                            <button type="button" className="btn btn-secondary" data-bs-dismiss="modal" onClick={deleteInput}>Close</button>
                            <button type="button" className="btn btn-primary" onClick={addNewContact}>Add</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    );
}

export default ChooseNewChat;