package com.rit.exam.usecase.impl;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.rit.exam.domain.Customer;
import com.rit.exam.jpa.CustomerRepository;
import com.rit.exam.usecase.CustomerService;
import com.rit.exam.usecase.exception.CustomerAlreadyExistsException;

@Service
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepository;

	@Inject
	public CustomerServiceImpl(final CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Override
	@Transactional
	public Customer create(final Customer customer) {
		Customer existing = customerRepository.findByName(customer.getName());
		if (existing != null) {
			throw new CustomerAlreadyExistsException(existing.getId());
		}
		return customerRepository.save(customer);
	}

	@Override
	public List<Customer> getAll() {
		return customerRepository.findAll();
	}

	@Override
	public Customer getById(UUID id) {
		return customerRepository.findOne(id);
	}

	@Override
	@Transactional
	public void delete(UUID id) {
		customerRepository.delete(id);
	}

	@Override
	@Transactional
	public Customer update(UUID id, Customer customer) {
		Customer updateCustomer = customerRepository.findOne(id);
		updateCustomer.setAddress(customer.getAddress());
		updateCustomer.setName(customer.getName());
		updateCustomer.setTelephoneNumber(customer.getTelephoneNumber());
		return customerRepository.save(updateCustomer);
	}

}