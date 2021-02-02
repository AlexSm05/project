package com.solve.demo.service;

import com.solve.demo.domein.Customer;
import com.solve.demo.dto.CustomerCreateDTO;
import com.solve.demo.dto.CustomerPatchDTO;
import com.solve.demo.dto.CustomerReadDTO;
import com.solve.demo.exeprions.EntityNotFoundExeprion;
import com.solve.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PatchMapping;

import java.util.UUID;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private TranslationService translationService;


    public CustomerReadDTO getCustomer(UUID id){
        Customer customer= getCustomerRequired(id);
        return translationService.toRead(customer);
    }

    public CustomerReadDTO createCustomer(CustomerCreateDTO create){
        Customer customer=translationService.toEntity(create);
        customer= customerRepository.save(customer);
        return translationService.toRead(customer);
    }

    @PatchMapping("/{id}")
    public CustomerReadDTO patchCustomer(UUID id, CustomerPatchDTO patch){
        Customer customer=getCustomerRequired(id);
        translationService.patchEntity(patch,customer);
        customer=customerRepository.save(customer);
        return translationService.toRead(customer);
    }

    public Customer getCustomerRequired(UUID id){
        return customerRepository.findById(id).orElseThrow(()->{
            throw new EntityNotFoundExeprion(Customer.class,id);
        });
    }

    public void deleteCustomer(UUID id){
        customerRepository.delete(getCustomerRequired(id));
    }


}
