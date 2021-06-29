package com.solve.demo.repository;


import com.solve.demo.domein.Master;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(statements = "delete from master",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class MasterRepositoryTest {

    @Autowired
    private MasterRepository masterRepository;
    @Autowired
    private TransactionTemplate transactionTemplate;

    @Test
    public void testGetIdsOfMaster(){
        Set<UUID> expectedIdsOfMaster = new HashSet<>();
        expectedIdsOfMaster.add(createMaster().getId());
        expectedIdsOfMaster.add(createMaster().getId());
        transactionTemplate.executeWithoutResult(status->{
            Assert.assertEquals(expectedIdsOfMaster, masterRepository.getIdsOfMasters().collect(Collectors.toSet()));
        });
    }
    private Master createMaster(){
        Master master=new Master();
        master.setName("qwe");
        master.setPhone("123");
        master.setAbout("wef23rf");
        master.setAverageMark(5.0);
        return masterRepository.save(master);
    }
}
