package com.flowdesk.aplication.business;

import com.flowdesk.aplication.common.ReferenceIds;
import com.flowdesk.aplication.common.RelationshipMapper;
import com.flowdesk.aplication.dto.NotificacaoResponse;
import com.flowdesk.aplication.models.Notificacao;
import com.flowdesk.aplication.repositories.NotificacaoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
public class NotificacaoService {

    private final NotificacaoRepository notificacaoRepository;
    private final RelationshipMapper relationshipMapper;

    public NotificacaoService(NotificacaoRepository notificacaoRepository, RelationshipMapper relationshipMapper) {
        this.notificacaoRepository = notificacaoRepository;
        this.relationshipMapper = relationshipMapper;
    }

    public NotificacaoResponse salvaNotificacao(Notificacao notificacao) {
        if (notificacao.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não informe ID ao criar notificação");
        }
        validar(notificacao);
        notificacao.setLida(notificacao.getLida() == null ? false : notificacao.getLida());
        notificacao.setDataEnvio(notificacao.getDataEnvio() == null
                ? java.time.LocalDateTime.now() : notificacao.getDataEnvio());
        return relationshipMapper.notificacao(notificacaoRepository.save(notificacao));
    }

    public List<NotificacaoResponse> buscaNotificacoes() {
        return notificacaoRepository.findAll().stream().map(relationshipMapper::notificacao).toList();
    }

    public List<NotificacaoResponse> buscaPorUsuario(String usuarioID) {
        relationshipMapper.requireUsuario(usuarioID, "Usuário");
        return notificacaoRepository.findByUsuarioID(usuarioID).stream()
                .map(relationshipMapper::notificacao).toList();
    }

    public NotificacaoResponse buscaNotificacaoID(String id) {
        ReferenceIds.require(id, "ID da notificação");
        return notificacaoRepository.findById(id)
                .map(relationshipMapper::notificacao)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Notificação não encontrada"));
    }

    public NotificacaoResponse atualizaNotificacao(Notificacao notificacao) {
        ReferenceIds.require(notificacao.getId(), "ID da notificação");
        if (notificacaoRepository.existsById(notificacao.getId())) {
            validar(notificacao);
            return relationshipMapper.notificacao(notificacaoRepository.save(notificacao));
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Notificação não encontrada");
    }

    public void deletarNotificacao(String id) {
        ReferenceIds.require(id, "ID da notificação");
        if (!notificacaoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Notificação não encontrada");
        }
        notificacaoRepository.deleteById(id);
    }

    private void validar(Notificacao notificacao) {
        relationshipMapper.requireUsuario(notificacao.getUsuarioID(), "Usuário");
        if (notificacao.getMensagem() == null || notificacao.getMensagem().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mensagem é obrigatória");
        }
    }
}
