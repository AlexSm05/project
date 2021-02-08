package com.solve.demo.repository;

import com.solve.demo.domein.Visit;
import com.solve.demo.dto.VisitFilter;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitRepositoryCustom {
    List<Visit> findByFilter(VisitFilter filter);
}
