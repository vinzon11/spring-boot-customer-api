package com.rit.exam.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Customer {

	@Id
	@GenericGenerator(name = "uuid-gen", strategy = "uuid2")
	@GeneratedValue(generator = "uuid-gen")
	private UUID id;

	@Column(name = "name", nullable = false)
	@NotNull
	@Size(max = 70)
	private String name;

	@Column(name = "address", nullable = false)
	@NotNull
	@Size(max = 255)
	private String address;

	@Column(name = "telephone_number", nullable = false)
	@NotNull
	@Size(max = 12)
	private String telephoneNumber;

	public Customer() {

	}

	public Customer(String name, String address, String telephoneNumber) {
		this.name = name;
		this.address = address;
		this.telephoneNumber = telephoneNumber;
	}

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

}
