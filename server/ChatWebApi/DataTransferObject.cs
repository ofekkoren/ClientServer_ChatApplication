using ChatWebApi.Models;
using Microsoft.AspNetCore.Mvc;

namespace ChatWebApi
{
    /*    public class DataTransferObject
        {*/
    public class LogInParameters
    {
        public string? username { get; set; }
        public string? password { get; set; }
    }

    public class SignInparameters
    {
        public string username { get; set; }
        public string nickname { get; set; }
        public string password { get; set; }
        public string repeatPassword { get; set; }
    }

    public class ContactsPost
    {
        public string id { get; set; }
        public string name { get; set; }
        public string server { get; set; }
    }

    public class IdClass
    {
        public string id { get; set; }
    }

    public class ContactsIdPut
    {
        public string name { get; set; }
        public string server { get; set; }
    }

    public class MessageContent
    {
        public string content { get; set; }
    }

    public class ParametersForMoveConversation
    {
        public string id { get; set; }
        public string username { get; set; }
    }

    public class ParametersForInvitation
    {
        public string from { get; set; }
        public string to { get; set; }
        public string fromServer { get; set; }
    }

    public class ParametersForTransfer
    {
        public string from { get; set; }
        public string to { get; set; }
        public string content { get; set; }
    }

    public class ParametersForSendAsyncNewMessage
    {
        public string from { get; set; }
        public string to { get; set; }

        public int id { get; set; }
        public string content { get; set; }

        public string created { get; set; }

        public bool sent { get; set; }

    }


    public class ParametersForSendAsyncNewContact
    {
        public string to { get; set; }

        public JsonResult conversations {get;set;}

    }
}
