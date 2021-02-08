package com.solve.demo.repository;

import com.solve.demo.domein.Visit;
import com.solve.demo.dto.VisitFilter;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class VisitRepositoryCustomImpl implements VisitRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Visit> findByFilter(VisitFilter filter) {
        StringBuilder sb=new StringBuilder();
        sb.append("select v from Visit v where 1=1");
        if (filter.getCustomerId()!=null){
            sb.append(" and v.customer.id = :customerId");
        }
        if (filter.getMasterId()!=null){
            sb.append(" and v.master.id = :masterId");
        }
        if (filter.getStatuses()!=null&& !filter.getStatuses().isEmpty()){
            sb.append(" and v.status in (:statuses)");
        }
        if (filter.getStartAtFrom()!=null){
            sb.append(" and v.startAt >= (:startAtFrom)");
        }
        if (filter.getStartAtTo()!=null){
            sb.append(" and v.startAt < (:startAtTo)");
        }
        TypedQuery<Visit> query=entityManager.createQuery(sb.toString(),Visit.class);

        if (filter.getCustomerId()!=null){
            query.setParameter("customerId",filter.getCustomerId());
        }
        if (filter.getMasterId()!=null){
            query.setParameter("masterId",filter.getMasterId());
        }
        if (filter.getStatuses()!=null&& !filter.getStatuses().isEmpty()){
            query.setParameter("statuses",filter.getStatuses());
        }
        if (filter.getStartAtFrom()!=null){
            query.setParameter("startAtFrom",filter.getStartAtFrom());
        }
        if (filter.getStartAtTo()!=null){
            query.setParameter("startAtTo",filter.getStartAtTo());
        }
        return query.getResultList();
    }
}
