using System.ComponentModel.DataAnnotations;

namespace ChatWebApi.Models
{
    public class User
    {
        [Key]
        [Required]
        public string id { get; set; }

        [Required]
        public string name { get; set; }

        [Required]
        public string password { get; set; }

        public List<Conversation> conversations { get; set; }

    }
}
