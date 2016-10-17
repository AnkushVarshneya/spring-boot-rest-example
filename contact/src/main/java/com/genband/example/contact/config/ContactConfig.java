package com.genband.example.contact.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.genband.example.contact.model.Contact;
import com.genband.example.contact.repository.ContactRepository;

@Configuration
public class ContactConfig {

    @Autowired
    private ContactRepository contactRepository;

    @Bean
    public CommandLineRunner initData() {
        return (args) -> {
            contactRepository.save(new Contact("Chris"));
            contactRepository.save(new Contact("Sen"));
            contactRepository.save(new Contact("Hao"));
        };
    }
    /*
     * @Bean public DataSource dataSource() { return new
     * EmbeddedDatabaseBuilder() .setType(EmbeddedDatabaseType.HSQL)
     * .addScript("classpath:schema.sql") .addScript("classpath:test-data.sql")
     * .build(); }
     */

    /*
     * @Bean public JdbcTemplate getJdbcTemplate() { return new JdbcTemplate(new
     * EmbeddedDatabaseBuilder() .setType(EmbeddedDatabaseType.HSQL)
     * .addScript("classpath:schema.sql") .addScript("classpath:test-data.sql")
     * .build()); }
     */
}
