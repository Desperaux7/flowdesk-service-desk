package com.flowdesk.aplication.repositories;

import com.flowdesk.aplication.models.Categoria;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface CategoriaRepository extends MongoRepository<Categoria, String> {
    List<Categoria> findBySetorRelacionadoID(String setorRelacionadoID);
    boolean existsBySetorRelacionadoID(String setorRelacionadoID);
}
