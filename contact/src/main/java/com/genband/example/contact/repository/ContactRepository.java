package com.genband.example.contact.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.genband.example.contact.model.Contact;

@Repository(value = "ContactRepository")
public interface ContactRepository extends JpaRepository<Contact, Integer> {

    public List<Contact> findByName(String name);

}
