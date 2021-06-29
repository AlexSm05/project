package com.solve.demo.dto;


import lombok.Data;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import java.time.Instant;
import java.util.UUID;

@Data
@EntityListeners(AuditingEntityListener.class)
public class VisitCreateDTO {


    private UUID customerId;
    private UUID masterId;

    private Instant startAt;
    private Instant endAt;


    private Instant createdAt;

    private Integer customerMark;




}
