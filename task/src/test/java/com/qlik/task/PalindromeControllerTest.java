package com.qlik.task;

import com.qlik.task.dto.PalindromeDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import springfox.documentation.spring.web.plugins.DocumentationPluginsBootstrapper;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PalindromeControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PalindromeRepository palindromeRepository;

    @MockBean
    private DocumentationPluginsBootstrapper documentationPluginsBootstrapper;


    @Test
    public void shouldSaveInDB() throws URISyntaxException {
        String url = "http://localhost:" + port + "/api/v1/palindrome/add";
        String requestStr = "adddpddda";
        HttpHeaders headers = new HttpHeaders();

        URI uri = new URI(url);

        HttpEntity<String> request = new HttpEntity<>(requestStr, headers);

        ResponseEntity<PalindromeDto> result = this.restTemplate.postForEntity(uri + "/" + requestStr, request, PalindromeDto.class);
        PalindromeDto palindromeDto = result.getBody();

        assertTrue(palindromeDto.isPalindrome());

        Long itemId = palindromeDto.id();

        Optional<Palindrome> p = palindromeRepository.findById(itemId);

        if (p.isPresent()) {
            assertEquals(palindromeDto.text(), p.get().getText());
        }
    }

    @Test
    public void shouldReturnTwoEntities() {
        String addUrl = "http://localhost:" + port + "/api/v1/palindrome/add";
        String allUrl = "http://localhost:" + port + "/api/v1/palindrome/all";
        String requestStr1 = "adddpddda";
        String requestStr2 = "adddpdddb";
        HttpHeaders headers = new HttpHeaders();


        HttpEntity<String> addRequest1 = new HttpEntity<>(requestStr1, headers);
        HttpEntity<String> addRequest2 = new HttpEntity<>(requestStr2, headers);

        this.restTemplate.postForEntity(addUrl + "/" + requestStr1, addRequest1, PalindromeDto.class);
        this.restTemplate.postForEntity(addUrl + "/" + requestStr2, addRequest2, PalindromeDto.class);

        PalindromeDto[] response = this.restTemplate.getForObject(allUrl, PalindromeDto[].class);

        assertEquals(2, response.length);
    }

    @Test
    public void shouldFindTextInDB() throws URISyntaxException {
        String url = "http://localhost:" + port + "/api/v1/palindrome/add";
        String requestStr = "adddpddda";
        HttpHeaders headers = new HttpHeaders();

        URI uri = new URI(url);

        HttpEntity<String> request = new HttpEntity<>(requestStr, headers);

        ResponseEntity<PalindromeDto> result = this.restTemplate.postForEntity(uri + "/" + requestStr, request, PalindromeDto.class);
        PalindromeDto palindromeDto = result.getBody();

        assertTrue(palindromeDto.isPalindrome());

        String itemText = palindromeDto.text();

        Optional<Palindrome> p = palindromeRepository.findByText(itemText);

        if (p.isPresent()) {
            assertEquals(palindromeDto.text(), p.get().getText());
        }
    }

    @Test
    public void shouldFindTwoContainingEntities() {
        String addUrl = "http://localhost:" + port + "/api/v1/palindrome/add";
        String containingUrl = "http://localhost:" + port + "/api/v1/palindrome/findByTextContaining";
        String addRequestStr1 = "kok";
        String addRequestStr2 = "ko";
        HttpHeaders headers = new HttpHeaders();

        HttpEntity<String> addRequest1 = new HttpEntity<>(addRequestStr1, headers);
        HttpEntity<String> addRequest2 = new HttpEntity<>(addRequestStr2, headers);

        this.restTemplate.postForEntity(addUrl + "/" + addRequestStr1, addRequest1, PalindromeDto.class);
        this.restTemplate.postForEntity(addUrl + "/" + addRequestStr2, addRequest2, PalindromeDto.class);

        String containingRequest = "k";

        PalindromeDto[] response = this.restTemplate.postForObject(containingUrl, containingRequest, PalindromeDto[].class);

        assertEquals(2, response.length);
    }

    @Test
    public void shouldFindOneEntity() {
        String addUrl = "http://localhost:" + port + "/api/v1/palindrome/add";
        String containingUrl = "http://localhost:" + port + "/api/v1/palindrome/findAllByIsPalindrome";
        String addRequestStr1 = "kok";
        String addRequestStr2 = "oooa";
        HttpHeaders headers = new HttpHeaders();

        HttpEntity<String> addRequest1 = new HttpEntity<>(addRequestStr1, headers);
        HttpEntity<String> addRequest2 = new HttpEntity<>(addRequestStr2, headers);

        this.restTemplate.postForEntity(addUrl + "/" + addRequestStr1, addRequest1, PalindromeDto.class);
        this.restTemplate.postForEntity(addUrl + "/" + addRequestStr2, addRequest2, PalindromeDto.class);

        boolean requestStr = true;
        HttpEntity<Boolean> containingRequest = new HttpEntity<>(requestStr, headers);

        PalindromeDto[] response = this.restTemplate.postForObject(containingUrl + "/" + requestStr, containingRequest, PalindromeDto[].class);

        assertEquals(1, response.length);
        assertEquals("kok", response[0].text());
    }

    @Test
    public void shouldFindTwoEntity() {
        String addUrl = "http://localhost:" + port + "/api/v1/palindrome/add";
        String containingUrl = "http://localhost:" + port + "/api/v1/palindrome/findAllByIsPalindrome";
        String addRequestStr1 = "kokk";
        String addRequestStr2 = "oooa";
        HttpHeaders headers = new HttpHeaders();

        HttpEntity<String> addRequest1 = new HttpEntity<>(addRequestStr1, headers);
        HttpEntity<String> addRequest2 = new HttpEntity<>(addRequestStr2, headers);

        this.restTemplate.postForEntity(addUrl + "/" + addRequestStr1, addRequest1, PalindromeDto.class);
        this.restTemplate.postForEntity(addUrl + "/" + addRequestStr2, addRequest2, PalindromeDto.class);

        boolean requestStr = false;
        HttpEntity<Boolean> containingRequest = new HttpEntity<>(requestStr, headers);

        Palindrome[] response = this.restTemplate.postForObject(containingUrl + "/" + requestStr, containingRequest, Palindrome[].class);

        response[0].isPalindrome();
        assertEquals(2, response.length);
    }

    @Test
    public void shouldDeleteEntityById() {
        String addUrl = "http://localhost:" + port + "/api/v1/palindrome/add";
        String deleteUrl = "http://localhost:" + port + "/api/v1/palindrome/deleteById";

        String addRequestStr = "adddpddda";
        HttpHeaders headers = new HttpHeaders();

        HttpEntity<String> addRequest = new HttpEntity<>(addRequestStr, headers);
        ResponseEntity<PalindromeDto> result = this.restTemplate.postForEntity(addUrl + "/" + addRequestStr, addRequest, PalindromeDto.class);

        assertTrue(result.getBody().isPalindrome());

        assertEquals(1, palindromeRepository.findAll().size());

        deleteUrl = deleteUrl + "/" + result.getBody().id();

        this.restTemplate.delete(deleteUrl);

        assertEquals(0, palindromeRepository.findAll().size());
    }
}
