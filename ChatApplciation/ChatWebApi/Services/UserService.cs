using ChatWebApi.Models;

namespace ChatWebApi.Services
{
    public class UserService : IUserService
    {
        private static List<User> users = new List<User>();

        public UserService()
        {
            if (users.Count == 0)
            {
                Conversation conversationOfekTomer = new Conversation()
                {
                    ConversationId = "Ofek KorenTomer Eligayev"
                ,

                    contact = new Contact() { id = 0, username = "Tomer Eligayev", name = "TOMER-77", last = "Have a nice day", lastdate = "2022-05-26T03:53:23.8120000", server = "localhost:5170" },
                    messages = new List<Message>() { new Message() { id = 1, content = "Hello, how are you?", created = "2022-05-26T03:53:23.8120000", sent =   true }
                ,new Message(){ id = 2, content = "I'm fine, thanks", created = "2022-05-26T03:53:23.8120000", sent =   false }
                ,new Message(){ id = 3, content = "Have a nice day.", created = "2022-05-26T03:53:23.8120000", sent =   false }}
                };

                Conversation conversationTomerOfek = new Conversation()
                {
                    ConversationId = "Tomer EligayevOfek Koren",
                    contact = new Contact() { id = 1, username = "Ofek Koren", name = "ofekkoren", last = "Have a nice day", lastdate = "2022-05-26T03:53:23.8120000", server = "localhost:5170" },
                    messages = new List<Message>() { new Message() { id = 4, content = "Hello, how are you?", created = "2022-05-26T03:53:23.8120000", sent = false }
                ,new Message(){ id = 5, content = "I'm fine, thanks", created = "2022-05-26T03:53:23.8120000", sent =   true }
                ,new Message(){ id = 6, content = "Have a nice day.", created = "2022-05-26T03:53:23.8120000", sent =   true }}
                };

                Conversation conversationTomerMoti = new Conversation()
                {
                    ConversationId = "Tomer EligayevMoti Luhim",
                    contact = new Contact() { id = 2, username = "Moti Luhim", name = "Moti Luhim", last = "Do you know you Misha Al?", lastdate = "2022-05-26T03:53:23.8120000", server = "localhost:5170" },
                    messages = new List<Message>()  { new Message() { id = 7, content = "You have a funny name!", created = "2022-05-26T03:53:23.8120000", sent = true },
                        new Message() { id = 8, content = "I know, my parents don't like me :(", created = "2022-05-26T03:53:23.8120000", sent = false },
                        new Message() { id = 9, content = "Do you know you Misha Al?", created = "2022-05-26T03:53:23.8120000", sent = true }

                    }
                };

                Conversation conversationMotiTomer = new Conversation()
                {
                    ConversationId = "Moti LuhimTomer Eligayev",
                    contact = new Contact() { id = 3, username = "Tomer Eligayev", name = "TOMER-77", last = "Do you know you Misha Al?", lastdate = "2022-05-26T03:53:23.8120000", server = "localhost:5170" },
                    messages = new List<Message>()  { new Message() { id = 10, content = "You have a funny name!", created = "2022-05-26T03:53:23.8120000", sent = false },
                        new Message() { id = 11, content = "I know, my parents don't like me :(", created = "2022-05-26T03:53:23.8120000", sent = true },
                        new Message() { id = 12, content = "Do you know you Misha Al?", created = "2022-05-26T03:53:23.8120000", sent = false }

                    }
                };

                Conversation conversationAviOfek = new Conversation()
                {
                    ConversationId = "Avi CohenOfek Koren",
                    contact = new Contact() { id = 4, username = "Ofek Koren", name = "ofekkoren", last = "No, but we can :)", lastdate = "2022-05-26T03:53:23.8120000", server = "localhost:5170" },
                    messages = new List<Message>()  { new Message() { id = 13, content = "Heyyy, want to eat icecream?", created = "2022-05-26T03:53:23.8120000", sent = true },
                        new Message() { id = 14, content = "I'm fine, thanks", created = "2022-05-26T03:53:23.8120000", sent = false },
                        new Message() { id = 15, content = "Do I know you?", created = "2022-05-26T03:53:23.8120000", sent = false },
                        new Message() { id = 16, content = "No, but we can :)", created = "2022-05-26T03:53:23.8120000", sent = true }
                    }
                };

                Conversation conversationOfekAvi = new Conversation()
                {
                    ConversationId = "Ofek KorenAvi Cohen",
                    contact = new Contact() { id = 5, username = "Avi Cohen", name = "Avi", last = "No, but we can :)", lastdate = "2022-05-26T03:53:23.8120000", server = "localhost:5170" },
                    messages = new List<Message>()  { new Message() { id = 17, content = "Heyyy, want to eat icecream?", created = "2022-05-26T03:53:23.8120000", sent = false },
                        new Message() { id = 18, content = "I'm fine, thanks", created = "2022-05-26T03:53:23.8120000", sent = true },
                        new Message() { id = 19, content = "Do I know you?", created = "2022-05-26T03:53:23.8120000", sent = true },
                        new Message() { id = 20, content = "No, but we can :)", created = "2022-05-26T03:53:23.8120000", sent = false }
                    }
                };

                //todo
                Conversation conversationAviTomer = new Conversation()
                {
                    ConversationId = "Avi CohenTomer Eligayev",
                    contact = new Contact() { id = 6, username = "Tomer Eligayev", name = "TOMER-77", last = "Bye have a nice life", lastdate = "2022-05-26T03:53:23.8120000", server = "localhost:5170" },
                    messages = new List<Message>()  { new Message() { id = 21, content = "Hey dood", created = "2022-05-26T03:53:23.8120000", sent = false },
                        new Message() { id = 22, content = "Hola", created = "2022-05-26T03:53:23.8120000", sent = true },
                        new Message() { id = 23, content = "Bye have a nice life", created = "2022-05-26T03:53:23.8120000", sent = false },
                    }
                };

                Conversation conversationTomerAvi = new Conversation()
                {
                    ConversationId = "Tomer EligayevAvi Cohen",
                    contact = new Contact() { id = 7, username = "Avi Cohen", name = "Avi", last = "Bye have a nice life", lastdate = "2022-05-26T03:53:23.8120000", server = "localhost:5170" },
                    messages = new List<Message>()  { new Message() { id = 24, content = "Hey dood", created = "2022-05-26T03:53:23.8120000", sent = true },
                        new Message() { id = 25, content = "Hola", created = "2022-05-26T03:53:23.8120000", sent = false },
                        new Message() { id = 26, content = "Bye have a nice life", created = "2022-05-26T03:53:23.8120000", sent = true },
                    }
                };

                List<Conversation> conversationsO = new List<Conversation>() { conversationOfekTomer, conversationOfekAvi };
                List<Conversation> conversationsT = new List<Conversation>() { conversationTomerOfek, conversationTomerMoti, conversationTomerAvi };
                List<Conversation> conversationsM = new List<Conversation>() { conversationMotiTomer };
                List<Conversation> conversationsA = new List<Conversation>() { conversationAviOfek, conversationAviTomer };

                users.Add(new User() { id = "Ofek Koren", name = "Ofekkoren", password = "123456K", conversations = conversationsO });
                users.Add(new User() { id = "Tomer Eligayev", name = "Tomer-77", password = "123456E", conversations = conversationsT });
                users.Add(new User() { id = "Moti Luhim", name = "Moti Luhim", password = "123456L", conversations = conversationsM });
                users.Add(new User() { id = "Avi Cohen", name = "Avi", password = "123456C", conversations = conversationsA });
                users.Add(new User() { id = "Shir Levi", name = "Shirus", password = "123456L", conversations =  new List<Conversation>() });

            }
        }

        /*
         * Adding new user to the db.
         */
        public void AddUser(string id, string name, string password)
        {
            User newUser = new User { id = id, name = name, password = password, conversations = new List<Conversation>() };
            users.Add(newUser);
        }

        /*
         * Getting the user with the given id from the db.
         */
        public User? GetUser(string id)
        {
            if (id == null)
                return null;
            return users.Find(user => user.id.Equals(id));
        }

        /*
         * Getting the list of conversations of user with the given username
         */
        public List<Conversation>? GetAllConversations(string username)
        {
            if (username == null)
                return null;
            User? user = GetUser(username);
            if (user == null)
                return null;
            return user.conversations;
        }
    }
}
