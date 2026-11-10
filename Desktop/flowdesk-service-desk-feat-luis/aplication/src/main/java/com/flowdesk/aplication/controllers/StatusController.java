package com.flowdesk.aplication.controllers;

import com.flowdesk.aplication.business.StatusService;
import com.flowdesk.aplication.models.Status;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/status")
public class StatusController {

    private final StatusService statusService;

    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @PostMapping
    public ResponseEntity<Status> salvaStatus(@RequestBody Status status) {
        return ResponseEntity.ok().body(statusService.salvaStatus(status));
    }

    @GetMapping
    public ResponseEntity<List<Status>> buscaStatus() {
        return ResponseEntity.ok().body(statusService.buscaStatus());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Status> buscaStatusID(@PathVariable String id) {
        return ResponseEntity.ok().body(statusService.buscaStatusID(id));
    }

    @PutMapping
    public ResponseEntity<Status> atualizaStatus(@RequestBody Status status) {
        return ResponseEntity.ok().body(statusService.atualizaStatus(status));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletarStatus(@RequestParam("id") String id) {
        statusService.deletarStatus(id);
        return ResponseEntity.accepted().build();
    }
}
