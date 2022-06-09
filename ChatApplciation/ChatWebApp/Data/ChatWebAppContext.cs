using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using ChatWebApp.Models;

namespace ChatWebApp.Data
{
    public class ChatWebAppContext : DbContext
    {
        public ChatWebAppContext (DbContextOptions<ChatWebAppContext> options)
            : base(options)
        {
        }

        public DbSet<ChatWebApp.Models.Rank>? Rank { get; set; }
    }
}
