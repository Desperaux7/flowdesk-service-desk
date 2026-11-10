package com.flowdesk.aplication.business;


import com.flowdesk.aplication.models.Ticket;
import com.flowdesk.aplication.repositories.TicketRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    public TicketService(TicketRepository ticketRepository){ this.ticketRepository = ticketRepository; }
    //create novo ticket :>
    public Ticket salvaTicket(Ticket ticket){ return ticketRepository.save(ticket); }
    //Procura todos os tickets
    public List<Ticket> buscaTickets(){ return ticketRepository.findAll(); }
    //Procura um ticket pelo id :>
    public Ticket buscaTicketID(String id){
        return ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket não encontrado"));
    }
    //Update em um ticket :>, caso não tenha esse ticket ele cria um novo
    public Ticket atualizaTicket(Ticket ticket){
        Optional<Ticket> ticketExiste = ticketRepository.findById(ticket.getId());
        if (ticketExiste.isPresent()){
            return ticketRepository.save(ticket);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket não encontrado");
    }
    //Deleta um ticket
    public void deletarTicket(String id){
        ticketRepository.deleteById(id);
    }

}
