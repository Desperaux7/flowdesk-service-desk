package com.flowdesk.aplication.controllers;

import com.flowdesk.aplication.business.NotificacaoService;
import com.flowdesk.aplication.models.Notificacao;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificacoes")
public class NotificacaoController {

    private final NotificacaoService notificacaoService;

    public NotificacaoController(NotificacaoService notificacaoService) {
        this.notificacaoService = notificacaoService;
    }

    @PostMapping
    public ResponseEntity<Notificacao> salvaNotificacao(@RequestBody Notificacao notificacao) {
        return ResponseEntity.ok().body(notificacaoService.salvaNotificacao(notificacao));
    }

    @GetMapping
    public ResponseEntity<List<Notificacao>> buscaNotificacoes() {
        return ResponseEntity.ok().body(notificacaoService.buscaNotificacoes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notificacao> buscaNotificacaoID(@PathVariable String id) {
        return ResponseEntity.ok().body(notificacaoService.buscaNotificacaoID(id));
    }

    @PutMapping
    public ResponseEntity<Notificacao> atualizaNotificacao(@RequestBody Notificacao notificacao) {
        return ResponseEntity.ok().body(notificacaoService.atualizaNotificacao(notificacao));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarNotificacao(@RequestParam("id") String id) {
        notificacaoService.deletarNotificacao(id);
        return ResponseEntity.accepted().build();
    }
}
