package com.example.biblioteca.controle;

import com.example.biblioteca.modelo.Livro;
import com.example.biblioteca.servico.LivroServico;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/livros")
@CrossOrigin(origins = "*")
public class LivroApiController {

    private final LivroServico livroServico;

    public LivroApiController(LivroServico livroServico) {
        this.livroServico = livroServico;
    }

    @GetMapping
    public List<Livro> listar() {
        try {
            return livroServico.listar();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao listar livros: " + e.getMessage());
        }
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Livro inserir(
            @RequestParam String titulo,
            @RequestParam String autor,
            @RequestParam(required = false) String genero,
            @RequestParam(required = false) String dataPublicacao,
            @RequestParam MultipartFile pdf
    ) {
        try {
            Livro livro = new Livro();
            livro.setTitulo(titulo);
            livro.setAutor(autor);
            livro.setGenero(genero);

            if (dataPublicacao != null && !dataPublicacao.isBlank()) {
                livro.setDataPublicacao(LocalDate.parse(dataPublicacao));
            }

            livro.setPdf(pdf.getBytes());

            return livroServico.inserir(livro);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao inserir livro: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        try {
            livroServico.excluir(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao excluir livro: " + e.getMessage());
        }
    }
}
