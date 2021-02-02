package com.solve.demo.service;

import com.solve.demo.domein.Customer;
import com.solve.demo.domein.Master;
import com.solve.demo.domein.Visit;
import com.solve.demo.domein.VisitStatus;
import com.solve.demo.dto.CustomerPatchDTO;
import com.solve.demo.dto.CustomerReadDTO;
import com.solve.demo.dto.VisitExtendedReadDTO;
import com.solve.demo.repository.CustomerRepository;
import com.solve.demo.repository.MasterRepository;
import com.solve.demo.repository.VisitRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Sql(statements = {
        "delete from visit",
        "delete from customer",
        "delete from master"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class VisitServiceTest {

    @Autowired
    private VisitService visitService;
    @Autowired
    private VisitRepository visitRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private MasterRepository masterRepository;

    @Test
    public void testGetVisitExtended()  {
        Customer customer=createCustomer();
        Master master=createMaster();
        Visit visit=createVisit(customer,master);

        VisitExtendedReadDTO read =visitService.getVisit(visit.getId());
        Assertions.assertThat(read).isEqualToIgnoringGivenFields(visit,"customer","master");
        Assertions.assertThat(read.getCustomer()).isEqualToIgnoringGivenFields(customer);
        Assertions.assertThat(read.getMaster()).isEqualToIgnoringGivenFields(master);

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
    private Visit createVisit(Customer customer,Master master)  {
        Visit visit= new Visit();
        visit.setMaster(master);
        visit.setCustomer(customer);

        visit.setStartAt(Instant.parse("2021-02-02T11:00:00Z"));
        visit.setEndAt(Instant.parse("2021-02-02T12:00:00Z"));
        visit.setStatus(VisitStatus.SCHEDULED);
        return visitRepository.save(visit);
    }
}
