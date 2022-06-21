using ChatWebApi.Data;
using ChatWebApi.Models;

namespace ChatWebApi.Services
{
    public class FirebaseTokenService : IFirebaseTokenService
    {
/*        private static List<FirebaseUserToken> tokensList = new List<FirebaseUserToken>();
*/
        public FirebaseTokenService()
        {
/*            if (tokensList.Count == 0)
            {
                tokensList.Add(new FirebaseUserToken() { username = "Ofek Koren", token = null });
                tokensList.Add(new FirebaseUserToken() { username = "Tomer Eligayev", token = null });
                tokensList.Add(new FirebaseUserToken() { username = "Moti Luhim", token = null });
                tokensList.Add(new FirebaseUserToken() { username = "Avi Cohen", token = null });
                tokensList.Add(new FirebaseUserToken() { username = "Shir Levi", token = null });
            }
*/        }

        public async void AddUser(ChatWebApiContext context, string username)
        {
            context.FirebaseUserToken.Add(new FirebaseUserToken() { username = username, token = "" });
            context.SaveChanges();
            /*            tokensList.Add(new FirebaseUserToken() { username = username, token = null });
            */
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
