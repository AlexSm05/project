package com.solve.demo.domein;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Data
@Entity
public class Visit {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Master master;

    private Instant startAt;
    private Instant endAt;

    @Enumerated(EnumType.STRING)
    private VisitStatus status;

}
