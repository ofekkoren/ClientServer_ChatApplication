import './LeftChatItem.css';
import React from 'react';


/**
 * The function represent a left chat item including the relevant information
 * needs to be presented in the item, such as the last message in the conversation,
 * the time it was sent, the nickname of the user in chat and his profile image.
 * @param props include the username of the user currently logged-in,
 * the array of conversations the user is having and a setter to this array.
 */
function LeftChatItem(props) {
    var lastMessagePresented = "";
    var iconClassName = "";

    lastMessagePresented = props.conversation.lastMessage;

    /**
    * The function sets the current conversation as lons as it is a different
    * conversation than the current chat the user is at.
    * @param chat represent the chat the user is on right now.
    */
    function showChat(chat) {
        if (chat !== props.refer.current) {
            props.refer.current = chat;
            props.setChat(chat);
        }
    }

    return (
        <div className='left-chat-item' onClick={() => showChat(props.chat)}>
            <div className='left-image'>
                <img src={props.conversation.image} className="chat-profile-image"></img>
            </div>
            <div className='mid-item'>
                <div>
                    <span className="chat-member-name">{props.conversation.nicknameInChat}</span>
                    <div className='last-message-time'>{props.conversation.time}</div>
                </div>
                <div className="chat-last-message">{lastMessagePresented}&nbsp;
                    <i className={iconClassName}></i>
                </div>
            </div>
        </div>
    );
}
export default LeftChatItem;