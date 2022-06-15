using ChatWebApi.Models;
using ChatWebApi.Services;
using Microsoft.AspNetCore.Mvc;

namespace ChatWebApi.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class UsersController : Controller
    {
        private static IUserService _userService;
        private static IConversationService _conversationService;
        private static IFirebaseTokenService _firebaseTokenService;

        private readonly string redirectTo = "http://localhost:3000/";
        private readonly string currentUser = "currentUser";

        public UsersController()
        {
            _userService = new UserService();
            _conversationService = new ConversationService();
            _firebaseTokenService = new FirebaseTokenService();
        }

        [HttpPost]
        public IActionResult Index()
        {
            if (HttpContext.Session.GetString(currentUser) == null)
                return Redirect(redirectTo);
            User user = _userService.GetUser(HttpContext.Session.GetString(currentUser));
            return Json(user);
        }



        [HttpPost("GetAllConversationsOfUser")]
        public IActionResult GetAllConversationsOfUser([FromBody] IdClass parameter)
        {
            if (HttpContext.Session.GetString(currentUser) == null)
                return Redirect(redirectTo);        
            List<Conversation> conversations = _userService.GetAllConversations(parameter.id);
            if (conversations == null)
            {
                return NotFound();
            }
            return Json(conversations);
        }

        [HttpPost("MoveConversationToTopList")]
        public IActionResult MoveConversationToTopList([FromBody] ParametersForMoveConversation parameters)
        {
            Conversation conversation = _conversationService.GetConversation(parameters.username, parameters.id);
            if (conversation == null)
            {
                return Json(_userService.GetAllConversations(parameters.username));
            }
            List<Conversation> conversations = _userService.GetAllConversations(parameters.username);
            if (conversations == null)
            {
                return Json(_userService.GetAllConversations(parameters.username));
            }
            //todo - conversations instead of calling the function again, check if updates user.conversations
            _userService.GetAllConversations(parameters.username).Remove(conversation);
            _userService.GetAllConversations(parameters.username).Insert(0, conversation);
            return Json(_userService.GetAllConversations(parameters.username));
        }

        [HttpPost("GetConversation")]
        public IActionResult GetConversation([FromBody] IdClass parameter)
        {
            if (HttpContext.Session.GetString(currentUser) == null)
                return Redirect(redirectTo);
            Conversation conversation = _conversationService.GetConversation(HttpContext.Session.GetString(currentUser), parameter.id);
            return Json(conversation);
        }

        [HttpPost("SetFirebaseToken")]
        public IActionResult SetFirebaseToken([FromBody] IdClass parameter)
        {
            if (HttpContext.Session.GetString(currentUser) == null)
                return BadRequest();
            _firebaseTokenService.SetToken(HttpContext.Session.GetString(currentUser),parameter.id);
            return StatusCode(201);
        }
    }
}