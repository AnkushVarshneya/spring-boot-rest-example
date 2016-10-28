package com.genband.example.address.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.genband.example.address.model.Address;
import com.genband.example.address.repository.AddressRepository;

@Configuration
public class AddressConfig {

  @Autowired
  private AddressRepository repository;

  /**
   * Use CommandLineRunner to load the data
   * 
   * @TODO for address, use sql schema to create database.
   * @return
   */
  @Bean
  public CommandLineRunner initAddress() {

    return (args) -> {
      repository.save(new Address("Chris", "genband"));
      repository.save(new Address("Sen", "kanata"));
      repository.save(new Address("Hao", "ottawa"));
      repository.save(new Address("Ha2o", "ottawa"));
      repository.save(new Address("Ha3o", "ottawa"));
    };
  }
}
