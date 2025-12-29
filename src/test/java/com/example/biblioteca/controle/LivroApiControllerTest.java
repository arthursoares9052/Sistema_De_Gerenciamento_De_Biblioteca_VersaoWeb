package com.example.biblioteca.controle;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class LivroApiControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void deveListarLivros() {
        ResponseEntity<String> resposta =
                restTemplate.getForEntity("/api/livros", String.class);

        assertEquals(200, resposta.getStatusCode().value());
    }
}
