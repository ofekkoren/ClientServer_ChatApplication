using System.ComponentModel.DataAnnotations;

namespace ChatWebApi.Models
{
    public class FirebaseUserToken
    {
        [Key]
        [Required]
        public string username { get; set; }

        [Required]
        public string? token { get; set; }
    }
}
