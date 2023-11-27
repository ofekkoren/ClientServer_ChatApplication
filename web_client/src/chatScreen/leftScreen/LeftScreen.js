import React, {useEffect} from 'react';
import '../ChatScreen.css';
import './LeftScreen.css';
import './leftChatItem/LeftChatItem.css';
import LeftChatItem from './leftChatItem/LeftChatItem';
import ChooseNewChat from './ChooseNewChat';
import {getFormattedDateString} from "../Utils";
import { profileImage } from '../../signUp/SignUp';

/**
 * The left side of the chat which holds the list of chats the user is having.
 * @param props include the information of the user currently logged-in, the current chosen
 * conversation, the list of conversations the user is having and a setter to this list.
 */
function LeftScreen(props) {
    // Chats holds all the conversations of the current log-in user.
    var chats = props.currentListOfChats;
    // Keeping the current log-in user's profile image.
    var logInUserImage = profileImage;
    var relevantInfo = [];

    /*
     * For each conversation the current log-in user is having, we create the information
     * needed to be presented on the left side bar, including the contact's name, his profile picture,
     * the last message has been sent in the conversation and the time it was delivered.
    */
    for (var i = 0; i < Object.keys(chats).length; i++) {
        var usernameInChat = "";
        var nicknameInChat = ""
        var lastMessage = "";
        var message = "";
        var image;
        var type = "";

        usernameInChat = chats[i].contact.username;
        nicknameInChat = chats[i].contact.name;
        image = profileImage;

        // If there are chats to persent, update their information.
        if ((chats[i].messages.length)!=0) {
            if (chats[i].messages[chats[i].messages.length - 1].content != null) {
                lastMessage = chats[i].messages[chats[i].messages.length - 1].content;
            }
            type = "text";
            message = chats[i].messages[chats[i].messages.length - 1];
        }
        relevantInfo.push({ nicknameInChat: nicknameInChat, usernameInChat: usernameInChat, type: type, lastMessage: lastMessage, time: getFormattedDateString(message), image: image });
    }

    // Keeps the list of conversations that the logged-in user is having.
    var conversationsList;        
    // Mapping components of LeftChatItem with the relevant information they are needed.
    conversationsList = props.currentListOfChats.map((conversation, index) => {
        return <LeftChatItem conversation={relevantInfo[index]} key={index} chat={chats[index]} currentConversation={props.currentConversation} refer={props.refer} setChat={props.setChat} />
    });
    return (
        <div className="col-4 leftScreen">
            <div className="topLine">
                <img src={logInUserImage} className="float-start top-left-profile-image"></img>
                <h5 className='top-left-username'>{props.user.name}</h5>
                <button className="bi bi-person-plus-fill add-conversation ms-3" data-bs-toggle="modal" data-bs-target="#add-new-contact"></button>
            </div>
            <div className="container" id="left-chats-container">
                <div className="center-col" id="present-left-chat-items">
                    {conversationsList}
                </div>
            </div>
            <ChooseNewChat user={props.user} logInUsername={props.user.id} conversationsList={conversationsList} listRef={props.listRef}
            currentListOfChats={props.currentListOfChats} setCurrentListOfChats={props.setCurrentListOfChats} connection={props.connection}/>
        </div>
    );
}
export default LeftScreen;