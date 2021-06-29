package com.solve.demo.job;


import com.solve.demo.domein.Customer;
import com.solve.demo.domein.Master;
import com.solve.demo.domein.Visit;
import com.solve.demo.repository.CustomerRepository;
import com.solve.demo.repository.MasterRepository;
import com.solve.demo.repository.VisitRepository;
import com.solve.demo.service.MasterService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@Import(ScheduledConfiguretionsTest.ScheduledTestConfig.class)
@ActiveProfiles("test")
public class ScheduledConfiguretionsTest {
    @Test
    public void testSpringContextUpAndRunning(){
        log.info("@Scheduled configurations are OK");

    }

    @EnableScheduling
    static class ScheduledTestConfig {

    }

}
