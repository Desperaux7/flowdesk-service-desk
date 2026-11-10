package com.flowdesk.aplication.repositories;

import com.flowdesk.aplication.models.Historico;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HistoricoRepository extends MongoRepository<Historico, String> {
}
