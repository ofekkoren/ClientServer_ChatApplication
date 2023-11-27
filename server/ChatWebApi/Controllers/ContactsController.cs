using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using ChatWebApi.Data;
using ChatWebApi.Models;
using ChatWebApi.Services;

namespace ChatWebApi.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class ContactsController : Controller
    {
        private static ChatWebApiContext _context;
        private IContactService _service;
        private IConversationService _conversationService;
        private readonly string redirectTo = "http://localhost:3000/";
        private readonly string currentUser = "currentUser";

        public ContactsController(ChatWebApiContext context)
        {
            _context = context;
            _service = new ContactService();
            _conversationService = new ConversationService();
        }


        // GET: Contacts
        [HttpGet]
        public async Task<IActionResult> Index()
        {
            if (HttpContext.Session.GetString(currentUser) == null)
                return Redirect(redirectTo);
            var lists = _context.User.ToList();
            if (await _service.GetAll(_context, HttpContext.Session.GetString(currentUser)) == null)
            {
                return NotFound();
            }
            return Json(await _service.GetAll(_context, HttpContext.Session.GetString(currentUser)));
        }

        // POST: Contacts
        [HttpPost]
        public async Task<IActionResult> Index([FromBody] ContactsPost parameters)
        {
            if (HttpContext.Session.GetString(currentUser) == null)
                return Redirect(redirectTo);
            if (HttpContext.Session.GetString(currentUser).Equals(parameters.id))
                return NotFound();
            if (_service == null)
                return NotFound();
            bool answer = await _service.Add(_context, HttpContext.Session.GetString(currentUser), parameters.id, parameters.name, parameters.server);
            if (answer == false)
                return NotFound();
            return StatusCode(201);
        }

        // GET: Contacts/id
        [HttpGet("{id}")]
        public async Task<IActionResult> Details(string id)
        {
            if (HttpContext.Session.GetString(currentUser) == null)
                return Redirect(redirectTo);
            if (id == null)
            {
                return NotFound();
            }
            Contact? contact = await _service.GetContact(_context, HttpContext.Session.GetString(currentUser), id);
            if (contact == null)
            {
                return NotFound();
            }
            return Json(contact);
        }

        // PUT: Contacts/id
        [HttpPut("{id}")]
        public async Task<IActionResult> Put(string id, [FromBody] ContactsIdPut parameters)
        {
            if (HttpContext.Session.GetString(currentUser) == null)
                return Redirect(redirectTo);
            if (await _service.Edit(_context, HttpContext.Session.GetString(currentUser), id, parameters.name, parameters.server) == false)
            {
                return NotFound();
            }
            return StatusCode(204);
        }

        // DELETE: Contacts/id
        [HttpDelete("{id}")]
        public async Task<IActionResult> Delete(string id)
        {
            if (HttpContext.Session.GetString(currentUser) == null)
                return Redirect(redirectTo);
            if (await _service.Delete(_context, HttpContext.Session.GetString(currentUser), id) == false)
            {
                return NotFound();
            }
            return StatusCode(204);
        }


        //GET Contacts/{id}/messages
        [HttpGet("{id}/messages")]
        public async Task<IActionResult> GetMessages(string id)
        {
            if (HttpContext.Session.GetString(currentUser) == null)
                return Redirect(redirectTo);
            List<Message> messages = await _conversationService.GetAllMessages(_context, HttpContext.Session.GetString(currentUser), id);
            if (messages == null)
            {
                return NotFound();
            }
            return Json(messages);
        }

        //Post Contacts/{id}/messages
        [HttpPost("{id}/messages")]
        public async Task<IActionResult> CreateNewMessage(string id, [FromBody] MessageContent parameter)
        {
            if (HttpContext.Session.GetString(currentUser) == null)
                return Redirect(redirectTo);
            if (await _conversationService.AddNewMessage(_context, HttpContext.Session.GetString(currentUser), id, parameter.content, true) == false)

            {
                return NotFound();
            }
            return StatusCode(201);
        }

        //GET Contacts/{id}/messages/{id2}
        [HttpGet("{id}/messages/{id2}")]
        public async Task<IActionResult> GetSpesificMessage(string id, int id2)
        {
            if (HttpContext.Session.GetString(currentUser) == null)
                return Redirect(redirectTo);
            Message message = await _conversationService.GetMessage(_context, HttpContext.Session.GetString(currentUser), id, id2);
            if (message == null)
            {
                return NotFound();
            }
            return Json(message);
        }

        //Put Contacts/{id}/messages/{id2}
        [HttpPut("{id}/messages/{id2}")]
        public async Task<IActionResult> EditSpesificMessage(string id, int id2, [FromBody] MessageContent parameter)
        {
            if (HttpContext.Session.GetString(currentUser) == null)
                return Redirect(redirectTo);
            if (await _conversationService.EditMessage(_context, HttpContext.Session.GetString(currentUser), id, id2, parameter.content) == false)
            {
                return NotFound();
            }
            return StatusCode(204);
        }

        //Delete Contacts/{id}/messages/{id2}
        [HttpDelete("{id}/messages/{id2}")]
        public async Task<IActionResult> EditSpesificMessage(string id, int id2)
        {
            if (HttpContext.Session.GetString(currentUser) == null)
                return Redirect(redirectTo);
            if (await _conversationService.DeleteMessage(_context, HttpContext.Session.GetString(currentUser), id, id2) == false)
            {
                return NotFound();
            }
            return StatusCode(204);
        }
        private bool ContactExists(int id)
        {
            return (_context.Contact?.Any(e => e.id == id)).GetValueOrDefault();
        }
    }
}
