package com.rit.exam.usecase.exception;

import java.util.UUID;

import com.rit.exam.util.CustomerErrorList;

public class CustomerAlreadyExistsException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6608956706702708735L;
	private String code;

	public CustomerAlreadyExistsException(final UUID id) {
		super(String.format(CustomerErrorList.CUSTOMER_ALREADY_EXISTS_MESSAGE + ": Id=%s", id));
		this.code = CustomerErrorList.CUSTOMER_ALREADY_EXISTS_CODE;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}