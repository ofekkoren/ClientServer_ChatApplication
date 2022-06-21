using ChatWebApi.Data;
using ChatWebApi.Models;

namespace ChatWebApi.Services
{

    public interface IConversationService
    {

        //getting the conversation of the currentuser with the user id
        public Task<Conversation?> GetConversation(ChatWebApiContext context, string Username ,string id);

        //api/contacts/:id/messages get
        //Getting all messages of the current with user id
        public Task<List<Message>?> GetAllMessages(ChatWebApiContext context, string Username, string id);

        //api/contacts/:id/messages post
        //Adds new message to the conversation with contact id
        public Task<bool> AddNewMessage(ChatWebApiContext context, String Username, String id, string content ,bool sent);

        //api/contacts/:id/messages/:id2 
        //Returns the message with id2(messageId) from the conversation with contact id(contactId)
        public Task<Message?> GetMessage(ChatWebApiContext context, String Username, string contactId, int messageId);


        //api/contacts/:id/messages/:id2 remove
        //delete the message with id2(messageId) from the conversation with contact id(contactId)
        public Task<bool> DeleteMessage(ChatWebApiContext context, String Username, string contactId, int messageId);

        //Creates a new conversation
        public Task<bool> Add(ChatWebApiContext context, String Username, Contact newContact);

        public Task<bool> EditMessage(ChatWebApiContext context, String Username, String id, int messageId,string content);

    }
}