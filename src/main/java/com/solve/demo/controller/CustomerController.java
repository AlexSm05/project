package com.solve.demo.controller;

import com.solve.demo.domein.Customer;
import com.solve.demo.dto.CustomerCreateDTO;
import com.solve.demo.dto.CustomerPatchDTO;
import com.solve.demo.dto.CustomerReadDTO;
import com.solve.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping
    public CustomerReadDTO createCustomer(@RequestBody CustomerCreateDTO createDTO){
        return customerService.createCustomer(createDTO);
    }

    @PatchMapping("/{id}")
    public CustomerReadDTO patchCustomer(@PathVariable UUID id, @RequestBody CustomerPatchDTO patch){
        return customerService.patchCustomer(id,patch);
    }
    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable UUID id){
        customerService.deleteCustomer(id);
    }
}
