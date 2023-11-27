import '../ChatScreen.css';
import "./RightScreen.css"
import {convertToBase64} from "../Utils";
import {currentChatGlobal, setCurrentChatGlobal, user} from "../../logIn/LogIn";
import message from "./Message";

/**
 * The bottom part of the right part of chat screen from which the user can send messages.
 */
function ChatTextBox(props) {
    async function handleSendTextClick() {
        //Getting the message typed by the user
        let messageContent = document.getElementById("textBox").value;
        //If the user didn't type a message we won't send an empty string, Else we add the message to the chat.
        if (messageContent.trim() !== "") {

            try{

            // Asking from the contact to add the message to his conversation chat.
            var response = await fetch('http://' + props.chat.contact.server + '/api/transfer',
                {
                    method: "POST",
                    credentials: 'include',
                    headers: {'Content-Type': 'application/json'},
                    body: JSON.stringify({
                        from: props.user.id,
                        to: props.chat.contact.username,
                        content: messageContent
                    })
                })
            if (!(response.status === 201)) {
                return;
            }
        }
            catch(err) {
                var invalidUser = "invalid user!"
                document.getElementById("validation").innerHTML = invalidUser;
                return;
            }

            // Adding a new message to the conversation in the db.
            var response = await fetch('http://localhost:5170/api/contacts/' + props.chat.contact.username
                + '/messages',
                {
                    method: "POST",
                    credentials: 'include',
                    headers: {'Content-Type': 'application/json'},
                    body: JSON.stringify({
                        content: messageContent
                    })
                })
            if (!(response.status === 201)) {
                return;
            }
            var conversation = ""

            // Getting the current update conversation of the logged-in user with the contact.
            var response = await fetch('http://localhost:5170/api/Users/GetConversation',
                {
                    method: "POST",
                    credentials: 'include',
                    headers: {'Content-Type': 'application/json'},
                    body: JSON.stringify({
                        id: props.chat.contact.username
                    })
                })
            if (response.ok) {
                conversation = await response.json();
            }
            if (conversation != "") {
                //Appending the message to the end of the messages array.
                props.setChat(conversation)
            }
            var lastMessage = conversation.messages[conversation.messages.length - 1]
            //Clearing the chat message box.
            document.getElementById("textBox").value = "";
            //Signaling to the contact that he has a new message.
            if (props.connection.connectionStarted) {
                try {
                    await props.connection.invoke('HubNewMessage', user.id, props.chat.contact.username, lastMessage.id,
                        lastMessage.content, lastMessage.created, lastMessage.sent);
                } catch (e) {
                    console.log(e);
                }
            }
        }
    }

    return (
        <div className="bottom-input-line">
            {/*text box for writing messages*/}
            <textarea id="textBox" className="form-control-lg message-box" rows="1"
                      placeholder="Type a message"></textarea>

            {/*send text button*/}
            <button type="button" className="btn-lg btn-outline-secondary float-end bottom-btn"
                    onClick={handleSendTextClick}>
                <i className="bi bi-send"></i>
            </button>
        </div>
    )
}

export default ChatTextBox;