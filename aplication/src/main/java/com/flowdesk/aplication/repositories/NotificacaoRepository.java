package com.flowdesk.aplication.repositories;

import com.flowdesk.aplication.models.Notificacao;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface NotificacaoRepository extends MongoRepository<Notificacao, String> {
    List<Notificacao> findByUsuarioID(String usuarioID);
    boolean existsByUsuarioID(String usuarioID);
}
