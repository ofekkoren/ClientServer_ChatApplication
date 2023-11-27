import React from 'react';
import LeftScreen from './LeftScreen';
import LeftChatItem from './leftChatItem/LeftChatItem';
import ChooseNewChat from './ChooseNewChat';
import { profileImage } from '../../signUp/SignUp';

/**
 * The function recieves new contact's username, and checks if it is a valid
 * username in the DB.
 * @param props include the username of the user currently logged-in, the username
 * that the user is wishing to talk to, the array of conversations the log-in user
 * is having and a setter to this array.
 */
async function AddNewChat(props) {
    var isRegisteredUser = false;
    // If the user entered his own contact's identifier username, do nothing. 
    if (props.logInUsername === props.newContact) {
        var invalidUser = "you can't talk with yourself here :("
        document.getElementById("validation").innerHTML = invalidUser;
        return "false";
    }

    for (var i = 0; i < Object.keys(props.conversationsList).length; i++) {
        // If the user already have a conversation with this contact, do nothing.
        if (props.conversationsList[i].props.conversation.usernameInChat === props.newContact) {
            var invalidUser = "this user is already talking with you!"
            document.getElementById("validation").innerHTML = invalidUser;
            return "false";
        }
    }
    return "true";
}

export default AddNewChat;