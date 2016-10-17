package com.genband.example.address.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.genband.example.address.model.Address;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AddressRepostoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AddressRepository addressRepository;

    @Before
    public void setup() {

    }

    @Test
    public void testFindByContactName() {
        this.entityManager.persist(new Address("kuanghao", "genband"));
        List<Address> findByContactName = addressRepository.findByContactName("kuanghao");
        assertNotNull(findByContactName);
        assertThat(findByContactName.size()).isEqualTo(1);
        assertThat(findByContactName.get(0).getContactName()).isEqualTo("kuanghao");
        assertThat(findByContactName.get(0).getAddressName()).isEqualTo("genband");
    }
}
