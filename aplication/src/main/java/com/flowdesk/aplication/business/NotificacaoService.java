package com.flowdesk.aplication.business;

import com.flowdesk.aplication.models.Notificacao;
import com.flowdesk.aplication.repositories.NotificacaoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class NotificacaoService {

    private final NotificacaoRepository notificacaoRepository;

    public NotificacaoService(NotificacaoRepository notificacaoRepository) {
        this.notificacaoRepository = notificacaoRepository;
    }

    public Notificacao salvaNotificacao(Notificacao notificacao) {
        return notificacaoRepository.save(notificacao);
    }

    public List<Notificacao> buscaNotificacoes() {
        return notificacaoRepository.findAll();
    }

    public Notificacao buscaNotificacaoID(String id) {
        return notificacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificação não encontrada"));
    }

    public Notificacao atualizaNotificacao(Notificacao notificacao) {
        Optional<Notificacao> notificacaoExiste = notificacaoRepository.findById(notificacao.getId());
        if (notificacaoExiste.isPresent()) {
            return notificacaoRepository.save(notificacao);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Notificação não encontrada");
    }

    public void deletarNotificacao(String id) {
        notificacaoRepository.deleteById(id);
    }
}
