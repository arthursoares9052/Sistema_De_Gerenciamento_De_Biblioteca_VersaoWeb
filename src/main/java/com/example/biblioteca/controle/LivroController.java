package com.example.biblioteca.controle;

import com.example.biblioteca.modelo.Livro;
import com.example.biblioteca.servico.LivroServico;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class LivroController {

    private final LivroServico livroServico;

    public LivroController(LivroServico livroServico) {
        this.livroServico = livroServico;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("livro", new Livro());
        return "livros/inserir";
    }

    @GetMapping("listar")
    public String listar(Model model) {
        model.addAttribute("livros", livroServico.listar());
        return "livros/listar";
    }
}
