import React, {useEffect} from 'react';
import './ChatScreen.css';
import {useState, useRef} from "react";
import LeftScreen from './leftScreen/LeftScreen';
import RightScreen from "./rightScreen/RightScreen";
import {Link, useLocation} from "react-router-dom";
import {user} from '../logIn/LogIn';
import {HubConnectionBuilder} from '@microsoft/signalr';


function ChatScreen() {
    var [currentConversation, setCurrentConversation] = useState("");
    var conversationDBRef = useRef(""); //Reference to the current conversation
    var [currentListOfChats, setCurrentListOfChats] = useState(user.conversations);
    var listOfChatsRef = useRef(user.conversations) //Reference to the current lost of chats
    var [connection, setConnection] = useState(null);

    useEffect(() => {
        //Creating a signaIR connection
        const newConnection = new HubConnectionBuilder().withUrl('http://localhost:5170/hubs/chatHub').withAutomaticReconnect().build();
        setConnection(newConnection)
    }, [])

    useEffect(() => {
        if (listOfChatsRef.current !== [] && currentConversation !== "") {

            /*
             * If a new message was sent to the current user in the chat he is currently watching we will update the
             * current conversation.
             */
            if (currentConversation.contact.username === listOfChatsRef.current[0].contact.username) {
                var copyLastConversation = JSON.parse(JSON.stringify(listOfChatsRef.current[0]))
                conversationDBRef.current = copyLastConversation
                setCurrentConversation(copyLastConversation)
            }
        }
    }, [currentListOfChats])

    useEffect(() => {
        if (connection) {
            //Defining methods that will be used when a signaIR signal is sent
            connection.start().then((result) => {
                //Adding new contact when someone else adds the current user as contact
                connection.on('NewContactAdded', (params) => {
                    if (user.id === params.to) {
                        listOfChatsRef.current = params.conversations.value
                        setCurrentListOfChats(params.conversations.value)
                    }
                })

                //Adding new message when a contact sent a new message to the current user
                connection.on('ReceiveMessage', (params) => {
                    if (user.id === params.to) {
                        //Finding the chat that the message should be added to
                        for (var i = 0; i < listOfChatsRef.current.length; i++) {
                            var chat = listOfChatsRef.current[i];
                            if (chat.contact.username === params.from) {
                                chat.messages.push({
                                    id: params.id,
                                    content: params.content,
                                    created: params.created,
                                    sent: params.sent
                                })
                                //Putting the conversations at the top of the conversations list
                                listOfChatsRef.current.splice(i, 1);
                                listOfChatsRef.current.unshift(chat);
                            }
                        }
                        //Updating the chats list
                        let chatsArr = JSON.parse(JSON.stringify(listOfChatsRef.current));
                        setCurrentListOfChats(chatsArr)
                    }
                })
            })
        }
    }, [connection])

    useEffect(() => {
        //Applying the function only if a chat was chosen by the user.
        if (currentConversation !== "") {
            let bottom = document.getElementById("lastMessage");
            //If a new message was sent in the current chat we add this message to the corresponding array in our DB.
            if (currentConversation.messages.length !== conversationDBRef.current.messages.length) {
                conversationDBRef.current.messages.push(currentConversation.messages[currentConversation.messages.length - 1])
                //Scrolling down to the last message if the user sent a new message.
                if (currentConversation.messages[currentConversation.messages.length - 1].sent === true) {
                    bottom.scrollIntoView({block: "end"});
                }
                // If the current conversation is in the array, add it to the front of the array.
                var conversations = "";

                async function fetchData() {
                    var response = await fetch('http://localhost:5170/api/Users/MoveConversationToTopList',
                        {
                            method: "POST",
                            credentials: 'include',
                            headers: {'Content-Type': 'application/json'},
                            body: JSON.stringify({
                                username: user.id,
                                id: currentConversation.contact.username
                            })
                        })
                    // If the current conversation pushed to the top of the user's list of conversations, get the list.
                    if (response.ok)
                        var conversations = await response.json();
                    if (conversations !== "") {
                        setCurrentListOfChats(conversations);
                        listOfChatsRef.current = conversations

                    }
                }
                fetchData();
            }
            //If we changed the chat conversation we scroll down to the last message.
            else {
                bottom.scrollIntoView({block: "end"});
            }
        }
    }, [currentConversation])


    //If there is no user connected the chat screen won't be displayed.
    if (user === "") {
        return (
            <div className="sign-up-form">
                <h4 className="text-center" role="alert">You have to log-in in order to see the chat
                    screen.<br/><br/>
                    You can click <Link to='/' className="text">here</Link> to log-in.<br/>
                </h4>
            </div>
        )
    } else {
        return (
            <div className="container-chat-screen justify-content-center">
                <div className="inner-chat-cube">
                    <LeftScreen currentConversation={currentConversation} user={user} setChat={setCurrentConversation}
                                listRef={listOfChatsRef} connection={connection} refer={conversationDBRef}
                                currentListOfChats={currentListOfChats} setCurrentListOfChats={setCurrentListOfChats}/>
                    <RightScreen chat={currentConversation} setChat={setCurrentConversation} user={user}
                                 connection={connection}/>
                </div>
            </div>
        );
    }
}

export default ChatScreen;