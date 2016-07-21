package com.rit.exam.usecase;

import java.util.List;
import java.util.UUID;

import com.rit.exam.domain.Customer;

public interface CustomerService {
	Customer create(Customer customer);

	List<Customer> getAll();

	Customer getById(UUID id);

	Customer update(UUID id, Customer customer);

	void delete(UUID id);
}
