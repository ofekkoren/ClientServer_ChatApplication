using ChatWebApi.Hubs;
using ChatWebApi.Models;
using ChatWebApi.Services;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.SignalR;

namespace ChatWebApi.Controllers
{
    /// <summary>
    /// In charge of recieving invitations for new chats for the users of the chat server.
    /// </summary>
    [ApiController]
    [Route("api/[controller]")]
    public class InvitationsController : Controller
    {
        private IUserService _userService;
        private IContactService _contactService;
        private readonly IHubContext<AppHub> _appHub;


        public InvitationsController(IHubContext<AppHub> newHub)
        {
            _contactService = new ContactService();
            _userService = new UserService();
            _appHub = newHub;

        }

        // POST: Invitation
        [HttpPost]
        public async Task<IActionResult> IndexAsync([FromBody] ParametersForInvitation parameters)
        {
            if (_userService.GetUser(parameters.to) == null)
            {
                return BadRequest();
            }
            if (_contactService.Add(parameters.to, parameters.from, parameters.from, parameters.fromServer) == false)
            {
                return BadRequest();
            }
            List<Conversation> conversations = _userService.GetAllConversations(parameters.to);
            ParametersForSendAsyncNewContact hunParams =new ParametersForSendAsyncNewContact() { to = parameters.to, conversations= Json(conversations) };
            await _appHub.Clients.All.SendAsync("NewContactAdded", hunParams);
            return StatusCode(201);

        }
    }
}
