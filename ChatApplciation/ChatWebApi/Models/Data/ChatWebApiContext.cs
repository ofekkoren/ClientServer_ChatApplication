using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using ChatWebApi.Models;

namespace ChatWebApi.Data
{
    public class ChatWebApiContext : DbContext
    {
        public ChatWebApiContext (DbContextOptions<ChatWebApiContext> options)
            : base(options)
        {
        }

        public DbSet<ChatWebApi.Models.Contact>? Contact { get; set; }

    }
}
