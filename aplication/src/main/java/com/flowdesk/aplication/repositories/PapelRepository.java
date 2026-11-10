package com.flowdesk.aplication.repositories;

import com.flowdesk.aplication.models.Papel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PapelRepository extends MongoRepository<Papel, String> {
}
