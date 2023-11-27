using System.ComponentModel.DataAnnotations;

namespace ChatWebApi.Models
{
    public class Invitation
    {
        [Required]
        public string from { get; set; }

        [Required]
        public string to { get; set; }

        [Required]
        public string server { get; set; }
    }
}
