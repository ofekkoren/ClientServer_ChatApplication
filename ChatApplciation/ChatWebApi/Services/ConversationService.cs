using ChatWebApi.Models;

namespace ChatWebApi.Services
{
    public class ConversationService : IConversationService
    {
        private static IUserService? _userService;
        // Counts the number of total messages sent so far
        private static int _ids = 27;

        public ConversationService()
        {
            _userService = new UserService();
        }

        /*
         * Receiving the conversation of the user with id username with the contact with id: "id".
         */
        public Conversation? GetConversation(string username, string id)
        {
            if (_userService == null)
                return null;
            User? currentUser = _userService.GetUser(username);
            if (currentUser == null)
                return null;
            List<Conversation> usersConversations = currentUser.conversations;
            if (usersConversations == null)
                return null;
            Conversation? conversation = null;
            int len = usersConversations.Count;
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

        /*
        * Receiving the all of the messages in the conversation of the user with id username with the contact with id: "id".
        */
        public List<Message>? GetAllMessages(string Username, string id)
        {
            Conversation? conversation = GetConversation(Username, id);
            if (conversation == null)
                return null;
            return conversation.messages;
        }

        /*
         * Receiving the all of the messages in the conversation of the user with id username with the contact with id: "id".
         */
        public bool AddNewMessage(string Username, string id, string content, bool sent)
        {
            Conversation? conversation1 = GetConversation(Username, id);
            if (conversation1 == null)
                return false;
            string creationTime = new DateTime(DateTime.Now.Year, DateTime.Now.Month, DateTime.Now.Day, DateTime.Now.Hour, DateTime.Now.Minute, DateTime.Now.Second, DateTime.Now.Millisecond, DateTimeKind.Unspecified).ToString("O");
            Message m1 = new Message() { id = _ids, content = content, created = creationTime, sent = sent };
            ++_ids;
            conversation1.messages.Add(m1);
            conversation1.contact.last = m1.content;
            conversation1.contact.lastdate = m1.created;
            return true;
        }

        /*
         * Receiving a single message with id: messageId in the conversation of the user with id username with the contact with id: "id".
         */
        public Message? GetMessage(string Username, string contactId, int messageId)
        {
            Conversation? conversation = GetConversation(Username, contactId);
            if (conversation == null)
                return null;
            List<Message>? messages = conversation.messages;
            return messages.Find(message => message.id == messageId);
        }

        /*
         * Deleting a single message with id: messageId in the conversation of the user with id username with the contact with id: "id".
         */
        public bool DeleteMessage(string Username, string contactId, int messageId)
        {
            Message? message = GetMessage(Username, contactId, messageId);
            if (message == null)
                return false;
            Conversation? conversation = GetConversation(Username, contactId);
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
            return true;
        }

        /*
         * Adding a new conversation of the user with id username with the contact received as a parameter.
         */
        public bool Add(string Username, Contact newContact)
        {
            if (newContact == null)
                return false;
            List<Conversation>? conversationsOfUser = _userService.GetUser(Username).conversations;
            if (conversationsOfUser == null)
                return false;
            string newConversationID = _userService.GetUser(Username).id + newContact.username;
            _userService.GetUser(Username).conversations.Insert(0, new Conversation() { contact = newContact, ConversationId = newConversationID, messages = new List<Message>() });
            return true;
        }

        /*
         * Editing the contant of a message with id: messageId, in the conversation of the user with id username 
         * with the contact with id: "id".
         */
        public bool EditMessage(string username, string id, int messageId, string content)
        {
            Message? message = GetMessage(username, id, messageId);
            if (message == null)
                return false;
            message.content = content;

            Conversation? conversation = GetConversation(username, id);
            if (conversation != null)
            {
                conversation.contact.last = conversation.messages.LastOrDefault().content;
                conversation.contact.lastdate = conversation.messages.LastOrDefault().created;
            }
            return true;
        }
    }
}
