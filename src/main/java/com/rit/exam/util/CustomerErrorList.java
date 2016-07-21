package com.rit.exam.util;

public final class CustomerErrorList {

	public static String CUSTOMER_ALREADY_EXISTS_CODE = "1001";
	public static String CUSTOMER_ALREADY_EXISTS_MESSAGE = "There is already a customer with the same name";

	private CustomerErrorList() {
		throw new AssertionError();
	}
}
