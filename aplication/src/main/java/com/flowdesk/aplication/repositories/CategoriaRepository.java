package com.flowdesk.aplication.repositories;

import com.flowdesk.aplication.models.Categoria;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoriaRepository extends MongoRepository<Categoria, String> {
}
