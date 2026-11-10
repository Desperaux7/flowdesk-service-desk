package com.flowdesk.aplication.repositories;

import com.flowdesk.aplication.models.Comentario;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ComentarioRepository extends MongoRepository<Comentario, String> {
}
