package com.solve.demo.repository;

import com.solve.demo.domein.Master;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MasterRepository extends CrudRepository<Master, UUID> {
}
