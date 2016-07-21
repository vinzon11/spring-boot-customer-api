package com.rit.exam;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;

import java.util.Arrays;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.rit.exam.domain.Customer;
import com.rit.exam.jpa.CustomerRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class CustomerTest {

	@Inject
	CustomerRepository repository;

	Customer vince;
	Customer steve;
	Customer ben;

	@Value("${local.server.port}")
	int port;

	@Before
	public void setUp() {
		vince = new Customer("Vince", "PH", "9549149");
		steve = new Customer("Steve", "NZD", "3812834");
		ben = new Customer("Ben", "NZD", "8931237");

		repository.deleteAll();
		repository.save(Arrays.asList(vince, steve, ben));

		RestAssured.port = port;
	}

	@Test
	public void shouldCreateKaleb() {
		Customer kaleb = new Customer("Kaleb", "NZD", "8137134");
		given().body(kaleb).contentType(ContentType.JSON).when().post("/exam/v1/customers").then()
				.statusCode(HttpStatus.SC_CREATED).body("name", Matchers.is("Kaleb"));
	}

	@Test
	public void shouldGetVince() {
		UUID vinceId = vince.getId();

		when().get("/exam/v1/customers/{id}", vinceId).then().statusCode(HttpStatus.SC_OK)
				.body("name", Matchers.is("Vince")).body("id", Matchers.is(vinceId.toString()));
	}

	@Test
	public void shouldNotCreateVince() {
		Customer newVince = new Customer("Vince", "NZD", "8137134");
		given().body(newVince).contentType(ContentType.JSON).when().post("/exam/v1/customers").then()
				.statusCode(HttpStatus.SC_CONFLICT).body("errorCode", Matchers.is("1001"));
	}

	@Test
	public void shouldUpdateSteve() {
		UUID steveId = steve.getId();
		steve.setName("Stephen");
		given().body(steve).contentType(ContentType.JSON).when().put("/exam/v1/customers/{id}", steveId).then()
				.statusCode(HttpStatus.SC_OK).body("name", Matchers.is("Stephen"))
				.body("id", Matchers.is(steveId.toString()));
	}

	@Test
	public void shouldGetAll() {
		when().get("/exam/v1/customers").then().statusCode(HttpStatus.SC_OK).body("name",
				Matchers.hasItems("Vince", "Steve", "Ben"));
	}

	@Test
	public void shouldDeletePluto() {
		UUID benId = ben.getId();

		when().delete("/exam/v1/customers/{id}", benId).then().statusCode(HttpStatus.SC_NO_CONTENT);
	}
}
