package com.flowdesk.aplication.repositories;

import com.flowdesk.aplication.models.Notificacao;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificacaoRepository extends MongoRepository<Notificacao, String> {
}
