import '../ChatScreen.css';
import "./RightScreen.css"
import ChatTextBox from "./ChatTextBox";
import ChatHeader from "./ChatHeader";
import Message from "./Message";

/**
 * The body of the chat. It consists of: A header which contain the image and nickname of the user we chat with, A chat body
 * which displays the messages sent in the current chosen chat , A text box to send new messages.
 * @param props include the information of the user currently logged-in and the current chosen conversation.
 */
function RightScreen(props) {

    //If no chat was chosen by the user, no name or image will or message box will be displayed.
    if (props.chat === "") {
        return (
            <div className="col rightScreen">
                <ChatHeader chatWith={""}></ChatHeader>
            </div>
        )
    }

    //Checking which of the chat participants is the signed-in user. The second participant is the user we chat with.
    else {
        return (
            <div className="col rightScreen">
                <ChatHeader chatWith={props.chat.contact}></ChatHeader>
                <div className="chat-body" id="chatBody">
                    {props.chat.messages.map((message, index) => (
                        <Message key={index} user={props.user} message={message}></Message>
                    ))}
                    <div className="chat-body-bottom" id="lastMessage"></div>


                </div>
                <ChatTextBox chat={props.chat} setChat={props.setChat} connection={props.connection}
                             sendingUser={props.user} user={props.user}></ChatTextBox>
            </div>
        )
    }
}

export default RightScreen;

