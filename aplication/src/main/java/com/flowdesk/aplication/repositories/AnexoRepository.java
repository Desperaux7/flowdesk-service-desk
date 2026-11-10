package com.flowdesk.aplication.repositories;

import com.flowdesk.aplication.models.Anexo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnexoRepository extends MongoRepository<Anexo, String> {
}
