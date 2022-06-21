using ChatWebApi.Data;
using ChatWebApi.Models;

namespace ChatWebApi.Services
{
    public class ConversationService : IConversationService
    {
/*        private readonly ChatWebApiContext _context;
*/        private static IUserService _userService;
        // Counts the number of total messages sent so far
/*        private static int _ids = 27;
*/
        public ConversationService()
        {
            _userService = new UserService();
/*            _context = context;
*/
        }


         
/*         * Receiving the conversation of the user with id username with the contact with id: "id".
*/

        public async Task<Conversation?> GetConversation(ChatWebApiContext context, string username, string id)
        {
            if (_userService == null)
                return null;
            User? currentUser = await _userService.GetUser(context, username);
            if (currentUser == null)
                return null;
            List<Conversation> usersConversations = currentUser.conversations;
            if (usersConversations == null)
                return null;
            Conversation conversation = null;
            int len = usersConversations.Count;
            //todo - change with _contex.find?
            for (int i = 0; i < len; i++)
            {
                if (usersConversations[i].contact.username.Equals(id))
                {
                    conversation = usersConversations[i];
                    break;
                }
            }
            return conversation;
        }


         
/*         * Receiving the all of the messages in the conversation of the user with id username with the contact with id: "id".
*/        
        public async Task<List<Message>?> GetAllMessages(ChatWebApiContext context, string username, string id)
        {
            Conversation? conversation = await GetConversation(context, username, id);
            if (conversation == null)
                return null;
            return conversation.messages;
        }


         
/*         * Receiving the all of the messages in the conversation of the user with id username with the contact with id: "id".
*/        
        public async Task<bool> AddNewMessage(ChatWebApiContext context, string username, string id, string content, bool sent)
        {
            Conversation? conversation1 = await GetConversation(context, username, id);
            if (conversation1 == null)
                return false;
            string creationTime = new DateTime(DateTime.Now.Year, DateTime.Now.Month, DateTime.Now.Day, DateTime.Now.Hour, DateTime.Now.Minute, DateTime.Now.Second, DateTime.Now.Millisecond, DateTimeKind.Unspecified).ToString("O");
/*            Message m1 = new Message() { id = _ids, content = content, created = creationTime, sent = sent };
            ++_ids;
*/
            Message m1 = new Message() { content = content, created = creationTime, sent = sent };
            conversation1.messages.Add(m1);
            conversation1.contact.last = m1.content;
            conversation1.contact.lastdate = m1.created;
            await context.SaveChangesAsync();
            return true;
        }


         
/*         * Receiving a single message with id: messageId in the conversation of the user with id username with the contact with id: "id".
*/        
        public async Task<Message?> GetMessage(ChatWebApiContext context, string Username, string contactId, int messageId)
        {
            Conversation? conversation = await GetConversation(context, Username, contactId);
            if (conversation == null)
                return null;
            List<Message>? messages = conversation.messages;
            return messages.Find(message => message.id == messageId);
        }


         
/*         * Deleting a single message with id: messageId in the conversation of the user with id username with the contact with id: "id".
*/        
        public async Task<bool> DeleteMessage(ChatWebApiContext context, string Username, string contactId, int messageId)
        {
            Message? message = await GetMessage(context, Username, contactId, messageId);
            if (message == null)
                return false;
            Conversation? conversation = await GetConversation(context, Username, contactId);
            if (conversation == null)
                return false;
            conversation.messages.Remove(message);
            if (conversation.messages.Count == 0)
            {
                conversation.contact.last = "";
                conversation.contact.lastdate = "";
            }
            else
            {
                conversation.contact.last = conversation.messages.LastOrDefault().content;
                conversation.contact.lastdate = conversation.messages.LastOrDefault().created;
            }
            await context.SaveChangesAsync();
            return true;
        }


          
/*         *  Adding a new conversation of the user with id username with the contact received as a parameter.
*/
        public async Task<bool> Add(ChatWebApiContext context, string Username, Contact newContact)
        {
            if (newContact == null)
                return false;
            User? user = await _userService.GetUser(context, Username);
            if (user == null)
                return false;
            List<Conversation>? conversationsOfUser = user.conversations;
            if (conversationsOfUser == null)
            {
                return false;
            }

            string newConversationID = user.id + newContact.username;
            conversationsOfUser.Insert(0, new Conversation() { contact = newContact, ConversationId = newConversationID, messages = new List<Message>() });
            user.conversations = conversationsOfUser;
            context.SaveChanges();
            return true;
        }


         
/*         * Editing the contant of a message with id: messageId, in the conversation of the user with id username 
         * with the contact with id: "id".
*/        
        public async Task<bool> EditMessage(ChatWebApiContext context, string username, string id, int messageId, string content)
        {
            Message? message = await GetMessage(context, username, id, messageId);
            if (message == null)
                return false;
            message.content = content;

            Conversation? conversation = await GetConversation(context, username, id);
            if (conversation != null)
            {
                conversation.contact.last = conversation.messages.LastOrDefault().content;
                conversation.contact.lastdate = conversation.messages.LastOrDefault().created;
            }
            await context.SaveChangesAsync();
            return true;
        }
    }
}
