using ChatWebApi.Models;

namespace ChatWebApi.Services
{
    public class ContactService : IContactService
    {
        private static IUserService? _userService;
        private static IConversationService? _conversationService;
        // Counts the number of total contacts we have in system so far
        private static int _ids=8;

        public ContactService()
        {
            _userService = new UserService();
            _conversationService = new ConversationService();
        }

        /*
         * Adding a new contact to the username list of contacts.
         */
        public bool Add(string username, string id, string name, string server)
        {
            // Check if the username of the user exist in the users db
            if (username == null || _userService == null || _userService.GetUser(username) == null )
                return false;
            List<Conversation>? conversation = _userService.GetUser(username).conversations;
            if (conversation == null)
                return false;
            int convLength = conversation.Count();
            for (int i = 0; i < convLength; i++)
            {
                if (id.Equals(conversation[i].contact.username))
                    return false;
            }
            Contact contact = new Contact() { id = _ids, username = id, name = name, server = server, last="", lastdate="" };
            _ids++;
            if (_conversationService == null || _conversationService.Add(username, contact) == false)
                return false;
            return true;
        }

        /*
         * Deleting the conversation of a user with the given username of contact.
         */
        public bool Delete(string username, string id)
        {
            if (_userService == null || _conversationService == null)
                return false;
            Contact? contact = GetContact(username, id);
            if (contact == null)
                return false;
            List<Conversation>? conversations = _userService.GetAllConversations(username);
            if (conversations == null)
                return false;
            Conversation? conversation = _conversationService.GetConversation(username, id);
            if (conversation == null)
                return false;
            conversations.Remove(conversation);
            return true;
        }

        /*
         * Editing the information of the log in user with the given id (username) of contact.
         */
        public bool Edit(string username, string id, string name, string server)
        {
            Contact? contact = GetContact(username, id);
            if (contact != null)
            {
                //todo - make sure it updates it in the list.
                contact.name = name;
                contact.server = server;
                return true;
            }
            return false;
        }

        /*
         * Getting the log-in user's contact with the given id (username).
         */
        public Contact? GetContact(string username, string id)
        {
            if (_userService == null)
                return null;
            if (id == null)
                return null;
            List<Conversation>? conversations = _userService.GetAllConversations(username);
            if (conversations == null)
                return null;
            foreach (Conversation conversation in conversations)
            {
                if (conversation.contact.username.Equals(id))
                {
                    return conversation.contact;
                }
            }
            return null;
        }

        /*
         * Getting the log-in user's contact with the given id (username), in the required format.
         */
        public ContactToJson? Get(string username, string id)
        {
            if (_userService == null)
                return null;
            Contact? contact = null;
            if (id == null)
                return null;
            List<Conversation>? conversations = _userService.GetAllConversations(username);
            if (conversations == null)
                return null;
            foreach (Conversation conversation in conversations)
            {
                if (conversation.contact.username.Equals(id))
                {
                    contact = conversation.contact;
                }
            }
            if (contact == null)
            {
                return null;
            }
            ContactToJson contactToJson = new ContactToJson() { id = contact.username,
                name = contact.name, server = contact.server, last = contact.last, lastdate = contact.lastdate };
            return contactToJson;

        }

        /*
         * Getting all the contacts of the username received as a parameter.
         */
        public List<ContactToJson>? GetAll(string username)
        {
            if (_userService == null)
                return null;
            List<ContactToJson> contactToJsons = new List<ContactToJson>();
            List<Conversation>? conversations = _userService.GetAllConversations(username);
            if (conversations == null)
            {
                return null;
            }
            foreach (Conversation conversation in conversations)
            {
                contactToJsons.Add(new ContactToJson()
                {
                    id = conversation.contact.username,
                    name = conversation.contact.name,
                    server = conversation.contact.server,
                    last = conversation.contact.last,
                    lastdate = conversation.contact.lastdate
                });
            }
            return contactToJsons;
        }
    }
}
