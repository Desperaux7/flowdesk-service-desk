package com.flowdesk.aplication.controllers;

import com.flowdesk.aplication.business.AnexoService;
import com.flowdesk.aplication.models.Anexo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/anexos")
public class AnexoController {

    private final AnexoService anexoService;

    public AnexoController(AnexoService anexoService) {
        this.anexoService = anexoService;
    }

    @PostMapping
    public ResponseEntity<Anexo> salvaAnexo(@RequestBody Anexo anexo) {
        return ResponseEntity.ok().body(anexoService.salvaAnexo(anexo));
    }

    @GetMapping
    public ResponseEntity<List<Anexo>> buscaAnexos() {
        return ResponseEntity.ok().body(anexoService.buscaAnexos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Anexo> buscaAnexoID(@PathVariable String id) {
        return ResponseEntity.ok().body(anexoService.buscaAnexoID(id));
    }

    @PutMapping
    public ResponseEntity<Anexo> atualizaAnexo(@RequestBody Anexo anexo) {
        return ResponseEntity.ok().body(anexoService.atualizaAnexo(anexo));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarAnexo(@RequestParam("id") String id) {
        anexoService.deletarAnexo(id);
        return ResponseEntity.accepted().build();
    }
}
