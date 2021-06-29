package com.solve.demo.exeprions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundExeprion extends RuntimeException {
    public EntityNotFoundExeprion(Class entityClass, UUID id) {
        super(String.format("Entity %s with id=%s is not found!", entityClass.getSimpleName(), id));
    }
}
