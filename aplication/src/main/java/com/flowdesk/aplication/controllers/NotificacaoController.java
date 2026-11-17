package com.flowdesk.aplication.controllers;

import com.flowdesk.aplication.business.NotificacaoService;
import com.flowdesk.aplication.dto.NotificacaoResponse;
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
    public ResponseEntity<NotificacaoResponse> salvaNotificacao(@RequestBody Notificacao notificacao) {
        return ResponseEntity.status(201).body(notificacaoService.salvaNotificacao(notificacao));
    }

    @GetMapping
    public ResponseEntity<List<NotificacaoResponse>> buscaNotificacoes() {
        return ResponseEntity.ok().body(notificacaoService.buscaNotificacoes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificacaoResponse> buscaNotificacaoID(@PathVariable String id) {
        return ResponseEntity.ok().body(notificacaoService.buscaNotificacaoID(id));
    }

    @PutMapping
    public ResponseEntity<NotificacaoResponse> atualizaNotificacao(@RequestBody Notificacao notificacao) {
        return ResponseEntity.ok().body(notificacaoService.atualizaNotificacao(notificacao));
    }

    @GetMapping("/usuario/{usuarioID}")
    public ResponseEntity<List<NotificacaoResponse>> buscaPorUsuario(@PathVariable String usuarioID) {
        return ResponseEntity.ok(notificacaoService.buscaPorUsuario(usuarioID));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarNotificacao(@RequestParam("id") String id) {
        notificacaoService.deletarNotificacao(id);
        return ResponseEntity.noContent().build();
    }
}
