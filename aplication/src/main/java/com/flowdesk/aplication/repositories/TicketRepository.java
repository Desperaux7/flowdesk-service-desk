package com.flowdesk.aplication.repositories;

import com.flowdesk.aplication.models.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface TicketRepository extends MongoRepository<Ticket, String> {
    List<Ticket> findBySolicitanteID(String solicitanteID);
    List<Ticket> findByResponsavelID(String responsavelID);
    List<Ticket> findByCategoriaID(String categoriaID);
    List<Ticket> findBySetorDestinoID(String setorDestinoID);
    List<Ticket> findByStatusID(String statusID);
}
