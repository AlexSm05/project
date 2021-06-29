package com.solve.demo.domein;

import lombok.Data;
import org.dom4j.tree.AbstractEntity;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;

import java.util.UUID;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Visit extends AbstractEntity {

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

    @CreatedDate
    private Instant createdAt;

    private Integer customerMark;

}
