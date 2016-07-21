package com.rit.exam.jpa;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rit.exam.domain.Customer;

// it's an interface but contains the CRUD feature that we need for this exam, thanks to Spring Data JPA
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
	Customer findByName(String name);
}
