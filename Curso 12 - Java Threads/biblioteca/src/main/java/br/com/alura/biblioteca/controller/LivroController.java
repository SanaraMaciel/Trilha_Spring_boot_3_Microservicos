package br.com.alura.biblioteca.controller;

import br.com.alura.biblioteca.dto.DadosAtualizacaoLivro;
import br.com.alura.biblioteca.dto.LivroDTO;
import br.com.alura.biblioteca.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @GetMapping("/{id}")
    public LivroDTO obterLivroPeloId(@PathVariable Long id){
        return livroService.obterLivroPeloId(id);
    }

    @PutMapping("/{livroId}")
    public ResponseEntity<String> atualizarTitulo(@PathVariable Long livroId, @RequestBody DadosAtualizacaoLivro dadosLivro) {
        try {
            livroService.atualizarTituloDoLivro(livroId, dadosLivro.titulo());
            return ResponseEntity.ok("Título atualizado com sucesso!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
