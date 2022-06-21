using ChatWebApi.Data;
using ChatWebApi.Services;
using Microsoft.AspNetCore.Mvc;
using System.Text.RegularExpressions;

namespace ChatWebApi.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class SignUpController : Controller
    {
        private static IUserService _userService;
        private static ChatWebApiContext _context;

        /*        private readonly ChatWebApiContext _context;
        */
        public SignUpController(ChatWebApiContext context)
        {
            _userService = new UserService();
            _context = context;
        }

        // POST: SignUpController
        [HttpPost]
        public async Task<IActionResult> Index([FromBody] SignInparameters parameters)
        {
            const int feedbackSize = 4;
            const int usernameIndex = 0;
            const int nicknameIndex = 1;
            const int passwordIndex = 2;
            const int repeatPasswordIndex = 3;
            const int passwordMinLength = 6;

            string[] signUpFeedback = new string[feedbackSize] { "valid", "valid", "valid", "valid" };

            //Checking the username. We want it to be unique and not an empty string.
            if ((parameters.username == null) || (parameters.username.Equals("")))
                signUpFeedback[usernameIndex] = "Username is required";
            else if (_userService.GetUser(_context, parameters.username).Result != null)
                signUpFeedback[usernameIndex] = "Such username already exists";

            //Checking the nickname. We don't allow empty string as nickname.
            if ((parameters.nickname == null) || (parameters.nickname.Equals("")))
                signUpFeedback[nicknameIndex] = "Nickname is required";


/*            *Checking the password chosen by the user. It must be longer than 6 character and contain al least one letter
            * and one number.
*/

            if (parameters.password == null || parameters.password.Length < passwordMinLength)
                signUpFeedback[passwordIndex] = "Password must contain at least 6 characters";
            else if (Regex.IsMatch(parameters.password, @"\d") == false)
                signUpFeedback[passwordIndex] = "Password must contain at least one number";
            else if ((Regex.IsMatch(parameters.password, "[a-zA-Z]") == false))
                signUpFeedback[passwordIndex] = "Password must contain at least one letter";

            if ((parameters.repeatPassword == null) || (parameters.repeatPassword.Equals("")))
                signUpFeedback[repeatPasswordIndex] = "You are required to repeat your password";
            else if (parameters.password.Equals(parameters.repeatPassword) == false)
                signUpFeedback[repeatPasswordIndex] = "Password doesn't match";
            const string validStr = "valid";
            bool isValid = true;
            for (int i = 0; i < feedbackSize; i++)
            {
                if (!signUpFeedback[i].Equals(validStr))
                {
                    isValid = false;
                    break;
                }

            }
            if (isValid)
            {
                _userService.AddUser(_context, parameters.username, parameters.nickname, parameters.password);
                HttpContext.Session.SetString("currentUser", parameters.username);
            }

            //Returning the result of the validation to the client
            var validationResult = new
            {
                usernameV = signUpFeedback[usernameIndex],
                nicknameV = signUpFeedback[nicknameIndex],
                passwordV = signUpFeedback[passwordIndex],
                repeatPasswordV = signUpFeedback[repeatPasswordIndex],
            };
            return Json(validationResult);
        }
    }
}
