package com.genband.example.address.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.genband.example.address.model.Address;

 /**
 * reference JPQL
 * http://docs.oracle.com/html/E13946_05/ejb3_langref.html
 */
public interface AddressRepository extends JpaRepository<Address, Integer> {
    public List<Address> findByContactName(String contactName);

    public List<Address> findByAddressName(String addressName);

    @Query("select a from Address a where a.addressName like %?1")
    public List<Address> findByAddressNameEndsWith(String addressName);
}


//TODO repositpory test