package com.solve.demo.controller;

import com.solve.demo.dto.VisitExtendedReadDTO;
import com.solve.demo.dto.VisitReadDTO;
import com.solve.demo.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/visit")
public class VisitController {

    @Autowired
    private VisitService visitService;

    @GetMapping
    public VisitExtendedReadDTO getVisit(@PathVariable UUID id){
       return visitService.getVisit(id);
    }

}
