package com.nailagent.backend.domain.customer.repository;

import com.nailagent.backend.domain.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByNameAndPhoneNum(String name, String phoneNum);
}
