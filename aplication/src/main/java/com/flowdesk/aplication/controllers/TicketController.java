package com.flowdesk.aplication.controllers;

import com.flowdesk.aplication.business.TicketService;
import com.flowdesk.aplication.dto.TicketResponse;
import com.flowdesk.aplication.models.Ticket;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ticket")
public class TicketController {
    private final TicketService ticketService;
    public TicketController(TicketService ticketService) { this.ticketService = ticketService; }
    //Create
    @PostMapping
    public ResponseEntity<TicketResponse> salvaTicket(@RequestBody Ticket ticket){
        return ResponseEntity.status(201).body(ticketService.salvaTicket(ticket));
    }
    //Busca todos
    @GetMapping
    public ResponseEntity<List<TicketResponse>> buscaTickets(){
        return ResponseEntity.ok().body(ticketService.buscaTickets());
    }

    @GetMapping("/solicitante/{solicitanteID}")
    public ResponseEntity<List<TicketResponse>> buscaPorSolicitante(@PathVariable String solicitanteID) {
        return ResponseEntity.ok(ticketService.buscaPorSolicitante(solicitanteID));
    }

    @GetMapping("/responsavel/{responsavelID}")
    public ResponseEntity<List<TicketResponse>> buscaPorResponsavel(@PathVariable String responsavelID) {
        return ResponseEntity.ok(ticketService.buscaPorResponsavel(responsavelID));
    }

    @GetMapping("/categoria/{categoriaID}")
    public ResponseEntity<List<TicketResponse>> buscaPorCategoria(@PathVariable String categoriaID) {
        return ResponseEntity.ok(ticketService.buscaPorCategoria(categoriaID));
    }

    @GetMapping("/setor/{setorDestinoID}")
    public ResponseEntity<List<TicketResponse>> buscaPorSetorDestino(@PathVariable String setorDestinoID) {
        return ResponseEntity.ok(ticketService.buscaPorSetorDestino(setorDestinoID));
    }

    @GetMapping("/status/{statusID}")
    public ResponseEntity<List<TicketResponse>> buscaPorStatus(@PathVariable String statusID) {
        return ResponseEntity.ok(ticketService.buscaPorStatus(statusID));
    }

    //Busca ticket pelo id
    @GetMapping("/{id}")
    public ResponseEntity<TicketResponse> buscaTicketID(@PathVariable String id){
        return ResponseEntity.ok().body(ticketService.buscaTicketID(id));
    }
    //Update
    @PutMapping
    public ResponseEntity<TicketResponse> atualizaTicket(@RequestBody Ticket ticket){
        return ResponseEntity.ok().body(ticketService.atualizaTicket(ticket));
    }
    //Deletar ticket
    @DeleteMapping
    public ResponseEntity<Void> deletarTicket(@RequestParam("id") String id){
        ticketService.deletarTicket(id);
        return ResponseEntity.noContent().build();
    }

}
