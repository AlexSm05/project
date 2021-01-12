package com.solve.demo.repository;

import com.solve.demo.domein.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;
@Repository
@ActiveProfiles("test")
public interface CustomerRepository extends CrudRepository<Customer, UUID> {
}

