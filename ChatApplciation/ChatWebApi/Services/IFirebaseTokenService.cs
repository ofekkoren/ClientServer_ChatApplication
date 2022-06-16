namespace ChatWebApi.Services
{
    public interface IFirebaseTokenService
    {
        void AddUser(string username);
        string? GetToken(string username);

        bool SetToken(string username, string token);
    }
}
