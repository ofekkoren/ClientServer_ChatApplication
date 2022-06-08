using System.ComponentModel.DataAnnotations;

namespace ChatWebApi.Models
{
    public class Conversation
    {
        [Key]
        public string ConversationId { get; set; }

        public List<Message>? messages { get; set; }

        [Required]
        public Contact contact { get; set; }

    }
}
