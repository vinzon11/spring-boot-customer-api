package com.rit.exam.api.rest;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.rit.exam.api.rest.CustomerRestController;
import com.rit.exam.domain.Customer;
import com.rit.exam.jpa.CustomerRepository;
import com.rit.exam.usecase.CustomerService;

@RunWith(MockitoJUnitRunner.class)
public class CustomerRestControllerTest {

	@Mock
	private CustomerService customerService;

	@Mock
	CustomerRepository customerRepository;

	private CustomerRestController customerController;

	@Before
	public void setUp() {
		customerController = new CustomerRestController(customerService);
	}

	@Test
	public void shouldCreateCustomer() throws Exception {
		final ResponseEntity<?> createdCustomer = mockServiceToReturnCreatedCustomer();
		final Customer customer = new Customer();
		ResponseEntity<?> returnedCustomer = customerController.createCustomer(customer);
		// verify create was invoked in customerService
		verify(customerService, times(1)).create(customer);
		assertEquals("customer should be created by the service", createdCustomer, returnedCustomer);
	}

	@Test
	public void shouldListAllCustomers() throws Exception {
		final ResponseEntity<?> customers = mockServiceToReturnAllCustomers();
		ResponseEntity<?> returnedCustomers = customerController.listAllCustomers();
		// verify getAll was invoked in customerService
		verify(customerService, times(1)).getAll();
		assertEquals("customer list should be get by the service", customers, returnedCustomers);
	}

	@Test
	public void shouldGetCustomerById() throws Exception {
		final ResponseEntity<?> customer = mockServiceToReturnCustomerFoundById();
		UUID id = UUID.randomUUID();
		ResponseEntity<?> returnedCustomer = customerController.getCustomerById(id);
		// verify getById was invoked in customerService
		verify(customerService, times(1)).getById(id);
		assertEquals("customers should be get by the service by Id", customer, returnedCustomer);
	}

	@Test
	public void shouldUpdateCustomer() throws Exception {
		final ResponseEntity<?> updatedCustomer = mockServiceToReturnUpdatedCustomer();
		final UUID id = ((Customer) updatedCustomer.getBody()).getId();
		final Customer customer = new Customer();
		ResponseEntity<?> returnedCustomer = customerController.updateCustomerById(id, customer);
		// verify create was invoked in customerService
		verify(customerService, times(1)).update(id, customer);
		assertEquals("customer should be updated by the service", updatedCustomer, returnedCustomer);
	}

	@Test
	public void shouldDeleteCustomer() throws Exception {
		final ResponseEntity<?> response = mockServiceToReturnCustomerDeletedById();
		UUID id = UUID.randomUUID();
		ResponseEntity<?> returnedResponse = customerController.deleteCustomerById(id);
		// verify delete was invoked in customerService
		verify(customerService, times(1)).delete(id);
		assertEquals("customers should be deleted by the service by Id", response, returnedResponse);
	}

	private ResponseEntity<?> mockServiceToReturnCreatedCustomer() {
		final Customer customer = new Customer();
		when(customerService.create(any(Customer.class))).thenReturn(customer);
		return new ResponseEntity<>(customer, HttpStatus.CREATED);
	}

	private ResponseEntity<?> mockServiceToReturnAllCustomers() {
		final Customer customer = new Customer();
		final List<Customer> customers = new ArrayList<Customer>();
		customers.add(customer);
		when(customerService.getAll()).thenReturn(customers);
		return new ResponseEntity<>(customers, HttpStatus.OK);
	}

	private ResponseEntity<?> mockServiceToReturnCustomerFoundById() {
		final Customer customer = new Customer();
		when(customerService.getById(any(UUID.class))).thenReturn(customer);
		return new ResponseEntity<>(customer, HttpStatus.OK);
	}

	private ResponseEntity<?> mockServiceToReturnCustomerDeletedById() {
		customerService.delete(any(UUID.class));
		return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
	}

	private ResponseEntity<?> mockServiceToReturnUpdatedCustomer() {
		final Customer customer = new Customer();
		when(customerService.update(any(UUID.class), any(Customer.class))).thenReturn(customer);
		return new ResponseEntity<>(customer, HttpStatus.OK);
	}

}