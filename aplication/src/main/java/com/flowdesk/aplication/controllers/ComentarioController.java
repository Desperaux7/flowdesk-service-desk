package com.flowdesk.aplication.controllers;

import com.flowdesk.aplication.business.ComentarioService;
import com.flowdesk.aplication.dto.ComentarioResponse;
import com.flowdesk.aplication.models.Comentario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comentarios")
public class ComentarioController {

    private final ComentarioService comentarioService;

    public ComentarioController(ComentarioService comentarioService) {
        this.comentarioService = comentarioService;
    }

    @PostMapping
    public ResponseEntity<ComentarioResponse> salvaComentario(@RequestBody Comentario comentario) {
        return ResponseEntity.status(201).body(comentarioService.salvaComentario(comentario));
    }

    @GetMapping
    public ResponseEntity<List<ComentarioResponse>> buscaComentarios() {
        return ResponseEntity.ok().body(comentarioService.buscaComentarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComentarioResponse> buscaComentarioID(@PathVariable String id) {
        return ResponseEntity.ok().body(comentarioService.buscaComentarioID(id));
    }

    @PutMapping
    public ResponseEntity<ComentarioResponse> atualizaComentario(@RequestBody Comentario comentario) {
        return ResponseEntity.ok().body(comentarioService.atualizaComentario(comentario));
    }

    @GetMapping("/ticket/{ticketID}")
    public ResponseEntity<List<ComentarioResponse>> buscaPorTicket(@PathVariable String ticketID) {
        return ResponseEntity.ok(comentarioService.buscaPorTicket(ticketID));
    }

    @GetMapping("/autor/{autorID}")
    public ResponseEntity<List<ComentarioResponse>> buscaPorAutor(@PathVariable String autorID) {
        return ResponseEntity.ok(comentarioService.buscaPorAutor(autorID));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarComentario(@RequestParam("id") String id) {
        comentarioService.deletarComentario(id);
        return ResponseEntity.noContent().build();
    }
}
