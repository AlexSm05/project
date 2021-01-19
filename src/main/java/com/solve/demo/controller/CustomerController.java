package com.solve.demo.controller;

import com.solve.demo.domein.Customer;
import com.solve.demo.dto.CustomerReadDTO;
import com.solve.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @GetMapping("/{id}")
    public CustomerReadDTO getCustomer(@PathVariable UUID id){
        return customerService.getCustomer(id);
    }
}
