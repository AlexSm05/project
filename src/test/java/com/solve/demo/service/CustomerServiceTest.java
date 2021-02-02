package com.solve.demo.service;


import com.solve.demo.domein.Customer;
import com.solve.demo.dto.CustomerCreateDTO;
import com.solve.demo.dto.CustomerPatchDTO;
import com.solve.demo.dto.CustomerReadDTO;
import com.solve.demo.exeprions.EntityNotFoundExeprion;
import com.solve.demo.repository.CustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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


    @Test(expected = EntityNotFoundExeprion.class)
    public void testGetCustomerWrongId(){
        customerService.getCustomer(UUID.randomUUID());
    }

    @Test
    public void testGetCustomer(){
        Customer customer=createCustomer();
        customer.setId(UUID.randomUUID());
        customer=customerRepository.save(customer);

        CustomerReadDTO readDTO= customerService.getCustomer(customer.getId());
        Assertions.assertThat(readDTO).isEqualToComparingFieldByField(customer);
    }

    @Test
    public void testCreateCustomer(){
        CustomerCreateDTO createDTO=new CustomerCreateDTO();
        createDTO.setName("qwe");
        createDTO.setPhone("23");
        CustomerReadDTO readDTO=customerService.createCustomer(createDTO);
        Assertions.assertThat(createDTO).isEqualToComparingFieldByField(readDTO);
        Assert.assertNotNull(readDTO.getId());

        Customer customer =customerRepository.findById(readDTO.getId()).get();
        Assertions.assertThat(readDTO).isEqualToComparingFieldByField(customer);
    }

    @Test
    public void testPatchCustomer(){
        Customer customer=createCustomer();

        CustomerPatchDTO patch=new CustomerPatchDTO();
        patch.setName("asde");
        patch.setPhone("76767");
        CustomerReadDTO read=customerService.patchCustomer(customer.getId(),patch);

        Assertions.assertThat(patch).isEqualToComparingFieldByField(read);

        customer=customerRepository.findById(read.getId()).get();
        Assertions.assertThat(customer).isEqualToComparingFieldByField(read);
    }

    @Test
    public void testPatchCustomerEmptyPatch(){
        Customer customer=createCustomer();


        CustomerPatchDTO patch=new CustomerPatchDTO();
        CustomerReadDTO read=customerService.patchCustomer(customer.getId(),patch);

        Assert.assertNotNull(read.getName());
        Assert.assertNotNull(read.getPhone());

        Customer customerAfterUpdate=customerRepository.findById(read.getId()).get();
        Assert.assertNotNull(customerAfterUpdate.getName());
        Assert.assertNotNull(customerAfterUpdate.getPhone());

        Assertions.assertThat(customer).isEqualToComparingFieldByField(customerAfterUpdate);
    }

    @Test
    public void testDeleteCustomer(){
        Customer customer=createCustomer();

        customerService.deleteCustomer(customer.getId());
        Assert.assertFalse(customerRepository.existsById(customer.getId()));
    }

    @Test(expected = EntityNotFoundExeprion.class)
    public void testDeleteCustomerNotFound(){
        customerService.deleteCustomer(UUID.randomUUID());
    }

    private Customer createCustomer(){
        Customer customer=new Customer();
        customer.setName("qwe");
        customer.setPhone("123");
        return customerRepository.save(customer);
    }
}
