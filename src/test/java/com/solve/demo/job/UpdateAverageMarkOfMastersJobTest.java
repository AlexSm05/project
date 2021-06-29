package com.solve.demo.job;


import com.solve.demo.domein.Customer;
import com.solve.demo.domein.Master;
import com.solve.demo.domein.Visit;
import com.solve.demo.repository.CustomerRepository;
import com.solve.demo.repository.MasterRepository;
import com.solve.demo.repository.VisitRepository;
import com.solve.demo.service.MasterService;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(statements = {
        "delete from visit ",
        "delete from customer",
        "delete from master"},executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UpdateAverageMarkOfMastersJobTest {

    @Autowired
    private UpdateAverageMarkOfMastersJob updateAverageMarkOfMastersJob;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private MasterRepository masterRepository;
    @Autowired
    private VisitRepository visitRepository;

    @SpyBean
    private MasterService masterService;


    @Test
    public void testUpdateAverageMarkOfMasters(){
        Customer c1 = createCustomer();
        Master m1 = createMaster();

        createVisit(c1,m1,3);
        createVisit(c1,m1,5);
        updateAverageMarkOfMastersJob.updateAverageMarkOfMasters();

        m1 = masterRepository.findById(m1.getId()).get();
        Assert.assertEquals(4.0,m1.getAverageMark(),Double.MIN_NORMAL);
    }

    @Test
    public void testMasterUpdateIdependently(){
        Customer c1 = createCustomer();
        Master m1 = createMaster();
        Master m2 = createMaster();
        createVisit(c1,m1,4);
        createVisit(c1,m2,5);

        UUID[] failedId = new UUID[1];
        Mockito.doAnswer(invocationOnMock -> {
            if (failedId[0] == null){
                failedId[0] = invocationOnMock.getArgument(0);
                throw new RuntimeException();
            }
            return invocationOnMock.callRealMethod();
        }).when(masterService).updateAverageMarkOfMaster(Mockito.any());

        updateAverageMarkOfMastersJob.updateAverageMarkOfMasters();

        for (Master m : masterRepository.findAll()) {
            if (m.getId().equals(failedId[0])){
                Assert.assertNull(m.getAverageMark());
            }else{
                Assert.assertNotNull(m.getAverageMark());
            }
        }
    }

    private Customer createCustomer(){
        Customer customer=new Customer();
        customer.setName("qwea");
        customer.setPhone("1233");
        return customerRepository.save(customer);
    }

    private Master createMaster(){
        Master master=new Master();
        master.setName("qweqwe");
        master.setPhone("1234455");
        master.setAbout("wef23rfeee");
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
