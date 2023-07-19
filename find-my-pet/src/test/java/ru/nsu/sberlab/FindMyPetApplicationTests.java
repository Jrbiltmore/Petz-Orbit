package ru.nsu.sberlab;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.client.TestRestTemplate.HttpClientOption;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FindMyPetApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders headers;

    @BeforeEach
    void setup() {
        // Initialize the headers for each test
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    void contextLoads() {
        // Verify that the Spring application context loads successfully
        assertNotNull(restTemplate);
    }

    @Test
    void testGetPetById() {
        // Prepare the request entity
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        // Send a GET request to the API endpoint
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/pets/123", HttpMethod.GET, requestEntity, String.class);

        // Verify the response status code
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify the response body
        String responseBody = response.getBody();
        assertNotNull(responseBody);
        // Add additional assertions for the response body if necessary
    }

    @Test
    void testCreatePet() {
        // Prepare the request body
        String requestBody = "{\"name\": \"Tom\", \"age\": 3}";

        // Prepare the request entity
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // Send a POST request to the API endpoint
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/pets", HttpMethod.POST, requestEntity, String.class);

        // Verify the response status code
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Verify the response body
        String responseBody = response.getBody();
        assertNotNull(responseBody);
        // Add additional assertions for the response body if necessary

        // Extract the created pet ID from the response body
        Long petId = extractPetId(responseBody);

        // Send a GET request to the created pet's endpoint
        ResponseEntity<String> getResponse = restTemplate.exchange(
                "http://localhost:" + port + "/api/pets/" + petId, HttpMethod.GET, requestEntity, String.class);

        // Verify the response status code for the GET request
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());

        // Verify the retrieved pet's details
        String getResponseBody = getResponse.getBody();
        assertNotNull(getResponseBody);
        // Add additional assertions for the retrieved pet's details if necessary
    }

    @Test
    void testUpdatePet() {
        // Prepare the request body
        String requestBody = "{\"name\": \"Jerry\", \"age\": 4}";

        // Prepare the request entity
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // Send a PUT request to the API endpoint
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/pets/123", HttpMethod.PUT, requestEntity, String.class);

        // Verify the response status code
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify the response body
        String responseBody = response.getBody();
        assertNotNull(responseBody);
        // Add additional assertions for the response body if necessary

        // Send a GET request to the updated pet's endpoint
        ResponseEntity<String> getResponse = restTemplate.exchange(
                "http://localhost:" + port + "/api/pets/123", HttpMethod.GET, requestEntity, String.class);

        // Verify the response status code for the GET request
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());

        // Verify the retrieved pet's details
        String getResponseBody = getResponse.getBody();
        assertNotNull(getResponseBody);
        // Add additional assertions for the retrieved pet's details if necessary
    }

    @Test
    void testDeletePet() {
        // Prepare the request entity
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        // Send a DELETE request to the API endpoint
        ResponseEntity<Void> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/pets/123", HttpMethod.DELETE, requestEntity, Void.class);

        // Verify the response status code
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        // Send a GET request to the deleted pet's endpoint
        ResponseEntity<String> getResponse = restTemplate.exchange(
                "http://localhost:" + port + "/api/pets/123", HttpMethod.GET, requestEntity, String.class);

        // Verify the response status code for the GET request
        assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
    }

    private Long extractPetId(String responseBody) {
        // Implement the logic to extract the pet ID from the response body
        // and return it as a Long value
        // Modify this method based on the structure of your response body
        return Long.parseLong(responseBody);
    }
}
