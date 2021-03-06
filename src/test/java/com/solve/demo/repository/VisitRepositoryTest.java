package com.solve.demo.repository;


import com.solve.demo.domein.Customer;
import com.solve.demo.domein.Master;
import com.solve.demo.domein.Visit;
import com.solve.demo.domein.VisitStatus;
import org.junit.Assert;
import org.junit.Test;
import org.assertj.core.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Sql(statements = {
        "delete from visit ",
        "delete from customer",
        "delete from master"},
        executionPhase =Sql.ExecutionPhase.AFTER_TEST_METHOD )
public class VisitRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private MasterRepository masterRepository;
    @Autowired
    private VisitRepository visitRepository;

    @Test
    public void testCreatedAtIsSet(){
        Customer c=createCustomer();
        Master m=createMaster();

        Visit v =new Visit();
        v.setCustomer(c);
        v.setMaster(m);
        v.setStartAt(LocalDateTime.of(2021,12,4,15,0,0)
                .toInstant(ZoneOffset.UTC));
        v.setEndAt(v.getStartAt().plus(1, ChronoUnit.HOURS));
        v.setStatus(VisitStatus.SCHEDULED);
        v = visitRepository.save(v);

        Instant createdBeforeReload=v.getCreatedAt();
        Assert.assertNotNull(createdBeforeReload);
        v=visitRepository.findById(v.getId()).get();

        Instant createdAfterReload=v.getCreatedAt();
        Assert.assertNotNull(createdAfterReload);
        Assert.assertEquals(createdBeforeReload,createdAfterReload);

        
    }

    @Test
    public void testGetMasterVisit(){
        Customer c =createCustomer();
        Master m1=createMaster();
        Master m2= createMaster();
        Visit v1=createVisit(c,m1,VisitStatus.SCHEDULED);
        createVisit(c,m1,VisitStatus.CANCELLED);
        Visit v2=createVisit(c,m1,VisitStatus.SCHEDULED);
        createVisit(c,m2,VisitStatus.CANCELLED);

        List<Visit> res=visitRepository.findByMasterIdAndStatusOrderByStartAtAsc(m1.getId(),VisitStatus.SCHEDULED);
        Assertions.assertThat(res).extracting(Visit::getId).isEqualTo(Arrays.asList(v1.getId(),v2.getId()));
    }

    @Test
    public void testGetMasterVisitsInInterval(){
        Customer c =createCustomer();
        Master m1=createMaster();
        Master m2= createMaster();
        ZoneOffset utc=ZoneOffset.UTC;
        Visit v1=createVisit(c,m1,VisitStatus.SCHEDULED,
                LocalDateTime.of(2019,12,4,15,0,0).toInstant(utc));
        createVisit(c,m1,VisitStatus.CANCELLED,
                LocalDateTime.of(2019,12,4,15,0,0).toInstant(utc));
        Visit v2=createVisit(c,m1,VisitStatus.SCHEDULED,
                LocalDateTime.of(2019,12,4,15,30,0).toInstant(utc));
        createVisit(c,m1,VisitStatus.CANCELLED,
                LocalDateTime.of(2019,12,4,17,30,0).toInstant(utc));
        createVisit(c,m2,VisitStatus.CANCELLED,
                LocalDateTime.of(2019,12,4,15,0,0).toInstant(utc));

        List<Visit> res=visitRepository.findVisitsForMasterInGivenInterval(m1.getId(),VisitStatus.SCHEDULED,
                LocalDateTime.of(2019,12,4,15,0,0).toInstant(utc),
                LocalDateTime.of(2019,12,4,17,30,0).toInstant(utc));
        Assertions.assertThat(res).extracting(Visit::getId).isEqualTo(Arrays.asList(v1.getId(),v2.getId()));
    }

    @Test
    public void testCalcAverageMark() {
        Customer c1=createCustomer();
        Master m1=createMaster();
        Master m2=createMaster();


        createVisit(c1,m1,4);
        createVisit(c1,m1,5);
        createVisit(c1,m1,(Integer) null);
        createVisit(c1,m2,2);

        Assert.assertEquals(4.5,visitRepository.calcAverageMarkOfMaster(m1.getId()),Double.MIN_NORMAL);
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
    private Visit createVisit(Customer customer, Master master,VisitStatus visitStatus)  {
        Visit visit= new Visit();
        visit.setMaster(master);
        visit.setCustomer(customer);

        visit.setStartAt(Instant.parse("2021-02-02T11:00:00Z"));
        visit.setEndAt(Instant.parse("2021-02-02T12:00:00Z"));
        visit.setStatus(visitStatus);
        return visitRepository.save(visit);
    }
    private Visit createVisit(Customer customer, Master master, VisitStatus visitStatus, Instant instant)  {
        Visit visit= new Visit();
        visit.setMaster(master);
        visit.setCustomer(customer);

        visit.setStartAt(instant);
        visit.setEndAt(instant);
        visit.setStatus(visitStatus);
        return visitRepository.save(visit);
    }
    private Visit createVisit(Customer customer, Master master, Integer customerMark)  {
        Visit visit= new Visit();
        visit.setCustomer(customer);
        visit.setMaster(master);
        visit.setCustomerMark(customerMark);
        return visitRepository.save(visit);
    }
}
