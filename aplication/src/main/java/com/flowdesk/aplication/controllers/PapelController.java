package com.flowdesk.aplication.controllers;

import com.flowdesk.aplication.business.PapelService;
import com.flowdesk.aplication.models.Papel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/papeis")
public class PapelController {

    private final PapelService papelService;

    public PapelController(PapelService papelService) {
        this.papelService = papelService;
    }

    @PostMapping
    public ResponseEntity<Papel> salvaPapel(@RequestBody Papel papel) {
        return ResponseEntity.ok().body(papelService.salvaPapel(papel));
    }

    @GetMapping
    public ResponseEntity<List<Papel>> buscaPapeis() {
        return ResponseEntity.ok().body(papelService.buscaPapeis());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Papel> buscaPapelID(@PathVariable String id) {
        return ResponseEntity.ok().body(papelService.buscaPapelID(id));
    }

    @PutMapping
    public ResponseEntity<Papel> atualizaPapel(@RequestBody Papel papel) {
        return ResponseEntity.ok().body(papelService.atualizaPapel(papel));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarPapel(@RequestParam("id") String id) {
        papelService.deletarPapel(id);
        return ResponseEntity.accepted().build();
    }
}
