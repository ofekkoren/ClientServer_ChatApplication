using System;
using ChatWebApp.Data;
using ChatWebApp.Models;

namespace ChatWebApp.Services
{
    public class RankService : IRankService
    {
/*        private static List<Rank> ranks = new List<Rank>();
*/
        public async Task<List<Rank>> GetAll(ChatWebAppContext context)
        {
            return context.Rank.ToList();
/*            return ranks;
*/        }

        public async Task<Rank?> Get(ChatWebAppContext context, string Username)
        {
            // returns null if username not found.
            if (Username != null && context.Rank != null)
            {
/*                return ranks.Find(x => x.Username.Equals(Username));
*/
                
                return context.Rank.ToList().Find(x => x.Username.Equals(Username));
            }
            return null;
        }

        public async void Edit(ChatWebAppContext context, string Username, int NumeralRank, string Feedback, string SubmitTime)
        {
            Rank rank = await Get(context, Username);
            if (rank != null)
            {
                rank.NumeralRank = NumeralRank;
                rank.Feedback = Feedback;
                rank.SubmitTime = SubmitTime;
                context.SaveChanges();
            }
        }

        public async void Delete(ChatWebAppContext context, string Username)
        {
            var rank = await Get(context, Username);
            if (rank != null)
            {
                context.Rank.Attach(rank);
                context.Rank.Remove(rank);
                context.SaveChanges();
                /*                ranks.Remove(context, rank);
                */
            }
        }

        public async void Add(ChatWebAppContext context, string Username, int NumeralRank, string Feedback)
        {
            if (await Get(context, Username) != null)
                return;
            Rank rank = new Rank { Username = Username, NumeralRank = NumeralRank, Feedback = Feedback };
            rank.SubmitTime = new (DateTime.Now.ToString("dd/MM/yyyy HH:mm"));
            context.Rank.Add(rank);
/*            ranks.Add(rank);
*/            context.SaveChanges();
        }


        public static string FormattedDateString(string dateString)
        {
            DateTime date=DateTime.Parse(dateString);
            string day = date.Day.ToString();
            string month = date.Month.ToString();
            string year = date.Year.ToString();
            string hour = date.Hour.ToString();
            if (date.Hour<10)
                hour = "0" + hour;
            string minutes = date.Minute.ToString();
            if(date.Minute<10)
                minutes = "0" + minutes;
            return day + "." + month + "." + year + ", " + hour + ":" + minutes;
        }

        public async Task<float> Average(ChatWebAppContext context)
        {
            float avg;
            if (context.Rank == null || context.Rank.ToList().Count() == 0)
                avg = 0;
            else
            {
                float sum = 0;
                for (int i = 0; i < context.Rank.ToList().Count(); i++)
                {
                    sum += context.Rank.ToList()[i].NumeralRank;         
                }
                avg = sum / context.Rank.ToList().Count();
            }
            return avg;

        }
    }
}