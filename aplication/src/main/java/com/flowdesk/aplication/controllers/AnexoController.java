package com.flowdesk.aplication.controllers;

import com.flowdesk.aplication.business.AnexoService;
import com.flowdesk.aplication.dto.AnexoResponse;
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
    public ResponseEntity<AnexoResponse> salvaAnexo(@RequestBody Anexo anexo) {
        return ResponseEntity.status(201).body(anexoService.salvaAnexo(anexo));
    }

    @GetMapping
    public ResponseEntity<List<AnexoResponse>> buscaAnexos() {
        return ResponseEntity.ok().body(anexoService.buscaAnexos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnexoResponse> buscaAnexoID(@PathVariable String id) {
        return ResponseEntity.ok().body(anexoService.buscaAnexoID(id));
    }

    @PutMapping
    public ResponseEntity<AnexoResponse> atualizaAnexo(@RequestBody Anexo anexo) {
        return ResponseEntity.ok().body(anexoService.atualizaAnexo(anexo));
    }

    @GetMapping("/ticket/{ticketID}")
    public ResponseEntity<List<AnexoResponse>> buscaPorTicket(@PathVariable String ticketID) {
        return ResponseEntity.ok(anexoService.buscaPorTicket(ticketID));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarAnexo(@RequestParam("id") String id) {
        anexoService.deletarAnexo(id);
        return ResponseEntity.noContent().build();
    }
}
