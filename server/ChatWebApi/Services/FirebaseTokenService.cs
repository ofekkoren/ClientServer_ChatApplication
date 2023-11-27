using ChatWebApi.Data;
using ChatWebApi.Models;

namespace ChatWebApi.Services
{
    public class FirebaseTokenService : IFirebaseTokenService
    {
        public FirebaseTokenService()
        {
        }

        public async void AddUser(ChatWebApiContext context, string username)
        {
            context.FirebaseUserToken.Add(new FirebaseUserToken() { username = username, token = "" });
            context.SaveChanges();
        }

        public async Task<string?> GetToken(ChatWebApiContext context, string username)
        {
            List<FirebaseUserToken> tokensList = context.FirebaseUserToken.ToList();

            foreach (FirebaseUserToken tuple in tokensList)
            {
                if (tuple.username.Equals(username))
                    return tuple.token;
            }
            return "";
        }

        public async Task<bool> SetToken(ChatWebApiContext context, string username, string token)
        {
            List<FirebaseUserToken> tokensList = context.FirebaseUserToken.ToList();
            foreach (FirebaseUserToken tuple in tokensList)
            {
                if (tuple.username.Equals(username))
                {
                    tuple.token = token;
                    context.SaveChanges();
                    return true;
                }
            }
            return false;
        }
    }
}