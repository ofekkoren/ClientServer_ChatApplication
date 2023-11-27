using ChatWebApi.Data;

namespace ChatWebApi.Services
{
    public interface IFirebaseTokenService
    {
        public void AddUser(ChatWebApiContext context, string username);
        public Task<string?> GetToken(ChatWebApiContext context, string username);

        public Task<bool> SetToken(ChatWebApiContext context, string username, string token);
    }
}
