package com.rit.exam.usecase;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.rit.exam.domain.Customer;
import com.rit.exam.jpa.CustomerRepository;
import com.rit.exam.usecase.impl.CustomerServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceImplTest {

	@Mock
	private CustomerRepository customerRepository;

	private CustomerService customerService;

	@Before
	public void setUp() {
		customerService = new CustomerServiceImpl(customerRepository);
	}

	@Test
	public void shouldCreateNewCustomer() {
		final Customer createdCustomer = stubRepositoryToReturnCustomerOnCreate();
		final Customer customer = new Customer();
		final Customer returnedCustomer = customerService.create(customer);
		// verify save was called in customerRepostory with customer
		verify(customerRepository, times(1)).save(customer);
		assertEquals("customer should come from the repository", createdCustomer, returnedCustomer);
	}

	@Test
	public void shouldGetAllCustomers() {
		final List<Customer> customers = stubRepositoryToReturnCustomerOnGetAll();
		final List<Customer> returnedCustomer = customerService.getAll();
		// verify findAll was called in customerRepository
		verify(customerRepository, times(1)).findAll();
		assertEquals("customers should come from the repository", customers, returnedCustomer);
	}

	@Test
	public void shouldGetCustomerById() {
		final Customer foundCustomer = stubRepositoryToReturnCustomerOnGetById();
		UUID id = UUID.randomUUID();
		final Customer returnedCustomer = customerService.getById(id);
		// verify findOne was called in customerRepository with id
		verify(customerRepository, times(1)).findOne(id);
		assertEquals("customer should come from the repository", foundCustomer, returnedCustomer);
	}

	@Test
	public void shouldDeleteCustomerById() {
		UUID id = UUID.randomUUID();
		customerService.delete(id);
		// verify delete was called in customerRepository with id
		verify(customerRepository, times(1)).delete(id);
	}

	private Customer stubRepositoryToReturnCustomerOnCreate() {
		Customer customer = new Customer();
		when(customerRepository.save(any(Customer.class))).thenReturn(customer);
		return customer;
	}

	private List<Customer> stubRepositoryToReturnCustomerOnGetAll() {
		Customer customer = new Customer();
		List<Customer> customers = new ArrayList<Customer>();
		customers.add(customer);
		when(customerRepository.findAll()).thenReturn(customers);
		return customers;
	}

	private Customer stubRepositoryToReturnCustomerOnGetById() {
		Customer customer = new Customer();
		when(customerRepository.findOne(any(UUID.class))).thenReturn(customer);
		return customer;
	}

}