using ChatWebApi.Data;
using ChatWebApi.Hubs;
using ChatWebApi.Models;
using ChatWebApi.Services;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.SignalR;
using FirebaseAdmin.Messaging;
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
        private static ChatWebApiContext _context;
        private IUserService _userService;
        private IContactService _contactService;
        private readonly IHubContext<AppHub> _appHub;
        private IConversationService _conversationService;
        private IFirebaseTokenService _firebaseTokenService;


        public InvitationsController(IHubContext<AppHub> newHub, ChatWebApiContext context)
        {
            _context = context;
            _contactService = new ContactService();
            _userService = new UserService();
            _appHub = newHub;
            _conversationService = new ConversationService();
            _firebaseTokenService = new FirebaseTokenService();


        }

        // POST: Invitation
        [HttpPost]
        public async Task<IActionResult> IndexAsync([FromBody] ParametersForInvitation parameters)
        {
            if (_userService.GetUser(_context, parameters.to) == null)
            {
                return BadRequest();
            }
            if (await _contactService.Add(_context, parameters.to, parameters.from, parameters.from, parameters.fromServer) == false)
            {
                return BadRequest();
            }
            List<Conversation> conversations = await _userService.GetAllConversations(_context, parameters.to);
            ParametersForSendAsyncNewContact hunParams = new ParametersForSendAsyncNewContact() { to = parameters.to, conversations = Json(conversations) };
            await _appHub.Clients.All.SendAsync("NewContactAdded", hunParams);
            //****************************************************
            Conversation conv = await _conversationService.GetConversation(_context, parameters.to, parameters.from);
            var sendToToken = await _firebaseTokenService.GetToken(_context, parameters.to);
            if (!sendToToken.Equals(""))
            {
                // See documentation on defining a message payload.
                var message = new FirebaseAdmin.Messaging.Message()
                {
                    Data = new Dictionary<string, string>()
            {
                { "type","newConversation" },
                { "sentTo", parameters.to },
                { "sender", parameters.from },
                { "conversationId", conv.ConversationId },
                { "contactId", conv.contact.id.ToString() },
                { "contactUsername", conv.contact.username },
                { "contactName", conv.contact.name },
                { "contactServer", conv.contact.server },
                { "contactLast", conv.contact.last },
                { "contactLastDate", conv.contact.lastdate },
            },
                    Notification = new Notification
                    {
                        Title = "New chat with " + conv.contact.name,
                        Body = conv.contact.name + " want to talk"
                    },
                    Token =  sendToToken,
                };
                string response = await FirebaseMessaging.DefaultInstance.SendAsync(message);
            }
            return StatusCode(201);

        }
    }
}
