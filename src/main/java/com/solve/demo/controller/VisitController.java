package com.solve.demo.controller;

import com.solve.demo.domein.Visit;
import com.solve.demo.dto.VisitExtendedReadDTO;
import com.solve.demo.dto.VisitFilter;
import com.solve.demo.dto.VisitReadDTO;
import com.solve.demo.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = {"/api/v1/visit","/api/v1/visits"})
public class VisitController {

    @Autowired
    private VisitService visitService;

    @GetMapping("/{id}")
    public VisitExtendedReadDTO getVisit(@PathVariable UUID id){
       return visitService.getVisit(id);
    }

    @GetMapping()
    public List<VisitReadDTO> getVisits(VisitFilter filter){
        return visitService.getVisits(filter);
    }

}
