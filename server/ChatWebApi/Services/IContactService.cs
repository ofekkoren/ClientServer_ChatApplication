using ChatWebApi.Data;
using ChatWebApi.Models;

namespace ChatWebApi.Services
{
    public interface IContactService
    {
        public Task<List<ContactToJson>?> GetAll(ChatWebApiContext context, string username);

        public Task<Contact?> GetContact(ChatWebApiContext context, string username, string id);

        public Task<ContactToJson?> Get(ChatWebApiContext context, string username, string id);

        public Task<bool> Add(ChatWebApiContext context, string username, string id, string name, string server);

        public Task<bool> Delete(ChatWebApiContext context, string username, string id);

        public Task<bool> Edit(ChatWebApiContext context, string username, string id, string name, string server);
    }
}
