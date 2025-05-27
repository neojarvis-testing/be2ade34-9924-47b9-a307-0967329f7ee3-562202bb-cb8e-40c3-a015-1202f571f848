package com.examly.apigateway;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Assertions;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ApigatewayApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    public String customerToken;
    private String tellerToken;
    private String managerToken;
    private int customerUserId;

    private ObjectMapper objectMapper = new ObjectMapper(); 

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @Test
    @Order(1)
    void backend_testRegisterCustomer() throws Exception {
        int userId = 1; // Example userId for test
        
        String requestBody = "{"
            + "\"userId\": " + userId + ","
            + "\"email\": \"customer@gmail.com\","
            + "\"password\": \"customer@1234\","
            + "\"username\": \"customer\","
            + "\"userRole\": \"Customer\","
            + "\"mobileNumber\": \"1234567890\""
            + "}";

        ResponseEntity<String> response = restTemplate.postForEntity("/api/users/register",
                new HttpEntity<>(requestBody, createHeaders()), String.class);

        // Assert status is Created
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Parse the response body
        JsonNode responseBody = objectMapper.readTree(response.getBody());
        int registeredUserId = responseBody.get("userId").asInt();
        Assertions.assertEquals(userId, registeredUserId, "UserId should match the provided one");

        // Store userId for further use if needed
        customerUserId = registeredUserId;
        System.out.println("Registered Customer UserId: " + customerUserId);
    }

    @Test
    @Order(2)
    void backend_testRegisterTeller() throws Exception {
        int userId = 2; // Example userId for test
        
        String requestBody = "{"
            + "\"userId\": " + userId + ","
            + "\"email\": \"teller@gmail.com\","
            + "\"password\": \"teller@1234\","
            + "\"username\": \"teller\","
            + "\"userRole\": \"Teller\","
            + "\"mobileNumber\": \"1234567890\""
            + "}";

        ResponseEntity<String> response = restTemplate.postForEntity("/api/users/register",
                new HttpEntity<>(requestBody, createHeaders()), String.class);

        // Assert status is Created
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Parse the response body
        JsonNode responseBody = objectMapper.readTree(response.getBody());
        int registeredUserId = responseBody.get("userId").asInt();
        Assertions.assertEquals(userId, registeredUserId, "UserId should match the provided one");

        // Store token for further use if needed
        int loanManagerUserId = registeredUserId;
        System.out.println("Registered Financial Consultant UserId: " + registeredUserId);
    }

    @Test
    @Order(3)
    void backend_testRegisterManager() throws Exception {
        int userId = 3; // Example userId for test
        
        String requestBody = "{"
            + "\"userId\": " + userId + ","
            + "\"email\": \"manager@gmail.com\","
            + "\"password\": \"manager@1234\","
            + "\"username\": \"manager\","
            + "\"userRole\": \"Manager\","
            + "\"mobileNumber\": \"1234567890\""
            + "}";

        ResponseEntity<String> response = restTemplate.postForEntity("/api/users/register",
                new HttpEntity<>(requestBody, createHeaders()), String.class);

        // Assert status is Created
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Parse the response body
        JsonNode responseBody = objectMapper.readTree(response.getBody());
        int registeredUserId = responseBody.get("userId").asInt();
        Assertions.assertEquals(userId, registeredUserId, "UserId should match the provided one");

        // Store token for further use if needed
        int branchManagerUserId = registeredUserId;
        System.out.println("Registered Regional Manager UserId: " + registeredUserId);
    }

    @Test
    @Order(4)
    void backend_testLoginCustomer() throws Exception, JsonProcessingException {
        String requestBody = "{\"email\": \"customer@gmail.com\", \"password\": \"customer@1234\"}";

        ResponseEntity<String> response = restTemplate.postForEntity("/api/users/login",
                new HttpEntity<>(requestBody, createHeaders()), String.class);

        // Parse the response body
        JsonNode responseBody = objectMapper.readTree(response.getBody());
        String token = responseBody.get("token").asText();
        customerToken = token;
        customerUserId = responseBody.get("userId").asInt();

        // Assert status is OK
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(token);
        Assertions.assertNotNull(customerUserId, "UserId should not be null");
    }


	@Test
    @Order(5)
    void backend_testLoginTeller() throws Exception, JsonProcessingException {
        String requestBody = "{\"email\": \"teller@gmail.com\", \"password\": \"teller@1234\"}";

        ResponseEntity<String> response = restTemplate.postForEntity("/api/users/login",
                new HttpEntity<>(requestBody, createHeaders()), String.class);

        JsonNode responseBody = objectMapper.readTree(response.getBody());
        String token = responseBody.get("token").asText();
        tellerToken = token;

        // Assert status is OK
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(token);
    }





    @Test
    @Order(6)
    void backend_testLoginManager() throws Exception, JsonProcessingException {
        String requestBody = "{\"email\": \"manager@gmail.com\", \"password\": \"manager@1234\"}";

        ResponseEntity<String> response = restTemplate.postForEntity("/api/users/login",
                new HttpEntity<>(requestBody, createHeaders()), String.class);

        JsonNode responseBody = objectMapper.readTree(response.getBody());
        String token = responseBody.get("token").asText();
        managerToken = token;

        // Assert status is OK
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(token);
    }



    @Test
    @Order(7)
    void backend_testAddAccount_AsCustomer() throws Exception {
        Assertions.assertNotNull(customerToken, "Customer token should not be null");

        // Define the request body for creating an account
        String requestBody = "{"
            + "\"accountHolderName\": \"John Doe\","
            + "\"accountType\": \"Savings\","
            + "\"balance\": 1000.0,"
            + "\"proofOfIdentity\": \"some-identity-proof-string\","
            + "\"user\": {"
            + "\"userId\": 1"  // Replace with an actual user ID from your system
            + "}"
            + "}";

        // Set up the headers with Authorization token
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + customerToken);

        // Create the HttpEntity for the request
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // Make the POST request to create an account
        ResponseEntity<String> response = restTemplate.exchange("/api/account", HttpMethod.POST, requestEntity, String.class);

        // Assert status is CREATED (201)
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Optionally log the response
        System.out.println("Response status: " + response.getStatusCode());
        System.out.println("Response body: " + response.getBody());
    }




	@Test
    @Order(8)
    void backend_testAddAccount_AsTeller() throws Exception {
        Assertions.assertNotNull(tellerToken, "Teller token should not be null");

        // Define the request body for creating an account
        String requestBody = "{"
            + "\"accountHolderName\": \"Jane Doe\","
            + "\"accountType\": \"Checking\","
            + "\"balance\": 2000.0,"
            + "\"proofOfIdentity\": \"another-identity-proof-string\","
            + "\"user\": {"
            + "\"userId\": 2"  // Replace with an actual user ID from your system
            + "}"
            + "}";

        // Set up the headers with Authorization token
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + tellerToken);

        // Create the HttpEntity for the request
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // Make the POST request to create an account
        ResponseEntity<String> response = restTemplate.exchange("/api/account", HttpMethod.POST, requestEntity, String.class);

        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

        // Optionally log the response
        System.out.println("Response status for Teller: " + response.getStatusCode());
        System.out.println("Response body for Teller: " + response.getBody());
    }



	@Test
    @Order(9)
    void backend_testAddAccount_AsManager() throws Exception {
        Assertions.assertNotNull(managerToken, "Manager token should not be null");

        // Define the request body for creating an account
        String requestBody = "{"
            + "\"accountHolderName\": \"Alice Smith\","
            + "\"accountType\": \"Business\","
            + "\"balance\": 5000.0,"
            + "\"proofOfIdentity\": \"different-identity-proof-string\","
            + "\"user\": {"
            + "\"userId\": 3"  // Replace with an actual user ID from your system
            + "}"
            + "}";

        // Set up the headers with Authorization token
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + managerToken);

        // Create the HttpEntity for the request
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // Make the POST request to create an account
        ResponseEntity<String> response = restTemplate.exchange("/api/account", HttpMethod.POST, requestEntity, String.class);

        // Assert status is UNAUTHORIZED (403)
        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

        // Optionally log the response
        System.out.println("Response status for Manager: " + response.getStatusCode());
        System.out.println("Response body for Manager: " + response.getBody());
    }



	@Test
    @Order(10)
    void backend_testAccessAccount_AsTeller() throws Exception {
        Assertions.assertNotNull(tellerToken, "Teller token should not be null");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + tellerToken);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange("/api/account", HttpMethod.GET, requestEntity, String.class);

        // Assert status is OK (200)
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        // Optionally log or assert the response body if needed
        System.out.println("Response status for Teller: " + response.getStatusCode());
        System.out.println("Response body for Teller: " + response.getBody());
    }




	@Test
    @Order(11)
    void backend_testAccessAccount_AsManager() throws Exception {
        Assertions.assertNotNull(managerToken, "Manager token should not be null");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + managerToken);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange("/api/account", HttpMethod.GET, requestEntity, String.class);

        // Assert status is OK (200)
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        // Optionally log or assert the response body if needed
        System.out.println("Response status for Manager: " + response.getStatusCode());
        System.out.println("Response body for Manager: " + response.getBody());
    }


	@Test
    @Order(12)
    void backend_testAccessAccount_AsCustomer() throws Exception {
        Assertions.assertNotNull(customerToken, "Customer token should not be null");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + customerToken);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange("/api/account", HttpMethod.GET, requestEntity, String.class);

        // Assert status is FORBIDDEN (403) for unauthorized access
        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

        // Optionally log or assert the response body if needed
        System.out.println("Response status for Customer (Unauthorized): " + response.getStatusCode());
        System.out.println("Response body for Customer (Unauthorized): " + response.getBody());
    }


	@Test
    @Order(13)
    void backend_testAccessAccountById_AsCustomer() throws Exception {
        Assertions.assertNotNull(customerToken, "Customer token should not be null");

        int testAccountId = 1; // Replace with an actual accountId from your system

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + customerToken);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange("/api/account/" + testAccountId, HttpMethod.GET, requestEntity, String.class);

        // Assert status is OK (200) for authorized access
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        // Optionally log or assert the response body if needed
        System.out.println("Response status for Customer (Authorized): " + response.getStatusCode());
        System.out.println("Response body for Customer (Authorized): " + response.getBody());
    }


	@Test
    @Order(14)
    void backend_testAccessAccountById_AsTeller() throws Exception {
        Assertions.assertNotNull(tellerToken, "Teller token should not be null");

        int testAccountId = 1; // Replace with an actual accountId from your system

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + tellerToken);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange("/api/account/" + testAccountId, HttpMethod.GET, requestEntity, String.class);

        // Assert status is OK (200) for authorized access
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        // Optionally log or assert the response body if needed
        System.out.println("Response status for Teller (Authorized): " + response.getStatusCode());
        System.out.println("Response body for Teller (Authorized): " + response.getBody());
    }


	@Test
    @Order(15)
    void backend_testAccessAccountById_AsManager() throws Exception {
        Assertions.assertNotNull(managerToken, "Manager token should not be null");

        int testAccountId = 1; // Replace with an actual accountId from your system

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + managerToken);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange("/api/account/" + testAccountId, HttpMethod.GET, requestEntity, String.class);

        // Assert status is OK (200) for authorized access
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        // Optionally log or assert the response body if needed
        System.out.println("Response status for Manager (Authorized): " + response.getStatusCode());
        System.out.println("Response body for Manager (Authorized): " + response.getBody());
    }



	@Test
    @Order(16)
    void backend_testUpdateAccount_AsManager() throws Exception {
        Assertions.assertNotNull(managerToken, "Manager token should not be null");

        int testAccountId = 1; // Replace with an actual accountId from your system

        // Define the request body for updating an account
        String requestBody = "{"
            + "\"accountHolderName\": \"John Doe Updated\"," // New values
            + "\"accountType\": \"Savings\"," 
            + "\"balance\": 1500.0," 
            + "\"proofOfIdentity\": \"updated-identity-proof-string\""
            + "}";

        // Set up the headers with Authorization token
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + managerToken);

        // Create the HttpEntity for the request
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // Make the PUT request to update an account
        ResponseEntity<String> response = restTemplate.exchange("/api/account/" + testAccountId, HttpMethod.PUT, requestEntity, String.class);

        // Assert status is OK (200) for authorized access
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        // Optionally log or assert the response body if needed
        System.out.println("Response status for Manager (Authorized): " + response.getStatusCode());
        System.out.println("Response body for Manager (Authorized): " + response.getBody());
    }



	@Test
    @Order(17)
	void backend_testGetuserAccountByuserIdAsCustomer() throws Exception {
        Assertions.assertNotNull(customerToken, "Customer token should not be null");

        int testUserId = 1; // Replace with an actual userId from your system

        // Set up the headers with Authorization token
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + customerToken);

        // Create the HttpEntity for the request
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        // Make the GET request to retrieve account details for a user
        ResponseEntity<String> response = restTemplate.exchange("/api/account/user/" + testUserId, HttpMethod.GET, requestEntity, String.class);

        // Assert status is OK (200) for authorized access
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        // Optionally log or assert the response body if needed
        System.out.println("Response status for Customer (Authorized): " + response.getStatusCode());
        System.out.println("Response body for Customer (Authorized): " + response.getBody());
    }

    @Test
    @Order(18)
    void backend_testGetuserAccountByuserIdManager() throws Exception {
        Assertions.assertNotNull(managerToken, "Manager token should not be null");

        int testUserId = 1; // Replace with an actual userId from your system

        // Set up the headers with Authorization token
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + managerToken);

        // Create the HttpEntity for the request
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        // Make the GET request to retrieve account details for a user
        ResponseEntity<String> response = restTemplate.exchange("/api/account/user/" + testUserId, HttpMethod.GET, requestEntity, String.class);

        // Assert status is OK (200) for authorized access
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        // Optionally log or assert the response body if needed
        System.out.println("Response status for Manager (Authorized): " + response.getStatusCode());
        System.out.println("Response body for Manager (Authorized): " + response.getBody());
    }


	@Test
    @Order(19)
	   void backend_testGetuserAccountByuserIdAsTeller() throws Exception {
        Assertions.assertNotNull(tellerToken, "Teller token should not be null");

        int testUserId = 1; // Replace with an actual userId from your system

        // Set up the headers with Authorization token
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + tellerToken);

        // Create the HttpEntity for the request
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        // Make the GET request to retrieve account details for a user
        ResponseEntity<String> response = restTemplate.exchange("/api/account/user/" + testUserId, HttpMethod.GET, requestEntity, String.class);

        // Assert status is OK (200) for authorized access
        Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

        // Optionally log or assert the response body if needed
        System.out.println("Response status for Teller (Authorized): " + response.getStatusCode());
        System.out.println("Response body for Teller (Authorized): " + response.getBody());
    }




	@Test
	@Order(20)
	void backend_testCreateRecurringDeposit_AsCustomer() throws Exception {
		Assertions.assertNotNull(customerToken, "Customer token should not be null");
	
		// Define the request body for creating a RecurringDeposit
		String dateCreated = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		String dateClosed = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
	
		String requestBody = "{"
			+ "\"rdId\": 1,"  // Include rdId in the request body
			+ "\"monthlyDeposit\": 100.0,"
			+ "\"interestRate\": 5.0,"
			+ "\"tenureMonths\": 12,"
			+ "\"maturityAmount\": 1200.0,"
			+ "\"status\": \"Active\","
			+ "\"dateCreated\": \"" + dateCreated + "\","
			+ "\"dateClosed\": \"" + dateClosed + "\","
			+ "\"account\": {"
			+ "\"accountId\": 1"  // Replace with an actual accountId
			+ "},"
			+ "\"user\": {"
			+ "\"userId\": 1"  // Replace with an actual userId
			+ "}"
			+ "}";
	
		// Set up the headers with Authorization token
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + customerToken);
	
		// Create the HttpEntity for the request
		HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
	
		// Make the POST request to create a recurring deposit
		ResponseEntity<String> response = restTemplate.exchange("/api/recurringdeposit", HttpMethod.POST, requestEntity, String.class);
	
		// Assert status is CREATED (201)
		Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
	
		// Optionally log the response
		System.out.println("Response status for Customer (Authorized): " + response.getStatusCode());
		System.out.println("Response body for Customer (Authorized): " + response.getBody());
	}
	


@Test
@Order(21)
void backend_testCreateRecurringDeposit_AsTeller() throws Exception {
    Assertions.assertNotNull(tellerToken, "Teller token should not be null");
    
    // Define the request body for creating a RecurringDeposit
    String dateCreated = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    String dateClosed = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

    String requestBody = "{"
        + "\"rdId\": 2,"  // Include rdId in the request body
        + "\"monthlyDeposit\": 150.0,"
        + "\"interestRate\": 6.0,"
        + "\"tenureMonths\": 24,"
        + "\"maturityAmount\": 3600.0,"
        + "\"status\": \"Active\","
        + "\"dateCreated\": \"" + dateCreated + "\","
        + "\"dateClosed\": \"" + dateClosed + "\","
        + "\"account\": {"
        + "\"accountId\": 2"  // Replace with an actual accountId
        + "},"
        + "\"user\": {"
        + "\"userId\": 2"  // Replace with an actual userId
        + "}"
        + "}";

    // Set up the headers with Authorization token for Teller
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", "Bearer " + tellerToken);

    // Create the HttpEntity for the request
    HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

    // Make the POST request to create a recurring deposit
    ResponseEntity<String> response = restTemplate.exchange("/api/recurringdeposit", HttpMethod.POST, requestEntity, String.class);

    // Assert status is FORBIDDEN (403)
    Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

    // Optionally log the response
    System.out.println("Response status for Teller (Unauthorized): " + response.getStatusCode());
    System.out.println("Response body for Teller (Unauthorized): " + response.getBody());
}

@Test
@Order(22)
void backend_testCreateRecurringDeposit_AsManager() throws Exception {
    Assertions.assertNotNull(managerToken, "Manager token should not be null");
    
    // Define the request body for creating a RecurringDeposit
    String dateCreated = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    String dateClosed = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

    String requestBody = "{"
        + "\"rdId\": 3,"  // Include rdId in the request body
        + "\"monthlyDeposit\": 200.0,"
        + "\"interestRate\": 7.0,"
        + "\"tenureMonths\": 36,"
        + "\"maturityAmount\": 7200.0,"
        + "\"status\": \"Active\","
        + "\"dateCreated\": \"" + dateCreated + "\","
        + "\"dateClosed\": \"" + dateClosed + "\","
        + "\"account\": {"
        + "\"accountId\": 3"  // Replace with an actual accountId
        + "},"
        + "\"user\": {"
        + "\"userId\": 3"  // Replace with an actual userId
        + "}"
        + "}";

    // Set up the headers with Authorization token for Manager
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", "Bearer " + managerToken);

    // Create the HttpEntity for the request
    HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

    // Make the POST request to create a recurring deposit
    ResponseEntity<String> response = restTemplate.exchange("/api/recurringdeposit", HttpMethod.POST, requestEntity, String.class);

    // Assert status is FORBIDDEN (403)
    Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

    // Optionally log the response
    System.out.println("Response status for Manager (Unauthorized): " + response.getStatusCode());
    System.out.println("Response body for Manager (Unauthorized): " + response.getBody());
}


@Test
@Order(23)
void backend_testGetRecurringDeposit_AsManager() throws Exception {
    Assertions.assertNotNull(managerToken, "Manager token should not be null");
    
    // Set up the headers with Authorization token for Manager
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", "Bearer " + managerToken);

    // Create the HttpEntity for the request
    HttpEntity<String> requestEntity = new HttpEntity<>(headers);

    // Make the GET request to retrieve recurring deposits
    ResponseEntity<String> response = restTemplate.exchange("/api/recurringdeposit", HttpMethod.GET, requestEntity, String.class);

    // Assert status is OK (200)
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

    // Optionally log the response
    System.out.println("Response status for Manager (Authorized): " + response.getStatusCode());
    System.out.println("Response body for Manager (Authorized): " + response.getBody());
}

@Test
@Order(24)
void backend_testGetRecurringDeposit_AsTeller() throws Exception {
    Assertions.assertNotNull(tellerToken, "Teller token should not be null");
    
    // Set up the headers with Authorization token for Teller
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", "Bearer " + tellerToken);

    // Create the HttpEntity for the request
    HttpEntity<String> requestEntity = new HttpEntity<>(headers);

    // Make the GET request to retrieve recurring deposits
    ResponseEntity<String> response = restTemplate.exchange("/api/recurringdeposit", HttpMethod.GET, requestEntity, String.class);

    // Assert status is OK (200)
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

    // Optionally log the response
    System.out.println("Response status for Teller (Authorized): " + response.getStatusCode());
    System.out.println("Response body for Teller (Authorized): " + response.getBody());
}


@Test
@Order(25)
void backend_testGetRecurringDeposit_AsCustomer() throws Exception {
    Assertions.assertNotNull(customerToken, "Customer token should not be null");
    
    // Set up the headers with Authorization token for Customer
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", "Bearer " + customerToken);

    // Create the HttpEntity for the request
    HttpEntity<String> requestEntity = new HttpEntity<>(headers);

    // Make the GET request to retrieve recurring deposits
    ResponseEntity<String> response = restTemplate.exchange("/api/recurringdeposit", HttpMethod.GET, requestEntity, String.class);

    // Assert status is FORBIDDEN (403)
    Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

    // Optionally log the response
    System.out.println("Response status for Customer (Unauthorized): " + response.getStatusCode());
    System.out.println("Response body for Customer (Unauthorized): " + response.getBody());
}




@Test
@Order(26)
void backend_testCreateFixedDeposit_AsCustomer() throws Exception {
    // Assert that the customerToken is not null
    Assertions.assertNotNull(customerToken, "Customer token should not be null");

    // Define the request body for creating a FixedDeposit
    String dateCreated = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    String requestBody = "{"
        + "\"principalAmount\": 5000.0,"
        + "\"interestRate\": 6.5,"
        + "\"tenureMonths\": 24,"
        + "\"maturityAmount\": 6000.0,"
        + "\"status\": \"Active\","
        + "\"dateCreated\": \"" + dateCreated + "\","
        + "\"dateClosed\": null,"
        + "\"account\": {"
        + "\"accountId\": 1"  // Replace with an actual accountId
        + "},"
        + "\"user\": {"
        + "\"userId\": 1"  // Replace with an actual userId
        + "}"
        + "}";

    // Set up the headers with Authorization token for Customer
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", "Bearer " + customerToken);

    // Create the HttpEntity for the request
    HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

    // Make the POST request to create a fixed deposit
    ResponseEntity<String> response = restTemplate.exchange("/api/fixeddeposit", HttpMethod.POST, requestEntity, String.class);

    // Assert status is CREATED (201)
    Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());

    // Optionally log the response
    System.out.println("Response status for Customer (Authorized): " + response.getStatusCode());
    System.out.println("Response body for Customer (Authorized): " + response.getBody());
}

@Test
@Order(27)
void backend_testCreateFixedDeposit_AsManager() throws Exception {
    // Define the request body for creating a FixedDeposit
    String dateCreated = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    String requestBody = "{"
        + "\"principalAmount\": 5000.0,"
        + "\"interestRate\": 6.5,"
        + "\"tenureMonths\": 24,"
        + "\"maturityAmount\": 6000.0,"
        + "\"status\": \"Active\","
        + "\"dateCreated\": \"" + dateCreated + "\","
        + "\"dateClosed\": null,"
        + "\"account\": {"
        + "\"accountId\": 1"  // Replace with an actual accountId
        + "},"
        + "\"user\": {"
        + "\"userId\": 1"  // Replace with an actual userId
        + "}"
        + "}";

    // Set up the headers with a token for a Manager
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", "Bearer " + managerToken);  // Use a token for a Manager

    // Create the HttpEntity for the request
    HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

    // Make the POST request to create a fixed deposit
    ResponseEntity<String> response = restTemplate.exchange("/api/fixeddeposit", HttpMethod.POST, requestEntity, String.class);

    // Assert status is FORBIDDEN (403)
    Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

    // Optionally log the response
    System.out.println("Response status for Manager: " + response.getStatusCode());
    System.out.println("Response body for Manager: " + response.getBody());
}



@Test
@Order(28)
void backend_testCreateFixedDeposit_AsTeller() throws Exception {
    // Define the request body for creating a FixedDeposit
    String dateCreated = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    String requestBody = "{"
        + "\"principalAmount\": 5000.0,"
        + "\"interestRate\": 6.5,"
        + "\"tenureMonths\": 24,"
        + "\"maturityAmount\": 6000.0,"
        + "\"status\": \"Active\","
        + "\"dateCreated\": \"" + dateCreated + "\","
        + "\"dateClosed\": null,"
        + "\"account\": {"
        + "\"accountId\": 1"  // Replace with an actual accountId
        + "},"
        + "\"user\": {"
        + "\"userId\": 1"  // Replace with an actual userId
        + "}"
        + "}";

    // Set up the headers with a token for a Teller
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", "Bearer " + tellerToken);  // Use a token for a Teller

    // Create the HttpEntity for the request
    HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

    // Make the POST request to create a fixed deposit
    ResponseEntity<String> response = restTemplate.exchange("/api/fixeddeposit", HttpMethod.POST, requestEntity, String.class);

    // Assert status is FORBIDDEN (403)
    Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

    // Optionally log the response
    System.out.println("Response status for Teller: " + response.getStatusCode());
    System.out.println("Response body for Teller: " + response.getBody());
}




@Test
@Order(29)
void backend_testGetFixedDeposit_AsManager() throws Exception {
    // Define the request URI for fetching FixedDeposits
    String requestUri = "/api/fixeddeposit";

    // Set up the headers with a token for a Manager
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", "Bearer " + managerToken);  // Use a token for a Manager

    // Create the HttpEntity for the request
    HttpEntity<String> requestEntity = new HttpEntity<>(headers);

    // Make the GET request to fetch FixedDeposits
    ResponseEntity<String> response = restTemplate.exchange(requestUri, HttpMethod.GET, requestEntity, String.class);

    // Assert status is OK (200)
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

    // Optionally log the response
    System.out.println("Response status for Manager: " + response.getStatusCode());
    System.out.println("Response body for Manager: " + response.getBody());
}


@Test
@Order(30)
void backend_testGetFixedDeposit_AsTeller() throws Exception {
    // Define the request URI for fetching FixedDeposits
    String requestUri = "/api/fixeddeposit";

    // Set up the headers with a token for a Teller
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", "Bearer " + tellerToken);  // Use a token for a Teller

    // Create the HttpEntity for the request
    HttpEntity<String> requestEntity = new HttpEntity<>(headers);

    // Make the GET request to fetch FixedDeposits
    ResponseEntity<String> response = restTemplate.exchange(requestUri, HttpMethod.GET, requestEntity, String.class);

    // Assert status is OK (200)
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

    // Optionally log the response
    System.out.println("Response status for Teller: " + response.getStatusCode());
    System.out.println("Response body for Teller: " + response.getBody());
}


@Test
@Order(31)
void backend_testGetFixedDeposit_AsCustomer() throws Exception {
    // Define the request URI for fetching FixedDeposits
    String requestUri = "/api/fixeddeposit";

    // Set up the headers with a token for a Customer
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", "Bearer " + customerToken);  // Use a token for a Customer

    // Create the HttpEntity for the request
    HttpEntity<String> requestEntity = new HttpEntity<>(headers);

    // Make the GET request to fetch FixedDeposits
    ResponseEntity<String> response = restTemplate.exchange(requestUri, HttpMethod.GET, requestEntity, String.class);

    // Assert status is FORBIDDEN (403)
    Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

    // Optionally log the response
    System.out.println("Response status for Customer: " + response.getStatusCode());
    System.out.println("Response body for Customer: " + response.getBody());
}



@Test
@Order(32)
void backend_testGetFixedDepositByUser_AsCustomer() throws Exception {
    // Define the userId for which FixedDeposit details are being requested
    int userId = 1; // Replace with an actual userId
    String requestUri = "/api/fixeddeposit/user/" + userId;

    // Set up the headers with a token for a Customer
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", "Bearer " + customerToken);  // Use a token for a Customer

    // Create the HttpEntity for the request
    HttpEntity<String> requestEntity = new HttpEntity<>(headers);

    // Make the GET request to fetch FixedDeposit details for the specific user
    ResponseEntity<String> response = restTemplate.exchange(requestUri, HttpMethod.GET, requestEntity, String.class);

    // Assert status is OK (200)
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

    // Optionally log the response
    System.out.println("Response status for Customer: " + response.getStatusCode());
    System.out.println("Response body for Customer: " + response.getBody());
}


@Test
@Order(33)
void backend_testGetFixedDepositByUser_AsManager() throws Exception {
    // Define the userId for which FixedDeposit details are being requested
    int userId = 1; // Replace with an actual userId
    String requestUri = "/api/fixeddeposit/user/" + userId;

    // Set up the headers with a token for a Manager
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", "Bearer " + managerToken);  // Use a token for a Manager

    // Create the HttpEntity for the request
    HttpEntity<String> requestEntity = new HttpEntity<>(headers);

    // Make the GET request to fetch FixedDeposit details for the specific user
    ResponseEntity<String> response = restTemplate.exchange(requestUri, HttpMethod.GET, requestEntity, String.class);

    // Assert status is FORBIDDEN (403)
    Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

    // Optionally log the response
    System.out.println("Response status for Manager: " + response.getStatusCode());
    System.out.println("Response body for Manager: " + response.getBody());
}

@Test
@Order(34)
void backend_testGetFixedDepositByUser_AsTeller() throws Exception {
    // Define the userId for which FixedDeposit details are being requested
    int userId = 1; // Replace with an actual userId
    String requestUri = "/api/fixeddeposit/user/" + userId;

    // Set up the headers with a token for a Teller
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", "Bearer " + tellerToken);  // Use a token for a Teller

    // Create the HttpEntity for the request
    HttpEntity<String> requestEntity = new HttpEntity<>(headers);

    // Make the GET request to fetch FixedDeposit details for the specific user
    ResponseEntity<String> response = restTemplate.exchange(requestUri, HttpMethod.GET, requestEntity, String.class);

    // Assert status is FORBIDDEN (403)
    Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

    // Optionally log the response
    System.out.println("Response status for Teller: " + response.getStatusCode());
    System.out.println("Response body for Teller: " + response.getBody());
}











@Test
@Order(35)
void backend_testGetRecurringDepositByUser_AsCustomer() throws Exception {
    // Define the userId for which RecurringDeposit details are being requested
    int userId = 1; // Replace with an actual userId
    String requestUri = "/api/recurringdeposit/user/" + userId;

    // Set up the headers with a token for a Customer
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", "Bearer " + customerToken);  // Use a token for a Customer

    // Create the HttpEntity for the request
    HttpEntity<String> requestEntity = new HttpEntity<>(headers);

    // Make the GET request to fetch RecurringDeposit details for the specific user
    ResponseEntity<String> response = restTemplate.exchange(requestUri, HttpMethod.GET, requestEntity, String.class);

    // Assert status is OK (200)
    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

    // Optionally log the response
    System.out.println("Response status for Customer: " + response.getStatusCode());
    System.out.println("Response body for Customer: " + response.getBody());
}


@Test
@Order(36)
void backend_testGetRecurringDepositByUser_AsManager() throws Exception {
    // Define the userId for which RecurringDeposit details are being requested
    int userId = 1; // Replace with an actual userId
    String requestUri = "/api/recurringdeposit/user/" + userId;

    // Set up the headers with a token for a Manager
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", "Bearer " + managerToken);  // Use a token for a Manager

    // Create the HttpEntity for the request
    HttpEntity<String> requestEntity = new HttpEntity<>(headers);

    // Make the GET request to fetch RecurringDeposit details for the specific user
    ResponseEntity<String> response = restTemplate.exchange(requestUri, HttpMethod.GET, requestEntity, String.class);

    // Assert status is FORBIDDEN (403)
    Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

    // Optionally log the response
    System.out.println("Response status for Manager: " + response.getStatusCode());
    System.out.println("Response body for Manager: " + response.getBody());
}



@Test
@Order(37)
void backend_testGetRecurringDepositByUser_AsTeller() throws Exception {
    // Define the userId for which RecurringDeposit details are being requested
    int userId = 1; // Replace with an actual userId
    String requestUri = "/api/recurringdeposit/user/" + userId;

    // Set up the headers with a token for a Teller
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", "Bearer " + tellerToken);  // Use a token for a Teller

    // Create the HttpEntity for the request
    HttpEntity<String> requestEntity = new HttpEntity<>(headers);

    // Make the GET request to fetch RecurringDeposit details for the specific user
    ResponseEntity<String> response = restTemplate.exchange(requestUri, HttpMethod.GET, requestEntity, String.class);

    // Assert status is FORBIDDEN (403)
    Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

    // Optionally log the response
    System.out.println("Response status for Teller: " + response.getStatusCode());
    System.out.println("Response body for Teller: " + response.getBody());
}



@Test
@Order(38)
void backend_testInvalidCredentialsLoginCustomer() throws Exception {
    // Use incorrect credentials
    String requestBody = "{\"email\": \"customer@gmail.com\", \"password\": \"wrongpassword\"}";

    ResponseEntity<String> response = restTemplate.postForEntity("/api/users/login",
            new HttpEntity<>(requestBody, createHeaders()), String.class);

    // Assert status is FORBIDDEN (403)
    Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

    
}




@Test
@Order(39)
void backend_testInvalidCredentialsLoginTeller() throws Exception {
    // Use incorrect credentials
    String requestBody = "{\"email\": \"teller@gmail.com\", \"password\": \"wrongpassword\"}";

    ResponseEntity<String> response = restTemplate.postForEntity("/api/users/login",
            new HttpEntity<>(requestBody, createHeaders()), String.class);

    // Assert status is FORBIDDEN (403)
    Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

    

}



@Test
@Order(40)
void backend_testInvalidCredentialsLoginManager() throws Exception {
    // Use incorrect credentials
    String requestBody = "{\"email\": \"Manager@gmail.com\", \"password\": \"wrongpassword\"}";

    ResponseEntity<String> response = restTemplate.postForEntity("/api/users/login",
            new HttpEntity<>(requestBody, createHeaders()), String.class);

    // Assert status is FORBIDDEN (403)
    Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

    
}


}
















