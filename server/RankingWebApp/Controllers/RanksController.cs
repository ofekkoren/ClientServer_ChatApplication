#nullable disable
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using ChatWebApp.Data;
using ChatWebApp.Models;
using ChatWebApp.Services;

namespace ChatWebApp.Controllers
{
    public class RanksController : Controller
    {
        private IRankService _service;
        private static ChatWebAppContext _context;

        public RanksController(ChatWebAppContext context)
        {
            _context = context;
            _service = new RankService();
        }

        // GET: Ranks
        public async Task<IActionResult> Index()
        {
            ViewBag.Ranks = await _service.Average(_context);
            return View(await _service.GetAll(_context));
        }

        // GET: Ranks/Details/5
        public async Task<IActionResult> Details(string Username)
        {
            if (Username == null)
                return NotFound();
            Rank rank = await _service.Get(_context, Username);
            if (rank == null)
                return BadRequest();
            return View(await _service.Get(_context, Username));
        }

        // GET: Ranks/Create
        public async Task<IActionResult> Create()
        {
            return View();
        }

        // POST: Ranks/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("Username,NumeralRank,Feedback")] Rank rank)
        {

            if (ModelState.IsValid)
            {
                if (await _service.Get(_context, rank.Username) != null)
                {
                    ModelState.AddModelError("Username", "This user already sent a feedback");
                    return View(rank);
                }
                _service.Add(_context, rank.Username, rank.NumeralRank, rank.Feedback);
                return RedirectToAction(nameof(Index));
            }
            return View(rank);
        }

        // GET: Ranks/Edit/5
        /*        public IActionResult Edit(string Username, int NumeralRank, string Feedback, string SubmitTime)
        */
        public async Task<IActionResult> Edit(string Username)
        {
            if (Username == null)
            {
                return NotFound();
            }
            return View(await _service.Get(_context, Username));
        }

        // POST: Ranks/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(string Username, [Bind("Username,NumeralRank,Feedback,SubmitTime")] Rank rank)
        {
            _service.Edit(_context, Username, rank.NumeralRank, rank.Feedback, rank.SubmitTime);
            return RedirectToAction(nameof(Index));
        }

        // GET: Ranks/Delete/5
        public async Task<IActionResult> Delete(string Username)
        {
            if (Username == null)
            {
                return NotFound();
            }

            var rank = await _service.Get(_context, Username);
            if (rank == null)
            {
                return NotFound();
            }

            return View(rank);
        }

        // POST: Ranks/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(string Username)
        {
            _service.Delete(_context, Username);
            return RedirectToAction(nameof(Index));
        }

        public async Task<IActionResult> Search(string query)
        {
            if (string.IsNullOrEmpty(query))
                return Json(await _service.GetAll(_context));
            List<Rank> results = new List<Rank>();
            List<Rank> ranks = await _service.GetAll(_context);
            int length = ranks.Count();
            for (int i = 0; i < length; i++)
            {
                if (ranks[i].Feedback != null && ranks[i].Feedback.Contains(query))
                    results.Add(ranks[i]);
            }
            return Json(results);
        }
    }
}
