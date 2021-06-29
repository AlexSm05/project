package com.solve.demo.domein;

import lombok.Data;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.util.UUID;

@Entity
@Data
public class Customer {

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String phone;


}


