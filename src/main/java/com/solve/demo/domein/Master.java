package com.solve.demo.domein;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
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

    //private Double avarageMark;

    @OneToMany(mappedBy = "master")
    private List<Vacation> vacations;
}
