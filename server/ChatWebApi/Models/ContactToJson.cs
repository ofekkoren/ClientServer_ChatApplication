using System.ComponentModel.DataAnnotations;

namespace ChatWebApi.Models
{
    public class ContactToJson
    {
        [Key]
        [Required]
        public string id { get; set; }

        [Required]
        public string name { get; set; }

        public string server { get; set; }

        public string last { get; set; }

        public string lastdate { get; set; }
    }
}
