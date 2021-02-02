package com.solve.demo.dto;

import com.solve.demo.domein.VisitStatus;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class VisitReadDTO {
    private UUID id;

    private UUID customerId;
    private UUID masterId;

    private Instant startAt;
    private Instant endAt;

    private VisitStatus status;

}
