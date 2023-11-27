using System;
using ChatWebApp.Data;
using ChatWebApp.Models;
public interface IRankService
{

	/// <returns>All the ranks that has been submitted so far</returns>
	public Task<List<Rank>> GetAll(ChatWebAppContext context);


	/// <param name="Username">Some username</param>
	/// <returns>The rank that has been submitted by the user or null of there is no such rank</returns>
	public Task<Rank> Get(ChatWebAppContext context, string Username);

	/// <summary>
	/// Editing the numeral rank or feedback given in the Rank.
	/// </summary>
	public void Edit(ChatWebAppContext context, string Username, int NumeralRank, string Feedback, string SubmitTime);

	/// <summary>
	/// Deleting the rank submitted by a user
	/// </summary>
	public void Delete(ChatWebAppContext context, string Username);

	/// <summary>
	/// Adding a new rank
	/// </summary>
	/// <param name="Username">The user whu submitted the rank</param>
	/// <param name="NumeralRank">His rank in range between 1-5</param>
	/// <param name="Feedback">Optional textual feedback</param>
	public void Add(ChatWebAppContext context, string Username, int NumeralRank, string Feedback);

	public Task<float> Average(ChatWebAppContext context);
	/// <returns>The average rank of the app</returns>
	/*	public static string FormattedDateString(string dateString);*/
}
