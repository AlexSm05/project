package com.solve.demo.service;

import com.solve.demo.domein.Customer;
import com.solve.demo.domein.Master;
import com.solve.demo.domein.Vacation;
import com.solve.demo.domein.Visit;
import com.solve.demo.dto.*;

import org.springframework.stereotype.Service;



@Service
public class TranslationService {


    public VisitExtendedReadDTO toReadExtended(Visit visit){
        VisitExtendedReadDTO dto =new VisitExtendedReadDTO();
        dto.setId(visit.getId());
        dto.setStartAt(visit.getStartAt());
        dto.setEndAt(visit.getEndAt());
        dto.setStatus(visit.getStatus());
        dto.setCustomer(toRead(visit.getCustomer()));
        dto.setMaster(toRead(visit.getMaster()));
        return dto;
    }
    public VisitReadDTO toRead(Visit visit){
        VisitReadDTO dto =new VisitReadDTO();
        dto.setId(visit.getId());
        dto.setStartAt(visit.getStartAt());
        dto.setEndAt(visit.getEndAt());
        dto.setStatus(visit.getStatus());
        dto.setCustomerId(visit.getCustomer().getId());
        dto.setMasterId(visit.getMaster().getId());
        return dto;
    }


    public CustomerReadDTO toRead(Customer customer) {
        CustomerReadDTO dto= new CustomerReadDTO();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setPhone(customer.getPhone());
        return dto;
    }
    public MasterReadDTO toRead(Master master) {
        MasterReadDTO dto= new MasterReadDTO();
        dto.setId(master.getId());
        dto.setName(master.getName());
        dto.setPhone(master.getPhone());
        dto.setAbout(master.getAbout());
        return dto;
    }

    public  Customer toEntity(CustomerCreateDTO create){
        Customer customer=new Customer();
        customer.setPhone(create.getPhone());
        customer.setName(create.getName());
        return customer;
    }
    public void patchEntity(CustomerPatchDTO patch,Customer customer){
        if (patch.getPhone()!=null){
            customer.setPhone(patch.getPhone());
        }
        if (patch.getName()!=null){
            customer.setName(patch.getName());
        }
    }
    public VacationReadDTO toRead(Vacation vacation){
        VacationReadDTO dto =new VacationReadDTO();
        dto.setId(vacation.getId());
        dto.setStartAt(vacation.getStartAt());
        dto.setEndAt(vacation.getEndAt());
        dto.setMasterId(vacation.getMaster().getId());
        return dto;
    }

}
