package com.solve.demo.repository;

import com.solve.demo.exeprions.EntityNotFoundExeprion;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.UUID;

@Component
public class RepositoryHelper {

    @PersistenceContext
    private EntityManager entityManager;

    public <E> E getReferenceIfExists(Class<E> entityClass, UUID id) {
        validateExists(entityClass, id);
        return  entityManager.getReference(entityClass, id);
    }

    public <E> void validateExists(Class<E> entityClass, UUID id) {
        Query query = entityManager.createQuery(
                "select count(e) from " + entityClass.getSimpleName() + " e where e.id = :id");
        query.setParameter("id", id);
        boolean exists = ((Number) query.getSingleResult()).intValue() > 0;
        if (!exists) {
            throw new EntityNotFoundExeprion(entityClass, id);
        }
    }
}
