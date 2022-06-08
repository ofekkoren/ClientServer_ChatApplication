using System.ComponentModel.DataAnnotations;

namespace ChatWebApi.Models
{
    public class Contact
    {
        [Key]
        [Required]
        public int id { get; set; }

        public string username { get; set; }

        [Required]
        public string name { get; set; }

        public string server { get; set; }

        public string last { get; set; }

        public string lastdate { get; set; }

    }
}
