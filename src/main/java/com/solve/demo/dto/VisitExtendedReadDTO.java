package com.solve.demo.dto;

import com.solve.demo.domein.VisitStatus;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class VisitExtendedReadDTO {
    private UUID id;

    private CustomerReadDTO customer;
    private MasterReadDTO master;
    private Instant startAt;
    private Instant endAt;
    private VisitStatus status;
}
