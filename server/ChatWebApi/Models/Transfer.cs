using System.ComponentModel.DataAnnotations;

namespace ChatWebApi.Models
{
    public class Transfer
    {
        [Required]
        public string from { get; set; }

        [Required]
        public string to { get; set; }

        [Required]
        public string content { get; set; }

    }
}
