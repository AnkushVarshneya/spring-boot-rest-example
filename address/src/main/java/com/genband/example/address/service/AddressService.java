package com.genband.example.address.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.genband.example.address.model.Address;
import com.genband.example.address.repository.AddressRepository;

@Service
public class AddressService extends AbstractService<Address, Integer> {

    private AddressRepository repository;

    @Autowired
    public AddressService(AddressRepository repository) {
        super(repository);

        this.repository = repository;
    }

    public List<Address> findByContactName(String contactName) {
        return repository.findByContactName(contactName);
    }
}
