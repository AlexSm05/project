package com.solve.demo.repository;

import com.solve.demo.domein.Vacation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VacationRepository extends CrudRepository<Vacation, UUID>, VacationRepositoryCustom{
}
