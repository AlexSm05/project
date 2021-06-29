package com.solve.demo.repository;

import com.solve.demo.domein.Master;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Repository
public interface MasterRepository extends CrudRepository<Master, UUID> {

    @Query("select m.id from Master m")
    Stream<UUID> getIdsOfMasters();
}
