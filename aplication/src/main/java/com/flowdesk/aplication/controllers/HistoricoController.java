package com.flowdesk.aplication.controllers;

import com.flowdesk.aplication.business.HistoricoService;
import com.flowdesk.aplication.dto.HistoricoResponse;
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
    public ResponseEntity<HistoricoResponse> salvaHistorico(@RequestBody Historico historico) {
        return ResponseEntity.status(201).body(historicoService.salvaHistorico(historico));
    }

    @GetMapping
    public ResponseEntity<List<HistoricoResponse>> buscaHistoricos() {
        return ResponseEntity.ok().body(historicoService.buscaHistoricos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoricoResponse> buscaHistoricoID(@PathVariable String id) {
        return ResponseEntity.ok().body(historicoService.buscaHistoricoID(id));
    }

    @PutMapping
    public ResponseEntity<HistoricoResponse> atualizaHistorico(@RequestBody Historico historico) {
        return ResponseEntity.ok().body(historicoService.atualizaHistorico(historico));
    }

    @GetMapping("/ticket/{ticketID}")
    public ResponseEntity<List<HistoricoResponse>> buscaPorTicket(@PathVariable String ticketID) {
        return ResponseEntity.ok(historicoService.buscaPorTicket(ticketID));
    }

    @GetMapping("/autor/{autorAcaoID}")
    public ResponseEntity<List<HistoricoResponse>> buscaPorAutorAcao(@PathVariable String autorAcaoID) {
        return ResponseEntity.ok(historicoService.buscaPorAutorAcao(autorAcaoID));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarHistorico(@RequestParam("id") String id) {
        historicoService.deletarHistorico(id);
        return ResponseEntity.noContent().build();
    }
}
