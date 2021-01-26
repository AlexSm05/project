package com.solve.demo.dto;

import java.util.Objects;

public class CustomerCreateDTO {
    private String name;
    private String phone;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerCreateDTO createDTO = (CustomerCreateDTO) o;
        return Objects.equals(name, createDTO.name) &&
                Objects.equals(phone, createDTO.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phone);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
