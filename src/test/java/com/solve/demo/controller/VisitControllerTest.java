package com.solve.demo.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solve.demo.domein.VisitStatus;
import com.solve.demo.dto.VisitFilter;
import com.solve.demo.dto.VisitReadDTO;
import com.solve.demo.service.VisitService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = VisitController.class)
@ActiveProfiles("test")
public class VisitControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VisitService visitService;


//test 123123
    @Test
    public void testGetVisits() throws Exception{
        VisitFilter visitFilter=new VisitFilter();
        visitFilter.setCustomerId(UUID.randomUUID());
        visitFilter.setMasterId(UUID.randomUUID());
        visitFilter.setStatuses(Set.of(VisitStatus.SCHEDULED,VisitStatus.FINISHED));
        visitFilter.setStartAtFrom(Instant.now());
        visitFilter.setStartAtTo(Instant.now());

        VisitReadDTO readDTO=new VisitReadDTO();
        readDTO.setCustomerId(visitFilter.getCustomerId());
        readDTO.setMasterId(visitFilter.getMasterId());
        readDTO.setStatus(VisitStatus.SCHEDULED);
        readDTO.setId(UUID.randomUUID());
        readDTO.setStartAt(Instant.now());
        readDTO.setEndAt(Instant.now());
        List<VisitReadDTO> expectedResult=List.of(readDTO);

        Mockito.when(visitService.getVisits(visitFilter)).thenReturn(expectedResult);

        String resultJson=mvc.perform(get("/api/v1/visits")
                .param("customerId",visitFilter.getCustomerId().toString())
                .param("masterId",visitFilter.getMasterId().toString())
                .param("statuses","SCHEDULED, FINISHED")
                .param("startAtFrom",visitFilter.getStartAtFrom().toString())
                .param("startAtTo",visitFilter.getStartAtTo().toString()))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        List<VisitReadDTO> actualResult= objectMapper.readValue(resultJson, new TypeReference<>() {});
        Assert.assertEquals(expectedResult,actualResult);
    }
}
