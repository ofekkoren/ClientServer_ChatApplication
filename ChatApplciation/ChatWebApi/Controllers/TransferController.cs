using ChatWebApi.Services;
using ChatWebApi.Hubs;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.SignalR;
using ChatWebApi.Models;

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
        private readonly IHubContext<AppHub> _appHub;

        public TransferController(IHubContext<AppHub> newHub)
        {
            _conversationService = new ConversationService();
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
            Message msg = conv.messages[conv.messages.Count - 1];
            ParametersForSendAsyncNewMessage hubParams = new ParametersForSendAsyncNewMessage() { from = parameters.from, to = parameters.to, id = msg.id, content = msg.content, created = msg.created, sent = msg.sent };
            await _appHub.Clients.All.SendAsync("ReceiveMessage", hubParams);
            return StatusCode(201);
        }
    }
}
