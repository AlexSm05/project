package com.solve.demo.service;


import com.solve.demo.domein.Master;
import com.solve.demo.exeprions.EntityNotFoundExeprion;
import com.solve.demo.repository.MasterRepository;
import com.solve.demo.repository.VisitRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class MasterService {
    @Autowired
    public VisitRepository visitRepository;
    @Autowired
    public MasterRepository masterRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateAverageMarkOfMaster(UUID masterId){
        Double averageMark = visitRepository.calcAverageMarkOfMaster(masterId);
        Master master = masterRepository.findById(masterId).orElseThrow(
                ()-> new EntityNotFoundExeprion(Master.class,masterId));

        log.info("Setting new average_mark of master: {}, Old value: {}, new value: {}",masterId,
                master.getAverageMark(),averageMark);
        master.setAverageMark(averageMark);
        masterRepository.save(master);

    }
}
