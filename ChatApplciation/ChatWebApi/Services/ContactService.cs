using ChatWebApi.Data;
using ChatWebApi.Models;

namespace ChatWebApi.Services
{
    public class ContactService : IContactService
    {
/*        private readonly ChatWebApiContext _context;
*/        private static IUserService? _userService;
        private static IConversationService? _conversationService;
        // Counts the number of total contacts we have in system so far
/*        private static int _ids = 8;
*/
        public ContactService()
        {
/*            ChatWebApiContext context = new ChatWebApiContext(new Microsoft.EntityFrameworkCore.DbContextOptions<ChatWebApiContext>());
*/
            _userService = new UserService();
            _conversationService = new ConversationService();
        }

        
/*         * Adding a new contact to the username list of contacts.
*/
        public async Task<bool> Add(ChatWebApiContext context, string username, string id, string name, string server)
        {
            // Check if the username of the user exist in the users db
            if (username == null || _userService == null || _userService.GetUser(context, username) == null)
                return false;
            User user = await _userService.GetUser(context, username);
            if (user == null)
                return false;
            List<Conversation>? conversations = user.conversations;
            if (conversations == null)
            {
                user.conversations = new List<Conversation>();
/*                context.SaveChanges();
*/                conversations = user.conversations;
                /*                return false;
*/
            }

            int convLength = conversations.Count();
            for (int i = 0; i < convLength; i++)
            {
                if (id.Equals(conversations[i].contact.username))
                    return false;
            }
            Contact contact = new Contact() { username = id, name = name, server = server, last = "", lastdate = "" };
/*            Contact contact = new Contact() { id = _ids, username = id, name = name, server = server, last = "", lastdate = "" };
            _ids++;
*/
            if (_conversationService == null || await _conversationService.Add(context, username, contact) == false)
                return false;
            //todo

            context.SaveChanges();

            /*            await context.SaveChangesAsync();
            */
            return true;
        }

        
/*         * Deleting the conversation of a user with the given username of contact.
*/
        public async Task<bool> Delete(ChatWebApiContext context, string username, string id)
        {
            if (_userService == null || _conversationService == null)
                return false;
            Contact? contact = await GetContact(context, username, id);
            if (contact == null)
                return false;
            List<Conversation>? conversations = await _userService.GetAllConversations(context, username);
            if (conversations == null)
                return false;
            Conversation conversation = await _conversationService.GetConversation(context, username, id);
            if (conversation == null)
                return false;
            /*            context.User.Include(x => x.conversations).Where(u => u.id.Equals(id));
            *//*            context.User.Include(x => x.conversations).ToList().Find(u=>u.id.Equals(username).ToString().Remove()
            */
            context.Conversation.Attach(conversation);
            context.Conversation.Remove(conversation);

/*            conversations.Remove(conversation);
*/            //todo
/*            User user = await _userService.GetUser(context, username);
            user.conversations = conversations;
*/            context.SaveChanges();
/*            await context.SaveChangesAsync();
*/            return true;
        }

        
/*         * Editing the information of the log in user with the given id(username) of contact.
*/
        public async Task<bool> Edit(ChatWebApiContext context, string username, string id, string name, string server)
        {
            Contact? contact = await GetContact(context, username, id);
            if (contact != null)
            {
                //todo - make sure it updates it in the list.
                contact.name = name;
                contact.server = server;
                //todo
                await context.SaveChangesAsync();
                return true;
            }
            return false;
        }

        
/*         * Getting the log-in user's contact with the given id (username).
*/         
        public async Task<Contact?> GetContact(ChatWebApiContext context, string username, string id)
        {
            if (_userService == null)
                return null;
            if (id == null)
                return null;
            List<Conversation>? conversations = await _userService.GetAllConversations(context, username);
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

        
/*         * Getting the log-in user's contact with the given id (username), in the required format.
*/         
        public async Task<ContactToJson?> Get(ChatWebApiContext context, string username, string id)
        {
            if (_userService == null)
                return null;
            Contact? contact = null;
            if (id == null)
                return null;
            List<Conversation>? conversations = await _userService.GetAllConversations(context, username);
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
            ContactToJson contactToJson = new ContactToJson()
            {
                id = contact.username,
                name = contact.name,
                server = contact.server,
                last = contact.last,
                lastdate = contact.lastdate
            };
            return contactToJson;
        }

        
/*         * Getting all the contacts of the username received as a parameter.
*/

        public async Task<List<ContactToJson>?> GetAll(ChatWebApiContext context, string username)
        {
            if (_userService == null)
                return null;
            List<ContactToJson> contactToJsons = new List<ContactToJson>();
            User user = await _userService.GetUser(context, username);
            List<Conversation>? conversations = await _userService.GetAllConversations(context, username);
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
