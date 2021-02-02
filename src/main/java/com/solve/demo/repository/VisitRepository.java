package com.solve.demo.repository;

import com.solve.demo.domein.Visit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VisitRepository extends CrudRepository<Visit, UUID> {
}
