package com.flowdesk.aplication.repositories;

import com.flowdesk.aplication.models.Historico;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface HistoricoRepository extends MongoRepository<Historico, String> {
    List<Historico> findByTicketID(String ticketID);
    List<Historico> findByAutorAcaoID(String autorAcaoID);
    boolean existsByTicketID(String ticketID);
    boolean existsByAutorAcaoID(String autorAcaoID);
}
