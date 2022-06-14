using ChatWebApi.Models;

namespace ChatWebApi.Services
{
    public class FirebaseTokenService : IFirebaseTokenService
    {
        private static List<FirebaseUserToken> tokensList = new List<FirebaseUserToken>();

        public FirebaseTokenService()
        {
            if (tokensList.Count == 0)
            {
                tokensList.Add(new FirebaseUserToken() { username = "Ofek Koren", token = null });
                tokensList.Add(new FirebaseUserToken() { username = "Tomer Eligayev", token = null });
                tokensList.Add(new FirebaseUserToken() { username = "Moti Luhim", token = null });
                tokensList.Add(new FirebaseUserToken() { username = "Avi Cohen", token = null });
                tokensList.Add(new FirebaseUserToken() { username = "Shir Levi", token = null });
            }
        }

        public void AddUser(string username)
        {
            tokensList.Add(new FirebaseUserToken() { username = username, token = null });
        }

        public string? GetToken(string username)
        {
            foreach (FirebaseUserToken tuple in tokensList)
            {
                if (tuple.username.Equals(username))
                    return tuple.token;
            }
            return null;
        }

        public bool SetToken(string username, string token)
        {
            foreach (FirebaseUserToken tuple in tokensList)
            {
                if (tuple.username.Equals(username))
                {
                    tuple.token = token;
                    return true;
                }
            }
            return false;
        }
    }
}
