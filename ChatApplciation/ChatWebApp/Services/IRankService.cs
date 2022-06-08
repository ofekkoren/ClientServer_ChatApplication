using System;
using ChatWebApp.Models;
public interface IRankService
{

	/// <returns>All the ranks that has been submitted so far</returns>
	public List<Rank> GetAll();


	/// <param name="Username">Some username</param>
	/// <returns>The rank that has been submitted by the user or null of there is no such rank</returns>
	public Rank Get(string Username);

	/// <summary>
	/// Editing the numeral rank or feedback given in the Rank.
	/// </summary>
	public void Edit(string Username, int NumeralRank, string Feedback, string SubmitTime);

	/// <summary>
	/// Deleting the rank submitted by a user
	/// </summary>
	public void Delete(string Username);

	/// <summary>
	/// Adding a new rank
	/// </summary>
	/// <param name="Username">The user whu submitted the rank</param>
	/// <param name="NumeralRank">His rank in range between 1-5</param>
	/// <param name="Feedback">Optional textual feedback</param>
	public void Add(string Username, int NumeralRank, string Feedback);


	/// <returns>The average rank of the app</returns>
	public float Average();
}
