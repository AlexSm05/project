package com.solve.demo.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CustomerReadDTO {
    private UUID id;
    private String name;
    private String phone;


}
