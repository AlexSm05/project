package com.solve.demo.repository;

import com.solve.demo.domein.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(statements = "delete from customer",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void testSave() {
        Customer c = new Customer();
        c = customerRepository.save(c);
        assertNotNull(c.getId());
        assertTrue(customerRepository.findById(c.getId()).isPresent());

    }
}


