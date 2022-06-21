using ChatWebApi.Data;
using ChatWebApi.Models;
/*using System.Data.Entity;
*/
using Microsoft.EntityFrameworkCore;

namespace ChatWebApi.Services
{
    public class UserService : IUserService
    {
        private IFirebaseTokenService _firebaseTokenService;

        public UserService()
        {
            _firebaseTokenService = new FirebaseTokenService();
        }

        public async void AddUser(ChatWebApiContext context, string id, string name, string password)
        {
            User newUser = new User { id = id, name = name, password = password, conversations = new List<Conversation>() };
            newUser.conversations = new List<Conversation>();
            context.User.Add(newUser);
            _firebaseTokenService.AddUser(context, id);
            context.SaveChanges();
        }


        /*          Getting the user with the given id from the db.
        */
        public async Task<ChatWebApi.Models.User> GetUser(ChatWebApiContext context, string id)
        {
            if (id == null)
                return null;
            try
            {
                var user = context.User.Include(x => x.conversations).Where(u => u.id.Equals(id)).FirstOrDefault();
                if (user == null)
                    return null;
                List<Conversation> conversations = new List<Conversation>();
                var allConversations = context.Conversation.Include(x => x.messages).Include(x => x.contact).ToList();


                int length = user.conversations.Count();
                for (int i = 0; i < length; i++)
                {
                    var conversation = allConversations.Find(con => con.ConversationId.Equals(user.conversations[i].ConversationId));
                    if (conversation != null)
                    {
                        conversations.Add(conversation);
                    }
                }
                user.conversations = conversations;
                return user;
            }
            catch (Exception e)
            {
                int x = 5;
            }
            return null;
        }


        /* 
         * Getting the list of conversations of user with the given username
         */
        public async Task<List<Conversation>> GetAllConversations(ChatWebApiContext context, string username)
        {
            if (username == null)
                return null;
            User user = await GetUser(context, username);
            if (user == null)
                return null;
            return user.conversations.ToList();
        }
    }
}
