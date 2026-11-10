package com.flowdesk.aplication.controllers;

import com.flowdesk.aplication.business.ComentarioService;
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
    public ResponseEntity<Comentario> salvaComentario(@RequestBody Comentario comentario) {
        return ResponseEntity.ok().body(comentarioService.salvaComentario(comentario));
    }

    @GetMapping
    public ResponseEntity<List<Comentario>> buscaComentarios() {
        return ResponseEntity.ok().body(comentarioService.buscaComentarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comentario> buscaComentarioID(@PathVariable String id) {
        return ResponseEntity.ok().body(comentarioService.buscaComentarioID(id));
    }

    @PutMapping
    public ResponseEntity<Comentario> atualizaComentario(@RequestBody Comentario comentario) {
        return ResponseEntity.ok().body(comentarioService.atualizaComentario(comentario));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarComentario(@RequestParam("id") String id) {
        comentarioService.deletarComentario(id);
        return ResponseEntity.accepted().build();
    }
}
