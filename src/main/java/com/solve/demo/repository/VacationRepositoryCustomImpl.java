package com.solve.demo.repository;

import com.solve.demo.domein.Vacation;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.UUID;

@Repository
public class VacationRepositoryCustomImpl implements VacationRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Vacation> findByMasterId(UUID masterId) {
        StringBuilder sb=new StringBuilder();
        sb.append("select v from Vacation v where 1=1");
        if (masterId!=null){
            sb.append(" and v.master.id = :masterId");
        }
        TypedQuery<Vacation> query=entityManager.createQuery(sb.toString(),Vacation.class);

        if (masterId!=null){
            query.setParameter("masterId",masterId);
        }
        return query.getResultList();
    }
}
