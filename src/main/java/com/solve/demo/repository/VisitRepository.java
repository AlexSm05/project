package com.solve.demo.repository;

import com.solve.demo.domein.Visit;
import com.solve.demo.domein.VisitStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface VisitRepository extends CrudRepository<Visit, UUID>,VisitRepositoryCustom {

    List<Visit> findByMasterIdAndStatusOrderByStartAtAsc(UUID id,VisitStatus visitStatus);

    @Query("select v from Visit v where v.master.id = :masterId and v.status = :visitStatus"
            +" and v.startAt >= :startFrom and v.startAt < :startTo order by v.startAt asc")
    List<Visit> findVisitsForMasterInGivenInterval(
            UUID masterId, VisitStatus visitStatus, Instant startFrom, Instant startTo);
}
