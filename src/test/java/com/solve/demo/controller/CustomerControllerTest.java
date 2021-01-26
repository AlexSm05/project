package com.solve.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solve.demo.domein.Customer;
import com.solve.demo.dto.CustomerCreateDTO;
import com.solve.demo.dto.CustomerReadDTO;
import com.solve.demo.exeprions.EntityNotFoundExeprion;
import com.solve.demo.service.CustomerService;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static  org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@WebMvcTest
public class CustomerControllerTest  {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CustomerService customerService;
    @Test
    public void testGetCustomerWrongId() throws Exception{
        UUID wrongId=UUID.randomUUID();

        EntityNotFoundExeprion exeprion= new EntityNotFoundExeprion(Customer.class,wrongId);
        Mockito.when(customerService.getCustomer(wrongId)).thenThrow(exeprion);

        String resultJson=mvc.perform(get("/api/v1/customers/{id}",wrongId))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();
        Assert.assertTrue(resultJson.contains(exeprion.getMessage()));

    }

    @Test
    public void testGetCustomer() throws Exception{
        CustomerReadDTO customer = new CustomerReadDTO();
        customer.setId(UUID.randomUUID());
        customer.setName("Joe");
        customer.setPhone("23123");
        Mockito.when(customerService.getCustomer(customer.getId())).thenReturn(customer);
        String resultJson=mvc.perform(get("/api/v1/customers/{id}",customer.getId()))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(resultJson);
        CustomerReadDTO actualCustomer= objectMapper.readValue(resultJson,CustomerReadDTO.class);
        Assertions.assertThat(actualCustomer).isEqualToComparingFieldByField(customer);

        Mockito.verify(customerService).getCustomer(customer.getId());
    }
    @Test
    public void testCreateCustomer() throws Exception{
        CustomerCreateDTO createDTO=new CustomerCreateDTO();
        createDTO.setPhone("123");
        createDTO.setName("qwe");
        CustomerReadDTO readDTO=new CustomerReadDTO();
        readDTO.setId(UUID.randomUUID());
        readDTO.setName("wqe");
        readDTO.setPhone("23");

        Mockito.when(customerService.createCustomer(createDTO)).thenReturn(readDTO);

        String resultJson=mvc.perform(post("/api/v1/customers")
                .content(objectMapper.writeValueAsString(createDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        CustomerReadDTO actualCustomer=objectMapper.readValue(resultJson,CustomerReadDTO.class);
        Assertions.assertThat(actualCustomer).isEqualToComparingFieldByField(readDTO);



    }
}
