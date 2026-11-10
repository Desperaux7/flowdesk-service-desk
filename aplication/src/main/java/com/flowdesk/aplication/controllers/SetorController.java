package com.flowdesk.aplication.controllers;

import com.flowdesk.aplication.business.SetorService;
import com.flowdesk.aplication.models.Setor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/setores")
public class SetorController {

    private final SetorService setorService;

    public SetorController(SetorService setorService) {
        this.setorService = setorService;
    }

    @PostMapping
    public ResponseEntity<Setor> salvaSetor(@RequestBody Setor setor) {
        return ResponseEntity.ok().body(setorService.salvaSetor(setor));
    }

    @GetMapping
    public ResponseEntity<List<Setor>> buscaSetores() {
        return ResponseEntity.ok().body(setorService.buscaSetores());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Setor> buscaSetorID(@PathVariable String id) {
        return ResponseEntity.ok().body(setorService.buscaSetorID(id));
    }

    @PutMapping
    public ResponseEntity<Setor> atualizaSetor(@RequestBody Setor setor) {
        return ResponseEntity.ok().body(setorService.atualizaSetor(setor));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarSetor(@RequestParam("id") String id) {
        setorService.deletarSetor(id);
        return ResponseEntity.accepted().build();
    }
}
