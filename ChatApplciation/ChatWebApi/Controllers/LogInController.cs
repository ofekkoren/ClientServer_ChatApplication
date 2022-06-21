using ChatWebApi.Data;
using ChatWebApi.Models;
using ChatWebApi.Services;
using Microsoft.AspNetCore.Mvc;

namespace ChatWebApi.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class LogInController : Controller
    {
        private static IUserService _userService;
        private static ChatWebApiContext _context;

        /*        private readonly ChatWebApiContext _context;
        */        /*        private static IUserService? _userService;
                */
        public LogInController(ChatWebApiContext context)
        {
            _context = context;
            _userService = new UserService();
            /*            _userService = new UserService();
            */
        }



        // POST: LogInController
        [HttpPost]
        public async Task<IActionResult> Index([FromBody] LogInParameters parameters)
        {
            // Checking if one of the fields is empty and returning a suitable json.
            if (parameters.username == null || parameters.password == null || parameters.username == "" || parameters.password == "")
            {
                var invalidEmptyUser = new
                {
                    username = "empty",
                    password = "empty"
                };
                return Json(invalidEmptyUser);
            }
            /*            User user =  _context.User.Where(u => u.name.Equals(parameters.username)).FirstOrDefault();
            */
            var user = await _userService.GetUser(_context, parameters.username);

            /*            User? user = await Utils.GetUser(parameters.username);
            */            // Checking if no such user exists in the system or if the password of the user does not match what the user has entered.
            if ((user == null) || (user.password.Equals(parameters.password) == false))
            {
                var invalidUser = new
                {
                    username = "invalid",
                    password = "invalid"
                };
                return Json(invalidUser);
            }
            // If both fields are valid, return a suitable json.
            var validUser = new
            {
                username = "valid",
                password = "valid"
            };
            HttpContext.Session.SetString("currentUser", parameters.username);
            return Json(validUser);

        }
    }
}