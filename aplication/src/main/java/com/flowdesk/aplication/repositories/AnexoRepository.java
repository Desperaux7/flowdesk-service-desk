package com.flowdesk.aplication.repositories;

import com.flowdesk.aplication.models.Anexo;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface AnexoRepository extends MongoRepository<Anexo, String> {
    List<Anexo> findByTicketID(String ticketID);
    boolean existsByTicketID(String ticketID);
}
