package com.solve.demo.service;

import com.solve.demo.domein.*;
import com.solve.demo.dto.*;



import com.solve.demo.repository.RepositoryHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.time.temporal.ChronoUnit;



@Service
public class TranslationService {


    @Autowired
    private RepositoryHelper repositoryHelper;


    public VisitExtendedReadDTO toReadExtended(Visit visit) {
        VisitExtendedReadDTO dto = new VisitExtendedReadDTO();
        dto.setId(visit.getId());
        dto.setStartAt(visit.getStartAt());
        dto.setEndAt(visit.getEndAt());
        dto.setStatus(visit.getStatus());
        dto.setCustomer(toRead(visit.getCustomer()));
        dto.setMaster(toRead(visit.getMaster()));
        return dto;
    }

    public VisitReadDTO toRead(Visit visit) {
        VisitReadDTO dto = new VisitReadDTO();
        dto.setId(visit.getId());
        dto.setStartAt(visit.getStartAt());
        dto.setEndAt(visit.getEndAt());
        dto.setStatus(visit.getStatus());
        dto.setCustomerId(visit.getCustomer().getId());
        dto.setMasterId(visit.getMaster().getId());
        dto.setCreatedAt(visit.getCreatedAt().truncatedTo(ChronoUnit.SECONDS));
        return dto;
    }

    public CustomerReadDTO toRead(Customer customer) {
        CustomerReadDTO dto = new CustomerReadDTO();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setPhone(customer.getPhone());
        return dto;
    }

    public MasterReadDTO toRead(Master master) {
        MasterReadDTO dto = new MasterReadDTO();
        dto.setId(master.getId());
        dto.setName(master.getName());
        dto.setPhone(master.getPhone());
        dto.setAbout(master.getAbout());
        return dto;
    }

    public VacationReadDTO toRead(Vacation vacation) {
        VacationReadDTO dto = new VacationReadDTO();
        dto.setId(vacation.getId());
        dto.setStartAt(vacation.getStartAt());
        dto.setEndAt(vacation.getEndAt());
        dto.setMasterId(vacation.getMaster().getId());
        return dto;
    }

    public  Customer toEntity(CustomerCreateDTO create) {
        Customer customer = new Customer();
        customer.setPhone(create.getPhone());
        customer.setName(create.getName());
        return customer;
    }

    public Visit toEntity(VisitCreateDTO create) {
        Visit visit = new Visit();
        visit.setStartAt(create.getStartAt());
        visit.setEndAt(create.getEndAt());
        visit.setStatus(VisitStatus.SCHEDULED);
        visit.setMaster(repositoryHelper.getReferenceIfExists(Master.class, create.getMasterId()));
        visit.setCustomer(repositoryHelper.getReferenceIfExists(Customer.class, create.getCustomerId()));
        visit.setCreatedAt(create.getCreatedAt().truncatedTo(ChronoUnit.SECONDS));
        visit.setCustomerMark(create.getCustomerMark());
        return visit;
    }



    public Vacation toEntity(VacationCreateDTO create) {
        Vacation vacation = new Vacation();
        vacation.setId(create.getId());
        vacation.setStartAt(create.getStartAt());
        vacation.setEndAt(create.getEndAt());
        return vacation;
    }

    public void patchEntity(CustomerPatchDTO patch, Customer customer) {
        if (patch.getPhone() != null) {
            customer.setPhone(patch.getPhone());
        }
        if (patch.getName() != null) {
            customer.setName(patch.getName());
        }
    }




}
