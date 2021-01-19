package com.solve.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solve.demo.dto.CustomerReadDTO;
import com.solve.demo.service.CustomerService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;



import java.util.UUID;
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
}
