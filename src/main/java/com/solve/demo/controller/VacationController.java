package com.solve.demo.controller;


import com.solve.demo.dto.VacationCreateDTO;
import com.solve.demo.dto.VacationReadDTO;
import com.solve.demo.service.VacationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/master/{masterId}/vacations")
public class VacationController {

    @Autowired
    private VacationService vacationService;

    @GetMapping
    public List<VacationReadDTO> getMasterVacations(@PathVariable UUID masterId) {
        return vacationService.getMasterVacations(masterId);
    }

    @PostMapping
    public VacationReadDTO createVacation(@PathVariable UUID masterId, VacationCreateDTO create) {
        return vacationService.createVacation(masterId, create);
    }

}
