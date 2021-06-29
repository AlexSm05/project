package com.solve.demo.service;

import com.solve.demo.domein.Customer;
import com.solve.demo.domein.Master;
import com.solve.demo.domein.Visit;
import com.solve.demo.repository.CustomerRepository;
import com.solve.demo.repository.MasterRepository;
import com.solve.demo.repository.VisitRepository;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Sql(statements = {
        "delete from visit",
        "delete from customer",
        "delete from master"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class MasterServiceTest {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private MasterRepository masterRepository;
    @Autowired
    private VisitRepository visitRepository;
    @Autowired
    private MasterService masterService;
    @Autowired
    private TransactionTemplate transactionTemplate;


    @Test
    public void testUpdateAverageMarkOfMaster(){
        Customer c1 = createCustomer();
        Master m1 = createMaster();

        createVisit(c1,m1,5);
        createVisit(c1,m1,3);

        masterService.updateAverageMarkOfMaster(m1.getId());
        m1 = masterRepository.findById(m1.getId()).get();
        Assert.assertEquals(4.0,m1.getAverageMark(),Double.MIN_NORMAL);

    }
    @Test
    public void testGetIdsOfMasters(){
        Set<UUID> expectedIdsOfMasters = new HashSet<>();
        expectedIdsOfMasters.add(createMaster().getId());
        expectedIdsOfMasters.add(createMaster().getId());

        transactionTemplate.executeWithoutResult(status->{
            Assert.assertEquals(expectedIdsOfMasters, masterRepository.getIdsOfMasters().collect(Collectors.toSet()));
        });
    }



    private Customer createCustomer(){
        Customer customer=new Customer();
        customer.setName("qwe");
        customer.setPhone("123");
        return customerRepository.save(customer);
    }

    private Master createMaster(){
        Master master=new Master();
        master.setName("qwe");
        master.setPhone("123");
        master.setAbout("wef23rf");
        return masterRepository.save(master);
    }
    private Visit createVisit(Customer customer, Master master, Integer customerMark)  {
        Visit visit= new Visit();
        visit.setCustomer(customer);
        visit.setMaster(master);
        visit.setCustomerMark(customerMark);
        return visitRepository.save(visit);
    }
}
