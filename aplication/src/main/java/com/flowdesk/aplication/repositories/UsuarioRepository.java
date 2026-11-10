package com.flowdesk.aplication.repositories;

import com.flowdesk.aplication.models.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {
}
