using System;
using ChatWebApp.Models;

namespace ChatWebApp.Services
{
    public class RankService : IRankService
    {
        private static List<Rank> ranks = new List<Rank>();

        public List<Rank> GetAll()
        {
            return ranks;
        }

        public Rank Get(string Username)
        {
            // returns null if username not found.
            if (Username != null && ranks != null)
            {
                return ranks.Find(x => x.Username.Equals(Username));
            }
            return null;
        }

        public void Edit(string Username, int NumeralRank, string Feedback, string SubmitTime)
        {
            Rank rank = Get(Username);
            if (rank != null)
            {
                rank.NumeralRank = NumeralRank;
                rank.Feedback = Feedback;
                rank.SubmitTime = SubmitTime;
            }
        }

        public void Delete(string Username)
        {
            var rank = Get(Username);
            if (rank != null)
            {
                ranks.Remove(rank);
            }
        }

        public void Add(string Username, int NumeralRank, string Feedback)
        {
            if (Get(Username) != null)
                return;
            Rank rank = new Rank { Username = Username, NumeralRank = NumeralRank, Feedback = Feedback };
            rank.SubmitTime = new (DateTime.Now.ToString("dd/MM/yyyy HH:mm"));
            ranks.Add(rank);
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

        public float Average()
        {
            float avg;
            if (ranks == null || ranks.Any() == false)
                avg = 0;
            else
            {
                float sum = 0;
                for (int i = 0; i < ranks.Count(); i++)
                {
                    sum += ranks[i].NumeralRank;         
                }
                avg = sum / ranks.Count();
            }
            return avg;

        }
    }
}