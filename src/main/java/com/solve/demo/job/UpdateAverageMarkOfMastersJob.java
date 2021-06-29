package com.solve.demo.job;

import com.solve.demo.repository.MasterRepository;
import com.solve.demo.service.MasterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Component
public class UpdateAverageMarkOfMastersJob {

    @Autowired
    private MasterService masterService;
    @Autowired
    private MasterRepository masterRepository;

    @Transactional(readOnly = true)
    @Scheduled(cron = "${update.average.mark.of.masters.job.cron}")
    public void updateAverageMarkOfMasters(){
        log.info("Job started");
        masterRepository.getIdsOfMasters().forEach(masterId->{
            try {
                masterService.updateAverageMarkOfMaster(masterId);
            }catch (Exception e){
                log.error("Failed to update average mark of master: {}",masterId,e);
            }
        });
        log.info("Job finished");

    }
}
