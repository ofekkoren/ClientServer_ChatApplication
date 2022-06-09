using ChatWebApi.Models;

namespace ChatWebApi.Services
{
    public interface IUserService
    {
        public void AddUser(string id, string name, string password);

        public User? GetUser(string id);

        public List<Conversation>? GetAllConversations(string username);

    }
}