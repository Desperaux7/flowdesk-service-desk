package com.flowdesk.aplication.controllers;

import com.flowdesk.aplication.business.CategoriaService;
import com.flowdesk.aplication.dto.CategoriaResponse;
import com.flowdesk.aplication.dto.TicketResponse;
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
    public ResponseEntity<CategoriaResponse> salvaCategoria(@RequestBody Categoria categoria) {
        return ResponseEntity.status(201).body(categoriaService.salvaCategoria(categoria));
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponse>> buscaCategorias() {
        return ResponseEntity.ok().body(categoriaService.buscaCategorias());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponse> buscaCategoriaID(@PathVariable String id) {
        return ResponseEntity.ok().body(categoriaService.buscaCategoriaID(id));
    }

    @GetMapping("/{id}/tickets")
    public ResponseEntity<List<TicketResponse>> buscaTickets(@PathVariable String id) {
        return ResponseEntity.ok(categoriaService.buscaTickets(id));
    }

    @PutMapping
    public ResponseEntity<CategoriaResponse> atualizaCategoria(@RequestBody Categoria categoria) {
        return ResponseEntity.ok().body(categoriaService.atualizaCategoria(categoria));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarCategoria(@RequestParam("id") String id) {
        categoriaService.deletarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}
