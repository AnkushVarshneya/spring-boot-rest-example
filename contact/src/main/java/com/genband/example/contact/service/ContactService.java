package com.genband.example.contact.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.genband.example.contact.model.Contact;
import com.genband.example.contact.repository.ContactRepository;

@Service
public class ContactService extends AbstractService<Contact, Integer> {

    private ContactRepository repository;

    @Autowired
    public ContactService(ContactRepository repository) {
        super(repository);

        this.repository = repository;
    }

    public List<Contact> findByName(String name) {
        return repository.findByName(name);
    }

    public Contact update(Contact contact) {
        Contact findOne = repository.getOne(contact.getId());

        if (findOne == null)
            return null;
        else {
            findOne.setName(contact.getName());
        }

        return repository.save(findOne);
    }

    public Contact updateById(int id, String updateName) {
        Contact findOne = repository.findOne(id);

        if (findOne == null)
            return null;
        else {
            findOne.setName(updateName);
        }

        return repository.save(findOne);
    }
}
