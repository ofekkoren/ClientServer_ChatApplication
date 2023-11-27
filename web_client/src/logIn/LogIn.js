import './LogIn.css';
import ChatScreen from '../chatScreen/ChatScreen';
import '../signUp/SignUp.css'
import React from 'react';
import ReactDOM from 'react-dom';
import {Link, useNavigate, useParams} from "react-router-dom";
import { getUser } from '../chatScreen/Utils';
import './Try.css'

// Keeps the current user which will be logged-in to the website.
var user = "";
var currentChatGlobal="";
/**
 * Setter to the logged-in user.
 * @param initializedUser is an update user logged in the website.
 */
function setUser(initializedUser) {
  user = initializedUser;
}
export {user, setUser};



/**
 * The LogIn function is the log-in logic. It gives the user an option to log in to the web by
 * entering his name and password, and if the fields are valid it enters him into his
 * chats screen.
 */
function LogIn() {
  
  function handle(e) {
    var text = document.getElementById("appendTxt");
    // text.setText("hello")
    console.log("hello")
  }

  var tag = $('#appendTxt');

    return (

<html>
    <head>
            {/* <Link href="Try.css" rel="stylesheet"></Link> */}
    </head>
    <body>
      <div className='form'>
        <div>
            <text> Username
                <input type="text" id="usernameInput" placeholder='Username please :)'> 
                </input>
            </text>
        </div>
        <div>
            <text> password
                <input type="password" placeholder='Password please :)'> 
                </input>
            </text>
        </div>
        <div>
          <button type='button' id="btn" onClick={handle}> Login
          </button>
          <div id="appendTxt">
          </div>
        </div>
        </div>
        <script src="[Source]/jquery.js"></script>
    </body>
</html>
    );
}
export default LogIn;