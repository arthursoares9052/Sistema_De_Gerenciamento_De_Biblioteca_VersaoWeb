package com.example.biblioteca.servico;

import com.example.biblioteca.modelo.Livro;
import com.example.biblioteca.repositorio.LivroRepositorio;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LivroServico {

    private final LivroRepositorio livroRepository;

    public LivroServico(LivroRepositorio livroRepository) {
        this.livroRepository = livroRepository;
    }

    @Transactional
    public Livro inserir(Livro livro) {
        boolean existe = livroRepository.findAll().stream()
                .anyMatch(l ->
                        l.getTitulo().equalsIgnoreCase(livro.getTitulo()) &&
                        l.getAutor().equalsIgnoreCase(livro.getAutor())
                );

        if (existe) {
            throw new IllegalArgumentException("Livro já existe");
        }

        return livroRepository.save(livro);
    }

    public List<Livro> listar() {
        return livroRepository.findAll();
    }

    @Transactional
    public void excluir(Integer id) {
        if (!livroRepository.existsById(id)) {
            throw new EntityNotFoundException("Livro não encontrado: " + id);
        }
        livroRepository.deleteById(id);
    }
}
