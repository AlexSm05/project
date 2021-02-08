package com.solve.demo.dto;

import com.solve.demo.domein.VisitStatus;
import lombok.Data;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Data
public class VisitFilter {
    private UUID customerId;
    private UUID masterId;
    private Set<VisitStatus> statuses;
    private Instant startAtFrom;
    private Instant startAtTo;

}
