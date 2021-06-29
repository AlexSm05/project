package com.solve.demo.domein;

import lombok.Data;



import javax.persistence.*;
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

    private Double averageMark;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "master", cascade = CascadeType.ALL)
    private List<Vacation> vacations;
}
