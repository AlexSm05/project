package com.solve.demo.service;

import com.solve.demo.domein.Customer;
import com.solve.demo.dto.CustomerReadDTO;
import com.solve.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.UUID;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public CustomerReadDTO getCustomer(UUID id){
        Customer customer= customerRepository.findById(id).get();
        return toRead(customer);
    }

    private CustomerReadDTO toRead(Customer customer) {
        CustomerReadDTO dto= new CustomerReadDTO();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setPhone(customer.getPhone());
        return dto;
    }
}
