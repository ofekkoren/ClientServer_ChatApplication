using ChatWebApi.Services;
using ChatWebApi.Hubs;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.SignalR;
using ChatWebApi.Models;
using FirebaseAdmin.Messaging;

using FirebaseAdmin;
using FirebaseAdmin.Messaging;
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
        private IConversationService _conversationService;
        private IFirebaseTokenService _firebaseTokenService;
        private readonly IHubContext<AppHub> _appHub;

        public TransferController(IHubContext<AppHub> newHub)
        {
            _conversationService = new ConversationService();
            _firebaseTokenService = new FirebaseTokenService();
            _appHub = newHub;
        }


        // POST: Transfer
        [HttpPost]
        public async Task<IActionResult> Index([FromBody] ParametersForTransfer parameters)
        {
            if (string.IsNullOrWhiteSpace(parameters.from) || string.IsNullOrWhiteSpace(parameters.to) || string.IsNullOrWhiteSpace(parameters.content))
                return BadRequest();
            if (_conversationService.AddNewMessage(parameters.to, parameters.from, parameters.content, false) == false)
            {
                return NotFound();
            }
            Conversation conv = _conversationService.GetConversation(parameters.to, parameters.from);
            Models.Message msg = conv.messages[conv.messages.Count - 1];
            ParametersForSendAsyncNewMessage hubParams = new ParametersForSendAsyncNewMessage() { from = parameters.from, to = parameters.to, id = msg.id, content = msg.content, created = msg.created, sent = msg.sent };
            await _appHub.Clients.All.SendAsync("ReceiveMessage", hubParams);
            var sendToToken = _firebaseTokenService.GetToken(parameters.to);

            // See documentation on defining a message payload.
            var message = new FirebaseAdmin.Messaging.Message()
            {
                Data = new Dictionary<string, string>()
            {
                { "score", "850fffffffff" },
                { "time", "2:45dddd" },
            },
                Notification = new Notification
                {
                    Title = "bla title",
                    Body = "blabla Body"
                },
                Token = sendToToken,
            };

            // Send a message to the device corresponding to the provided
            // registration token.
            string response = await FirebaseMessaging.DefaultInstance.SendAsync(message);
            // Response is a message ID string.
            Console.WriteLine("Successfully sent message: " + response);
            return StatusCode(201);
        }
    }
}
