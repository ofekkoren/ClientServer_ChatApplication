import '../ChatScreen.css';
import "./Message.css"
import {getFormattedDateString} from "../Utils";
import React from "react";

/**
 * A component of a single chat message.
 * @param props contains the information about a message in the chat
 */
function Message(props) {
    return (
        <div
            className={"general-message " + (props.message.sent === true ? 'sent-message' : 'received-message')}>
            <h6 className="text-message" key={props.index}>{props.message.content}</h6>
            <span className="text-date" key={props.index}>{getFormattedDateString(props.message)}</span>
        </div>
    )
}

export default Message;