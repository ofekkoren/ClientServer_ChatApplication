using ChatWebApi.Models;

namespace ChatWebApi.Services
{

    public interface IConversationService
    {

        //getting the conversation of the currentuser with the user id
        public Conversation? GetConversation(string Username ,string id);

        //api/contacts/:id/messages get
        //Getting all messages of the current with user id
        public List<Message>? GetAllMessages(string Username, string id);

        //api/contacts/:id/messages post
        //Adds new message to the conversation with contact id
        public bool AddNewMessage(String Username, String id, string content ,bool sent);

        //api/contacts/:id/messages/:id2 
        //Returns the message with id2(messageId) from the conversation with contact id(contactId)
        public Message? GetMessage(String Username, string contactId, int messageId);


        //api/contacts/:id/messages/:id2 remove
        //delete the message with id2(messageId) from the conversation with contact id(contactId)
        public bool DeleteMessage(String Username, string contactId, int messageId);

        //Creates a new conversation
        public bool Add(String Username, Contact newContact);

        public bool EditMessage(String Username, String id, int messageId,string content);

    }
}