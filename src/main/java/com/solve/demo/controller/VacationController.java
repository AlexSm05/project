package com.solve.demo.controller;


import com.solve.demo.dto.VacationReadDTO;
import com.solve.demo.service.VacationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/master/{masterId}/vacations")
public class VacationController {

    @Autowired
    private VacationService vacationService;

    @GetMapping
    public List<VacationReadDTO> getMasterVacations(@PathVariable UUID masterId){
        return vacationService.getMasterVacations(masterId);
    }

}
