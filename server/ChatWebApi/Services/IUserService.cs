using ChatWebApi.Data;
using ChatWebApi.Models;

namespace ChatWebApi.Services
{
    public interface IUserService
    {
        public void AddUser(ChatWebApiContext context, string id, string name, string password);

        public Task<User?> GetUser(ChatWebApiContext context, string id);

        public Task<List<Conversation>?> GetAllConversations(ChatWebApiContext context, string username);

    }
}