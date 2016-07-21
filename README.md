# spring-boot-customer-api
CRUD customer api using spring boot

## Usage

- Run the application and
- Use the following urls to invoke rest controller methods and see the interactions
  with the hsqldb:
  ``` 
    Create Customer:
        POST http:localhost/exam/v1/customers
        {
          "name": "SM Robinson",
          "address": "Shaw Mandaluyong",
          "telephone": "6411111"
        }
    
    Get ALL Customers:
        GET http:localhost/exam/v1/customers

    Get One Customer:
        GET http:localhost/exam/v1/customers/{Id}
    
    Update Customer:
        PUT http:localhost/exam/v1/customers/{Id}
        {
          "name": "SM Robinson",
          "address": "Cubao QC",
          "telephone": "6411111"
        }
    
    Delete Customer:
        DELETE http:localhost/exam/v1/customers/{Id}
  ```

### Build and run

#### Prerequisites

- Java 7 or higher
- Maven 3

#### From terminal

Go on the project's root folder, then type:

    $ mvn spring-boot:run
    
    or
    
    $ java -jar target/customer-api-1.0.0-SNAPSHOT.jar --server.port=<yourchoiceofport>

#### From Eclipse (Spring Tool Suite)

Import as *Existing Maven Project* and run it as *Spring Boot App*.
