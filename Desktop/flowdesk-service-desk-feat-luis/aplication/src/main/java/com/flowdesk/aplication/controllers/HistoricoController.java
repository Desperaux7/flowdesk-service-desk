package com.flowdesk.aplication.controllers;

import com.flowdesk.aplication.business.HistoricoService;
import com.flowdesk.aplication.models.Historico;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/historicos")
public class HistoricoController {

    private final HistoricoService historicoService;

    public HistoricoController(HistoricoService historicoService) {
        this.historicoService = historicoService;
    }

    @PostMapping
    public ResponseEntity<Historico> salvaHistorico(@RequestBody Historico historico) {
        return ResponseEntity.ok().body(historicoService.salvaHistorico(historico));
    }

    @GetMapping
    public ResponseEntity<List<Historico>> buscaHistoricos() {
        return ResponseEntity.ok().body(historicoService.buscaHistoricos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Historico> buscaHistoricoID(@PathVariable String id) {
        return ResponseEntity.ok().body(historicoService.buscaHistoricoID(id));
    }

    @PutMapping
    public ResponseEntity<Historico> atualizaHistorico(@RequestBody Historico historico) {
        return ResponseEntity.ok().body(historicoService.atualizaHistorico(historico));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarHistorico(@RequestParam("id") String id) {
        historicoService.deletarHistorico(id);
        return ResponseEntity.accepted().build();
    }
}
