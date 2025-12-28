package com.example.biblioteca.repositorio;

import com.example.biblioteca.modelo.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepositorio extends JpaRepository<Livro, Integer>{
    
}
