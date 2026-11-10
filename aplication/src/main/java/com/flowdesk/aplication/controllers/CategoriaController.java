package com.flowdesk.aplication.controllers;

import com.flowdesk.aplication.business.CategoriaService;
import com.flowdesk.aplication.models.Categoria;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping
    public ResponseEntity<Categoria> salvaCategoria(@RequestBody Categoria categoria) {
        return ResponseEntity.ok().body(categoriaService.salvaCategoria(categoria));
    }

    @GetMapping
    public ResponseEntity<List<Categoria>> buscaCategorias() {
        return ResponseEntity.ok().body(categoriaService.buscaCategorias());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> buscaCategoriaID(@PathVariable String id) {
        return ResponseEntity.ok().body(categoriaService.buscaCategoriaID(id));
    }

    @PutMapping
    public ResponseEntity<Categoria> atualizaCategoria(@RequestBody Categoria categoria) {
        return ResponseEntity.ok().body(categoriaService.atualizaCategoria(categoria));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarCategoria(@RequestParam("id") String id) {
        categoriaService.deletarCategoria(id);
        return ResponseEntity.accepted().build();
    }
}
