package com.rit.exam.api.rest;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rit.exam.domain.Customer;
import com.rit.exam.domain.RestErrorInfo;
import com.rit.exam.usecase.CustomerService;
import com.rit.exam.usecase.exception.CustomerAlreadyExistsException;

@RestController
@RequestMapping(value = "exam/v1/customers")
public class CustomerRestController {

	private final CustomerService customerService;

	@Inject
	public CustomerRestController(final CustomerService customerService) {
		this.customerService = customerService;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createCustomer(@RequestBody @Valid final Customer customer) {

		Customer createdCustomer = customerService.create(customer);
		return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> listAllCustomers() {

		List<Customer> customers = customerService.getAll();
		return new ResponseEntity<>(customers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getCustomerById(@PathVariable final UUID id) {

		Customer createdCustomer = customerService.getById(id);
		if (createdCustomer == null) {
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(createdCustomer, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateCustomerById(@PathVariable final UUID id,
			@RequestBody @Valid final Customer customer) {

		Customer updatedCustomer = customerService.update(id, customer);
		return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteCustomerById(@PathVariable final UUID id) {
		customerService.delete(id);
		return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
	}

	@ExceptionHandler
	public ResponseEntity<?> handleCustomerAlreadyExistsException(CustomerAlreadyExistsException e) {
		RestErrorInfo errorInfo = new RestErrorInfo();
		errorInfo.setErrorCode(e.getCode());
		errorInfo.setErrorMessage(e.getMessage());
		return new ResponseEntity<>(errorInfo, HttpStatus.CONFLICT);
	}
}