package com.flowdesk.aplication.repositories;

import com.flowdesk.aplication.models.Comentario;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ComentarioRepository extends MongoRepository<Comentario, String> {
    List<Comentario> findByTicketID(String ticketID);
    List<Comentario> findByAutorID(String autorID);
    boolean existsByTicketID(String ticketID);
    boolean existsByAutorID(String autorID);
}
