using ChatWebApi.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.SignalR;

namespace ChatWebApi.Hubs
{
    /// <summary>
    /// A signalIR hub that will be in charge of pushing new messages and chats for the chat users in real-time.
    /// </summary>
    public class AppHub:Hub
    {
        /// <summary>
        /// In charge of pushing new message from user "from" to user "to". It recieves all the fields of the new message
        /// as parametes and sends them to the client side.
        /// </summary>

        public async Task HubNewMessage (string from,string to, int id, string content ,string created,bool sent)
        {
            ParametersForSendAsyncNewMessage hubParams = new ParametersForSendAsyncNewMessage() { from = from, to=to ,id = id, content = content,created=created,sent=sent };
            await Clients.All.SendAsync("MessageReceived", hubParams);
        }

        /// <summary>
        /// In charge of adding a new chat for to user "to" after he was invited by other user. The chat information is
        /// sent to the user in Json format.
        /// </summary>
        public async Task HubNewContact(string to,JsonResult jsonConversation )
        {
            ParametersForSendAsyncNewContact hunParams = new ParametersForSendAsyncNewContact() { to = to, conversations = jsonConversation };
            await Clients.All.SendAsync("NewContactAdded", hunParams);
        }
    }
}
