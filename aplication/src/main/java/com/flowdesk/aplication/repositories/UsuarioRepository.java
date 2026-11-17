package com.flowdesk.aplication.repositories;

import com.flowdesk.aplication.models.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByEmailIgnoreCase(String email);
    List<Usuario> findBySetorId(String setorId);
    List<Usuario> findByPapelId(String papelId);
    boolean existsBySetorId(String setorId);
    boolean existsByPapelId(String papelId);
}
