package com.flowdesk.aplication.repositories;

import com.flowdesk.aplication.models.Setor;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SetorRepository extends MongoRepository<Setor, String> {
}
