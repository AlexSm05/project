package com.solve.demo.service;

import com.solve.demo.domein.Customer;
import com.solve.demo.domein.Master;
import com.solve.demo.domein.Visit;
import com.solve.demo.domein.VisitStatus;
import com.solve.demo.dto.*;
import com.solve.demo.exeprions.EntityNotFoundExeprion;
import com.solve.demo.repository.CustomerRepository;
import com.solve.demo.repository.MasterRepository;
import com.solve.demo.repository.VisitRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import static  org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.UUID;

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

    @Ignore
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
    //test 1
    @Ignore
    @Test
    public void testGetVisitWithEmptyFilter(){
        Customer c1=createCustomer();
        Master m1=createMaster();
        Customer c2=createCustomer();
        Master m2=createMaster();

        Visit v1=createVisit(c1,m1);
        Visit v2=createVisit(c1,m2);
        Visit v3=createVisit(c2,m2);

        VisitFilter visitFilter=new VisitFilter();
        Assertions.assertThat(visitService.getVisits(visitFilter)).extracting("id")
                .containsExactlyInAnyOrder(v1.getId(),v2.getId(),v3.getId());
    }
    //test2
    @Ignore
    @Test
    public void testGetVisitByCustomer(){
        Customer c1=createCustomer();
        Master m1=createMaster();
        Customer c2=createCustomer();
        Master m2=createMaster();

        Visit v1=createVisit(c1,m1);
        Visit v2=createVisit(c1,m2);
        createVisit(c2,m2);

        VisitFilter visitFilter=new VisitFilter();
        visitFilter.setCustomerId(c1.getId());
        Assertions.assertThat(visitService.getVisits(visitFilter)).extracting("id")
                .containsExactlyInAnyOrder(v1.getId(),v2.getId());

    }
    //test3
    @Ignore
    @Test
    public void testGetVisitByMaster(){
        Customer c1=createCustomer();
        Master m1=createMaster();
        Customer c2=createCustomer();
        Master m2=createMaster();

        createVisit(c1,m1);
        Visit v2=createVisit(c1,m2);
        Visit v3=createVisit(c2,m2);

        VisitFilter visitFilter=new VisitFilter();
        visitFilter.setMasterId(m2.getId());
        Assertions.assertThat(visitService.getVisits(visitFilter)).extracting("id")
                .containsExactlyInAnyOrder(v2.getId(),v3.getId());

    }
    //test4
    @Ignore
    @Test
    public void testGetVisitByStatuses(){
        Customer c1=createCustomer();
        Master m1=createMaster();
        Customer c2=createCustomer();
        Master m2=createMaster();

        createVisit(c1,m1, createInstant(12), VisitStatus.SCHEDULED);
        Visit v2=createVisit(c1,m2,createInstant(13),VisitStatus.CANCELLED);
        Visit v3=createVisit(c2,m2,createInstant(9),VisitStatus.FINISHED);

        VisitFilter visitFilter=new VisitFilter();
        visitFilter.setStatuses(Set.of(VisitStatus.CANCELLED,VisitStatus.FINISHED));
        Assertions.assertThat(visitService.getVisits(visitFilter)).extracting("id")
                .containsExactlyInAnyOrder(v2.getId(),v3.getId());

    }
    //test5
    @Ignore
    @Test
    public void testGetVisitByStartAtInterval(){
        Customer c1=createCustomer();
        Master m1=createMaster();
        Customer c2=createCustomer();
        Master m2=createMaster();

        Visit v1=createVisit(c1,m1, createInstant(12), VisitStatus.SCHEDULED);
        createVisit(c1,m2,createInstant(13),VisitStatus.CANCELLED);
        Visit v3=createVisit(c2,m2,createInstant(9),VisitStatus.FINISHED);

        VisitFilter visitFilter=new VisitFilter();
        visitFilter.setStartAtFrom(createInstant(9));
        visitFilter.setStartAtTo(createInstant(13));
        Assertions.assertThat(visitService.getVisits(visitFilter)).extracting("id")
                .containsExactlyInAnyOrder(v1.getId(),v3.getId());
    }

    //test6
    @Ignore
    @Test
    public void testGetVisitByAllFilters(){
        Customer c1=createCustomer();
        Master m1=createMaster();
        Customer c2=createCustomer();
        Master m2=createMaster();

        Visit v1=createVisit(c1,m1, createInstant(12), VisitStatus.SCHEDULED);
        createVisit(c1,m2,createInstant(13),VisitStatus.CANCELLED);
        createVisit(c2,m2,createInstant(9),VisitStatus.FINISHED);

        VisitFilter visitFilter=new VisitFilter();
        visitFilter.setCustomerId(c1.getId());
        visitFilter.setMasterId(m1.getId());
        visitFilter.setStartAtFrom(createInstant(9));
        visitFilter.setStartAtTo(createInstant(13));
        visitFilter.setStatuses(Set.of(VisitStatus.SCHEDULED));
        Assertions.assertThat(visitService.getVisits(visitFilter)).extracting("id")
                .containsExactlyInAnyOrder(v1.getId());
    }

    //test7
    @Test
    public void testCreateVisit(){
        Customer c=createCustomer();
        Master m =createMaster();
        VisitCreateDTO create=new VisitCreateDTO();
        create.setStartAt(createInstant(4));
        create.setEndAt(createInstant(5));
        create.setCustomerId(c.getId());
        create.setMasterId(m.getId());
        create.setCreatedAt(Instant.now().truncatedTo(ChronoUnit.SECONDS));


        VisitReadDTO read=visitService.createVisit(create);
        Assertions.assertThat(create).isEqualToComparingFieldByField(read);
        Assert.assertNotNull(read.getId());
        Assert.assertEquals(VisitStatus.SCHEDULED,read.getStatus());

        Visit savedVisit=visitRepository.findById(read.getId()).get();
        Assertions.assertThat(savedVisit).isEqualToIgnoringGivenFields(read,
                "customer", "master");
    }

    @Test(expected = EntityNotFoundExeprion.class)
    public void testCreateVisitWhithWrongCustomer(){
        Master m=createMaster();
        VisitCreateDTO create=new VisitCreateDTO();
        create.setStartAt(createInstant(3));
        create.setEndAt(createInstant(4));
        create.setMasterId(m.getId());
        create.setCustomerId(UUID.randomUUID());

        visitService.createVisit(create);

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
    private Visit createVisit(Customer customer,Master master,Instant instant,VisitStatus visitStatus)  {
        Visit visit= new Visit();
        visit.setMaster(master);
        visit.setCustomer(customer);

        visit.setStartAt(instant);
        visit.setEndAt(instant.plusSeconds(300));
        visit.setStatus(visitStatus);
        return visitRepository.save(visit);
    }
    private Instant createInstant(int hour){
        return LocalDateTime.of(2019,12,23,hour,0).toInstant(ZoneOffset.UTC);
    }
}
