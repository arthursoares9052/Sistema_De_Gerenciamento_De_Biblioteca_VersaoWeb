package com.example.biblioteca.servico;

import com.example.biblioteca.modelo.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class LivroServicoTest {

    @Autowired
    private LivroServico livroServico;

    @Test
    void deveInserirLivro() {
        Livro livro = new Livro();
        livro.setTitulo("Servico Teste " + System.nanoTime());
        livro.setAutor("Autor Servico " + System.nanoTime());
        livro.setGenero("Teste");
        livro.setDataPublicacao(LocalDate.now());
        livro.setPdf(new byte[]{9, 9, 9});

        Livro salvo = livroServico.inserir(livro);

        assertNotNull(salvo.getId());
    }
}
