package com.example.biblioteca.repositorio;

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
class LivroRepositorioTest {

    @Autowired
    private LivroRepositorio livroRepositorio;

    @Test
    void deveSalvarLivro() {
        Livro livro = new Livro();
        livro.setTitulo("Repo Teste " + System.nanoTime());
        livro.setAutor("Autor Repo " + System.nanoTime());
        livro.setGenero("Teste");
        livro.setDataPublicacao(LocalDate.now());
        livro.setPdf(new byte[]{1, 2, 3});

        Livro salvo = livroRepositorio.save(livro);

        assertNotNull(salvo.getId());
    }
}
