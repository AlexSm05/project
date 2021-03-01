package com.solve.demo.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class VacationReadDTO {
    private UUID id;

    private LocalDate startAt;
    private LocalDate endAt;

    private UUID masterId;
}
