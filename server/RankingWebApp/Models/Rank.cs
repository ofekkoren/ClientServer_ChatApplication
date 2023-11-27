using System.ComponentModel;
using System.ComponentModel.DataAnnotations;

namespace ChatWebApp.Models
{
    public class Rank
    {
        [Key]
        [DisplayName("User name")]
        [Required]
        public string Username { get; set; }

        [Required]
        [DisplayName("Rank")]
        [Range(1,5, ErrorMessage="The rank must be between 1 and 5")]
        public int NumeralRank { get; set; }

        public string? Feedback { get; set; }

        [DisplayName("Time")]
        public string? SubmitTime { get; set; }         
    }
}
