using System.ComponentModel.DataAnnotations;

namespace ChatWebApi.Models
{
    public class Conversation
    {
        [Key]
        public string ConversationId { get; set; }

        public virtual List<Message>? messages { get; set; }

        [Required]
        public Contact contact { get; set; }

    }
}
