package com.solve.demo.service;

import com.solve.demo.domein.Vacation;

import com.solve.demo.dto.VacationReadDTO;

import com.solve.demo.repository.VacationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VacationService {
    @Autowired
    private VacationRepository vacationRepository;
    @Autowired
    private TranslationService translationService;


    public List<VacationReadDTO> getMasterVacations(UUID masterId){
        List<Vacation> vacations= vacationRepository.findByMasterId(masterId);
    return vacations.stream().map(translationService::toRead).collect(Collectors.toList());

    }

}
