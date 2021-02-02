package com.solve.demo.domein;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;


@Data
@Entity
public class Master {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String phone;
    private String about;
}
