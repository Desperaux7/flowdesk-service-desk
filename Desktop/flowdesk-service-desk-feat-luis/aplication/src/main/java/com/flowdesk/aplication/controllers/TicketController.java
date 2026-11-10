package com.flowdesk.aplication.controllers;

import com.flowdesk.aplication.business.TicketService;
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
    public ResponseEntity<Ticket> salvaTicket(@RequestBody Ticket ticket){
        return ResponseEntity.ok().body(ticketService.salvaTicket(ticket));
    }
    //Busca todos
    @GetMapping
    public ResponseEntity<List<Ticket>> buscaTickets(){
        return ResponseEntity.ok().body(ticketService.buscaTickets());
    }
    //Busca ticket pelo id
    @GetMapping("/{id}")
    public ResponseEntity<Ticket> buscaTicketID(@PathVariable String id){
        return ResponseEntity.ok().body(ticketService.buscaTicketID(id));
    }
    //Update
    @PutMapping
    public ResponseEntity<Ticket> atualizaTicket(@RequestBody Ticket ticket){
        return ResponseEntity.ok().body(ticketService.atualizaTicket(ticket));
    }
    //Deletar ticket
    @DeleteMapping
    public ResponseEntity<Void> deletarTicket(@RequestParam("id") String id){
        ticketService.deletarTicket(id);
        return ResponseEntity.accepted().build();
    }

}
