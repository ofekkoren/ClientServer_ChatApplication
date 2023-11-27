import '../logIn/LogIn.css';
import './SignUp.css'
import {convertToBase64, getUser} from "../chatScreen/Utils";
import {Link, useNavigate} from "react-router-dom";
import React from "react";
import {user, setUser} from '../logIn/LogIn';
// Keeps the current user which will be logged-in to the website.

var profileImage = "/images/userImages/defaultImage.png";
export {profileImage};



/**
 * A sign-up form for the chat app.
 */
function SignUp() {
    const navigate = useNavigate();

    /**
     * Setting an invalid class and invalid feedback for element.
     * @param element the element that will have an invalid feedback.
     * @param message the message of the feedback.
     */
    const setValid = (element, message) => {
        const inputParent = element.parentElement;
        element.classList.add('is-valid');
        element.classList.remove('is-invalid')
        const validationMessage = inputParent.getElementsByClassName("validation-helper")[0];
        validationMessage.classList.add('valid-feedback');
        validationMessage.classList.remove('invalid-feedback')
        validationMessage.innerText = message;
    };

    /**
     * Setting a valid class and valid feedback for element.
     * @param element the element that will have a valid feedback.
     * @param message the message of the feedback.
     */
    const setInvalid = (element, message) => {
        const inputParent = element.parentElement;
        element.classList.add('is-invalid');
        element.classList.remove('is-valid')
        const validationMessage = inputParent.getElementsByClassName("validation-helper")[0];
        validationMessage.classList.add('invalid-feedback');
        validationMessage.classList.remove('valid-feedback')
        validationMessage.innerText = message;

    }

    /**
     * Checking if the data filled by the user in a form is valid.
     * @returns true if all the data is valid. Else, false is returned.
     */
    const checkValid = (validationData) => {
        //getting the user input elements.
        const userName = document.getElementById('username');
        const nickName = document.getElementById('nickname');
        const password = document.getElementById('Password');
        const passwordRepeat = document.getElementById('validatePassword');
        const valid = "valid"
        let isValid = true;
        //Checking the username. We want it to be unique and not an empty string.
        if (validationData.usernameV === valid)
            setValid(userName, "This username is available");
        else {
            setInvalid(userName, validationData.usernameV);
            isValid = false;
        }

        //Checking the nickname. We don't allow empty string as nickname.
        if (validationData.nicknameV === valid)
            setValid(nickName, "Nice Nickname!");
        else {
            setInvalid(nickName, validationData.nicknameV);
            isValid = false;
        }

        /*
         * Checking the password chosen by the user. It must be longer than 6 character and contain al least one letter
         * and one number.
         */
        if (validationData.passwordV === valid)
            setValid(password, "Good password");
        else {
            setInvalid(password, validationData.passwordV);
            isValid = false;
        }

        if (validationData.repeatPasswordV === valid)
            setValid(passwordRepeat, "");
        else {
            setInvalid(passwordRepeat, validationData.repeatPasswordV);
            isValid = false;
        }
        return isValid
    }

    /**
     * Handling the submission of the registration form.
     * @param event the submit event.
     */
    const handleSubmit = (event) => {
        event.preventDefault();
        event.stopPropagation()
        const newUserName = document.getElementById("username").value.trim();
        const newNickName = document.getElementById('nickname').value.trim();
        const newPassword = document.getElementById('Password').value;
        const repeatPassword = document.getElementById('validatePassword').value;
        fetch("http://localhost:5170/api/SignUp",
            {
                method: 'POST',
                credentials: 'include',
                headers: {'Content-type': 'application/json'},
                body: JSON.stringify({
                    username: newUserName.toString(),
                    nickname: newNickName.toString(),
                    password: newPassword.toString(),
                    repeatPassword: repeatPassword.toString()
                })
            }).then((response) => {
            response.json().then(async (validationData) => {
                // Setting the current user and redirecting the user to the chat if the sign-up was successful.
                if (checkValid(validationData)) {
                    var newUser = await getUser(newUserName.toString());
                    setUser(newUser);
                    navigate("../chatScreen");
                }
            })
        });
    }

    return (
        //The sign-up form.
        <div className="container" id="signContainer">
            <form className="text-center sign-up-form needs-validation" noValidate id="signUpForm"
                  onSubmit={handleSubmit}>
                <h3 className="log-in-header">We need more friends, please join us ...</h3>

                <div className="form-floating mb-3 input-style ">
                    <input type="text" name='userName' className="form-control input-box-size" id="username"
                           placeholder="Username"></input>
                    <label className="form-label" htmlFor="username">Username</label>
                    <span className="validation-helper"></span>
                </div>

                <div className="form-floating mb-3 input-style ">
                    <input type="text" name='userName' className="form-control " id="nickname"
                           placeholder="nickname"></input>
                    <label className="form-label" htmlFor="nickname">nickname</label>
                    <span className="validation-helper"></span>
                </div>

                <div className="form-floating mb-3 input-style ">
                    <input type="password" name='Password' className="form-control " id="Password"
                           placeholder="Password"></input>
                    <label className="form-label" htmlFor="Password">Password</label>
                    <span className="validation-helper"></span>
                </div>

                <div className="form-floating mb-3 input-style ">
                    <input type="password" name='validatePassword' className="form-control " id="validatePassword"
                           placeholder="Repeat password"></input>
                    <label className="form-label" htmlFor="validatePassword">Repeat password</label>
                    <span className="validation-helper"></span>
                </div>

                <div className="mb-3">
                    <button type="submit" className="btn btn-primary btn-lg">Sign-in</button>
                </div>

                <div className="text">
                    already registered? log in <Link to='/' className="text">here</Link>
                </div>
                <div className="text">
                  You are welcome to <a href='http://localhost:5189/Ranks'>Rate us</a>
                </div>
            </form>
        </div>
    )
        ;
}

export default SignUp;
