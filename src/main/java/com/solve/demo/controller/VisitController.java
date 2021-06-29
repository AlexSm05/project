package com.solve.demo.controller;


import com.solve.demo.dto.VisitCreateDTO;
import com.solve.demo.dto.VisitExtendedReadDTO;
import com.solve.demo.dto.VisitFilter;
import com.solve.demo.dto.VisitReadDTO;
import com.solve.demo.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = {"/api/v1/visit", "/api/v1/visits"})
public class VisitController {

    @Autowired
    private VisitService visitService;

    @GetMapping("/{id}")
    public VisitExtendedReadDTO getVisit(@PathVariable UUID id) {
        return visitService.getVisit(id);
    }

    @GetMapping()
    public List<VisitReadDTO> getVisits(VisitFilter filter) {
        return visitService.getVisits(filter);
    }

    @PostMapping
    public VisitReadDTO createVisit(@RequestBody VisitCreateDTO create) {
        return visitService.createVisit(create);
    }


}
