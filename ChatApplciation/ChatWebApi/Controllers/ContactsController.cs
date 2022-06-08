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
        private IContactService _service;
        private IConversationService _conversationService;
        private readonly string redirectTo = "http://localhost:3000/";
        private readonly string currentUser = "currentUser";


        public ContactsController()
        {
            _service = new ContactService();
            _conversationService = new ConversationService();
        }


        // GET: Contacts
        [HttpGet]
        public IActionResult Index()
        {
            if (HttpContext.Session.GetString(currentUser) ==null)
                return Redirect(redirectTo);
            if (_service.GetAll(HttpContext.Session.GetString(currentUser)) == null)
            {
                return NotFound();
            }
            return Json(_service.GetAll(HttpContext.Session.GetString(currentUser)));
        }


        // POST: Contacts
        [HttpPost]
        public IActionResult Index([FromBody] ContactsPost parameters)
        {
            if (HttpContext.Session.GetString(currentUser) == null)
                return Redirect(redirectTo);
            if (HttpContext.Session.GetString(currentUser).Equals(parameters.id))
                return NotFound();
            if (_service.Add(HttpContext.Session.GetString(currentUser), parameters.id,  parameters.name, parameters.server) == false)
                return NotFound();
            return StatusCode(201);
        }

        // GET: Contacts/id
        [HttpGet("{id}")]
        public IActionResult Details(string id)
        {
            if (HttpContext.Session.GetString(currentUser) == null)
                return Redirect(redirectTo);
            if (id == null)
            {
                return NotFound();
            }
            Contact? contact = _service.GetContact(HttpContext.Session.GetString(currentUser), id);
            if (contact == null)
            {
                return NotFound();
            }
            return Json(contact);
        }

        // PUT: Contacts/id
        [HttpPut("{id}")]
        public IActionResult Put(string id, [FromBody] ContactsIdPut parameters)
        {
            if (HttpContext.Session.GetString(currentUser) == null)
                return Redirect(redirectTo);
            if (_service.Edit(HttpContext.Session.GetString(currentUser), id, parameters.name, parameters.server) == false)
            {
                return NotFound();
            }
            return StatusCode(204);
        }

        // DELETE: Contacts/id
        [HttpDelete("{id}")]
        public IActionResult Delete(string id)
        {
            if (HttpContext.Session.GetString(currentUser) == null)
                return Redirect(redirectTo);
            if (_service.Delete(HttpContext.Session.GetString(currentUser), id) == false)
            {
                return NotFound();
            }
            return StatusCode(204);
        }


        //GET Contacts/{id}/messages
        [HttpGet("{id}/messages")]
        public IActionResult GetMessages(string id)
        {
            if (HttpContext.Session.GetString(currentUser) == null)
                return Redirect(redirectTo);
            List<Message> messages = _conversationService.GetAllMessages(HttpContext.Session.GetString(currentUser), id);
            if (messages == null)
            {
                return NotFound();
            }
            return Json(messages);
        }

        //Post Contacts/{id}/messages
        [HttpPost("{id}/messages")]
        public IActionResult CreateNewMessage(string id, [FromBody] MessageContent parameter) //todo - check it receives id
        {
            if (HttpContext.Session.GetString(currentUser) == null)
                return Redirect(redirectTo);
            if (_conversationService.AddNewMessage(HttpContext.Session.GetString(currentUser), id, parameter.content,true) == false)

            {
                return NotFound();
            }
            return StatusCode(201);
        }

        //GET Contacts/{id}/messages/{id2}
        [HttpGet("{id}/messages/{id2}")]
        public IActionResult GetSpesificMessage(string id, int id2)
        {
            if (HttpContext.Session.GetString(currentUser) == null)
                return Redirect(redirectTo);
            Message message = _conversationService.GetMessage(HttpContext.Session.GetString(currentUser), id, id2);
            if (message == null)
            {
                return NotFound();
            }
            return Json(message);
        }

        //Put Contacts/{id}/messages/{id2}
        [HttpPut("{id}/messages/{id2}")]
        public IActionResult EditSpesificMessage(string id, int id2, [FromBody] MessageContent parameter)
        {
            if (HttpContext.Session.GetString(currentUser) == null)
                return Redirect(redirectTo);
            if (_conversationService.EditMessage(HttpContext.Session.GetString(currentUser), id, id2, parameter.content) == false)
            {
                return NotFound();
            }
            return StatusCode(204);
        }

        //Delete Contacts/{id}/messages/{id2}
        [HttpDelete("{id}/messages/{id2}")]
        public IActionResult EditSpesificMessage(string id, int id2)
        {
            if (HttpContext.Session.GetString(currentUser) == null)
                return Redirect(redirectTo);
            if (_conversationService.DeleteMessage(HttpContext.Session.GetString(currentUser), id, id2) == false)
            {
                return NotFound();
            }
            return StatusCode(204);
        }
    }
}
