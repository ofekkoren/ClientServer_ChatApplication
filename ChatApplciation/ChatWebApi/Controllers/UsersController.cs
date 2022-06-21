using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using ChatWebApi.Data;
using ChatWebApi.Models;
using ChatWebApi.Services;

namespace ChatWebApi.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class UsersController : Controller
    {
        private static IUserService _userService;
        private static IConversationService _conversationService;
        private static ChatWebApiContext _context;
        private static IFirebaseTokenService _firebaseTokenService;

        /*        private readonly ChatWebApiContext _context;
        */
        private readonly string redirectTo = "http://localhost:3000/";
        private readonly string currentUser = "currentUser";

        public UsersController(ChatWebApiContext context)
        {
            _context = context;
            _userService = new UserService();
            _conversationService = new ConversationService();
            _firebaseTokenService = new FirebaseTokenService();

        }

        /*        [HttpGet]
                public async Task<IActionResult> getUser()
                {
                    var listOfUsers = _context.User.ToList();
                    var user = UtilsUser.GetUser(_context,"Ofek Koren").Result;
                    return Json(user);
                }*/

        [HttpPost]
        public async Task<IActionResult> Index()
        {
            if (HttpContext.Session.GetString(currentUser) == null)
                return Redirect(redirectTo);
            var user = await _userService.GetUser(_context, HttpContext.Session.GetString(currentUser));

            /*            var user = from u in _context.User
                                   where u.name.Equals(HttpContext.Session.GetString(currentUser))
                                   select u;*/
            /*            User? user = await Utils.GetUser(HttpContext.Session.GetString(currentUser));
            */
            if (user == null)
            {
                return NotFound();
            }
            return Json(user);
        }

        [HttpPost("GetAllConversationsOfUser")]
        public async Task<IActionResult> GetAllConversationsOfUser([FromBody] IdClass parameter)
        {
            if (HttpContext.Session.GetString(currentUser) == null)
                return Redirect(redirectTo);
            List<Conversation> conversations = await _userService.GetAllConversations(_context, parameter.id);
            if (conversations == null)
            {
                return NotFound();
            }
            return Json(conversations);
        }


        [HttpPost("GetConversation")]
        public async Task<IActionResult> GetConversation([FromBody] IdClass parameter)
        {
            if (HttpContext.Session.GetString(currentUser) == null)
                return Redirect(redirectTo);
            Conversation conversation = await _conversationService.GetConversation(_context, HttpContext.Session.GetString(currentUser), parameter.id);
            if (conversation == null)
                return NotFound();
            return Json(conversation);
        }

        [HttpPost("MoveConversationToTopList")]
        public async Task<IActionResult> MoveConversationToTopList([FromBody] ParametersForMoveConversation parameters)
        {
            Conversation conversation = await _conversationService.GetConversation(_context, parameters.username, parameters.id);
            if (conversation == null)
            {
                return Json(_userService.GetAllConversations(_context, parameters.username));
            }
            List<Conversation> conversations = await _userService.GetAllConversations(_context, parameters.username);
            if (conversations == null)
                return NotFound();
            conversations.Remove(conversation);
            conversations.Insert(0, conversation);
            _context.SaveChanges();
            return Json(conversations);
        }

        [HttpPost("SetFirebaseToken")]
        public IActionResult SetFirebaseToken([FromBody] IdClass parameter)
        {
            if (HttpContext.Session.GetString(currentUser) == null)
                return BadRequest();
            _firebaseTokenService.SetToken(_context, HttpContext.Session.GetString(currentUser), parameter.id);
            return StatusCode(201);
        }
    }
}