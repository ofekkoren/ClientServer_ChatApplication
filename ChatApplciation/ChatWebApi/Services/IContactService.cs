using ChatWebApi.Models;

namespace ChatWebApi.Services
{
    public interface IContactService
    {
        public List<ContactToJson>? GetAll(string username);

        public Contact? GetContact(string username, string id);

        public ContactToJson? Get(string username, string id);

        public bool Add(string username, string id, string name, string server);

        public bool Delete(string username, string id);

        public bool Edit(string username, string id, string name, string server);
    }
}
