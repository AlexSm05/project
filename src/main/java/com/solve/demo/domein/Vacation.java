package com.solve.demo.domein;


import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity

public class Vacation {

    @Id
    @GeneratedValue
    private UUID id;

    private LocalDate startAt;
    private LocalDate endAt;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private Master master;
}
