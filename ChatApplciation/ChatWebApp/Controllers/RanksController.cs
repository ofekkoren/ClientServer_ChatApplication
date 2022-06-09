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

        public RanksController()
        {
            _service = new RankService();
        }

        // GET: Ranks
        public IActionResult Index()
        {
            ViewBag.Ranks = _service.Average();
            return View(_service.GetAll());
        }

        // GET: Ranks/Details/5
        public IActionResult Details(string Username)
        {
            if (Username == null)
                return NotFound();
            Rank rank = _service.Get(Username);
            if (rank == null)
                return BadRequest();
            return View(_service.Get(Username));
        }

        // GET: Ranks/Create
        public IActionResult Create()
        {
            return View();
        }

        // POST: Ranks/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public IActionResult Create([Bind("Username,NumeralRank,Feedback")] Rank rank)
        {

            if (ModelState.IsValid)
            {
                if (_service.Get(rank.Username) != null)
                {
                    ModelState.AddModelError("Username", "This user already sent a feedback");
                    return View(rank);
                }
                _service.Add(rank.Username, rank.NumeralRank, rank.Feedback);
                return RedirectToAction(nameof(Index));
            }
            return View(rank);
        }

        // GET: Ranks/Edit/5
        /*        public IActionResult Edit(string Username, int NumeralRank, string Feedback, string SubmitTime)
        */
        public IActionResult Edit(string Username)
        {
            if (Username == null)
            {
                return NotFound();
            }
            return View(_service.Get(Username));
        }

        // POST: Ranks/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public IActionResult Edit(string Username, [Bind("Username,NumeralRank,Feedback,SubmitTime")] Rank rank)
        {
            _service.Edit(Username, rank.NumeralRank, rank.Feedback, rank.SubmitTime);
            return RedirectToAction(nameof(Index));
        }

        // GET: Ranks/Delete/5
        public IActionResult Delete(string Username)
        {
            if (Username == null)
            {
                return NotFound();
            }

            var rank = _service.Get(Username);
            if (rank == null)
            {
                return NotFound();
            }

            return View(rank);
        }

        // POST: Ranks/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public IActionResult DeleteConfirmed(string Username)
        {
            _service.Delete(Username);
            return RedirectToAction(nameof(Index));
        }

        public async Task<IActionResult> Search(string query)
        {
            if (string.IsNullOrEmpty(query))
                return Json(_service.GetAll());
            List<Rank> results = new List<Rank>();
            List<Rank> ranks = _service.GetAll();
            int length = _service.GetAll().Count;
            for (int i = 0; i < length; i++)
            {
                if (ranks[i].Feedback != null && ranks[i].Feedback.Contains(query))
                    results.Add(ranks[i]);
            }
            return Json(results);
        }
    }
}
