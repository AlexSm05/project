package com.solve.demo.service;


import com.solve.demo.domein.Customer;
import com.solve.demo.dto.CustomerReadDTO;
import com.solve.demo.repository.CustomerRepository;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Sql(statements = "delete from customer",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CustomerServiceTest {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private  CustomerService customerService;
    @Test
    public void testGetCustomer(){
        Customer customer=new Customer();
        customer.setId(UUID.randomUUID());
        customer.setName("Joe");
        customer.setPhone("123");
        customer=customerRepository.save(customer);

        CustomerReadDTO readDTO= customerService.getCustomer(customer.getId());
        Assertions.assertThat(readDTO).isEqualToComparingFieldByField(customer);
    }
}
