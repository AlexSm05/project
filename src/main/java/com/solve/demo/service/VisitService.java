package com.solve.demo.service;



import com.solve.demo.domein.Visit;
import com.solve.demo.dto.VisitFilter;
import com.solve.demo.dto.VisitReadDTO;
import com.solve.demo.service.TranslationService;

import com.solve.demo.dto.VisitExtendedReadDTO;
import com.solve.demo.exeprions.EntityNotFoundExeprion;
import com.solve.demo.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VisitService {

    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private TranslationService translationService;

    public VisitExtendedReadDTO getVisit(UUID id){
        Visit visit=getVisitRequider(id);
        return translationService.toReadExtended(visit);
    }
    public List<VisitReadDTO> getVisits(VisitFilter filter){
        List<Visit> visits=visitRepository.findByFilter(filter);
        return visits.stream().map(translationService::toRead).collect(Collectors.toList());

    }
    private Visit getVisitRequider(UUID id){
        return visitRepository.findById(id).orElseThrow(()->{
            throw new EntityNotFoundExeprion(Visit.class,id);
        });
    }


}
