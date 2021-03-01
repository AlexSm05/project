package com.solve.demo.repository;


import com.solve.demo.domein.Vacation;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VacationRepositoryCustom {
    List<Vacation> findByMasterId(UUID masterId);
}
