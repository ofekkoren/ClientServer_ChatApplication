import '../ChatScreen.css';
import "./RightScreen.css"
import { profileImage } from '../../signUp/SignUp';

/**
 * The header of the chat. It includes an image and the nickname of the user we currently chat with.
 * @param props includes the user we currently chat with.
 */
function ChatHeader(props) {
    if (props.chatWith === "") {
        return (<div className="topLine"></div>)
    }
    else {
        return (
            <div className="topLine">
                <img src={profileImage}
                     className=" top-profile-image"></img>
                <h5>{props.chatWith.name}</h5>
            </div>
        )
    }
}

export default ChatHeader;