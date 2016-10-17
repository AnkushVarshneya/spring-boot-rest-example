package com.genband.example.contact.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.genband.example.contact.model.Contact;
import com.genband.example.contact.service.ContactService;

@RestController
@RequestMapping("/contact")
public class ContactController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ContactService contactService;

    /**
     * search name
     * URL: localhost:8080/contact/{name}
     * used by address example
     * @param name
     * @return if found, return HTTPStatus.OK. Otherwise, return HttpStatus.NOT_FOUND.
     */
    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public ResponseEntity<?> checkByName(@PathVariable String name) {
        List<Contact> contacts = contactService.findByName(name);

        if (contacts != null && contacts.size() > 0) {
            logger.info("ContactController => checkByName => HTTP Response :" + HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            logger.info("ContactController => checkByName => HTTP Response :" + HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * search by id and return json object via GET
     * @param id
     * @return
     */
    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public Contact getById(@PathVariable int id) {
        return contactService.findOne(id);
    }

    /**
     * search by contact see if it's in the database.
     * @param contact
     * @return
     */
    @RequestMapping(value = "/check-contact", method = RequestMethod.POST)
    public ResponseEntity<?> isContactAvailable(@RequestBody Contact contact) {

        Contact findOne = contactService.findOne(contact.getId());
        if (findOne != null && findOne.getName().equals(contact.getName())) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * search all result
     * @return
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Contact> findAll() {
        return contactService.findAll();
    }

    /**
     * add contact by name
     * @param name
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public Contact addContact(@RequestParam String name) {
        return contactService.save(new Contact(name));
    }

    /**
     * add contact list
     * @TODO use transaction here 
     * @param contacts
     * @return
     */
    @RequestMapping(value = "/add-list", method = RequestMethod.POST)
    public ResponseEntity<?> addContact(@RequestBody List<Contact> contacts) {
        for (Contact contact : contacts) {
            Contact save = contactService.save(contact);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * update contact
     * @param contact
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<?> updateByID(@RequestBody Contact contact) {
        if (contactService.update(contact) != null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
