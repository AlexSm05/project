package com.solve.demo.controller;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import  static  org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static  org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HealthController.class)
@ActiveProfiles("test")
public class HealthControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void testHealth() throws Exception {
        mvc.perform(get("/health")).andExpect(status().isOk());
    }
}
