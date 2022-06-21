using ChatWebApi.Services;
using ChatWebApi.Hubs;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.SignalR;
using ChatWebApi.Models;
using ChatWebApi.Data;

using FirebaseAdmin.Messaging;
using FirebaseAdmin;
using Google.Apis.Auth.OAuth2;
using System;
using System.IO;
using System.Threading.Tasks;

namespace ChatWebApi.Controllers
{
    /// <summary>
    /// In charge of recieving new messages sent to the users of the chat server.
    /// </summary>
    [ApiController]
    [Route("api/[controller]")]
    public class TransferController : Controller
    {
        private static ChatWebApiContext _context;
        private IConversationService _conversationService;
        private IFirebaseTokenService _firebaseTokenService;
        private readonly IHubContext<AppHub> _appHub;

        public TransferController(IHubContext<AppHub> newHub, ChatWebApiContext context)
        {
            _context = context;
            _firebaseTokenService = new FirebaseTokenService();
            _conversationService = new ConversationService();
            _appHub = newHub;
        }


        // POST: Transfer
        [HttpPost]
        public async Task<IActionResult> Index([FromBody] ParametersForTransfer parameters)
        {
            if (string.IsNullOrWhiteSpace(parameters.from) || string.IsNullOrWhiteSpace(parameters.to) || string.IsNullOrWhiteSpace(parameters.content))
                return BadRequest();
            if (await _conversationService.AddNewMessage(_context, parameters.to, parameters.from, parameters.content, false) == false)
            {
                return NotFound();
            }
            Conversation conv = await _conversationService.GetConversation(_context, parameters.to, parameters.from);
            Models.Message msg = conv.messages[conv.messages.Count - 1];
            ParametersForSendAsyncNewMessage hubParams = new ParametersForSendAsyncNewMessage() { from = parameters.from, to = parameters.to, id = msg.id, content = msg.content, created = msg.created, sent = msg.sent };
            await _appHub.Clients.All.SendAsync("ReceiveMessage", hubParams);
            var sendToToken = await _firebaseTokenService.GetToken(_context, parameters.to);
            if (!sendToToken.Equals(""))
            {
                // See documentation on defining a message payload.
                var message = new FirebaseAdmin.Messaging.Message()
                {
                    Data = new Dictionary<string, string>()
            {
                { "type","newMessage" },
                { "sentTo", parameters.to },
                { "sender", parameters.from },
                { "id", msg.id.ToString() },
                { "content", msg.content },
                { "created", msg.created },
                { "sent", msg.sent.ToString() },
            },
                    Notification = new Notification
                    {
                        Title = "New message from " + conv.contact.name,
                        Body = msg.content
                    },
                    Token =  sendToToken,
                };
                string response = await FirebaseMessaging.DefaultInstance.SendAsync(message);
            }
            return StatusCode(201);
        }
    }
}
