package com.flowdesk.aplication.business;

import com.flowdesk.aplication.common.ReferenceIds;
import com.flowdesk.aplication.common.RelationshipMapper;
import com.flowdesk.aplication.dto.HistoricoResponse;
import com.flowdesk.aplication.models.Historico;
import com.flowdesk.aplication.repositories.HistoricoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
public class HistoricoService {

    private final HistoricoRepository historicoRepository;
    private final RelationshipMapper relationshipMapper;

    public HistoricoService(HistoricoRepository historicoRepository, RelationshipMapper relationshipMapper) {
        this.historicoRepository = historicoRepository;
        this.relationshipMapper = relationshipMapper;
    }

    public HistoricoResponse salvaHistorico(Historico historico) {
        if (historico.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não informe ID ao criar histórico");
        }
        validar(historico);
        historico.setDataHora(historico.getDataHora() == null
                ? java.time.LocalDateTime.now() : historico.getDataHora());
        return relationshipMapper.historico(historicoRepository.save(historico));
    }

    public List<HistoricoResponse> buscaHistoricos() {
        return historicoRepository.findAll().stream().map(relationshipMapper::historico).toList();
    }

    public List<HistoricoResponse> buscaPorTicket(String ticketID) {
        relationshipMapper.requireTicket(ticketID, "Ticket");
        return historicoRepository.findByTicketID(ticketID).stream()
                .map(relationshipMapper::historico).toList();
    }

    public List<HistoricoResponse> buscaPorAutorAcao(String autorAcaoID) {
        relationshipMapper.requireUsuario(autorAcaoID, "Autor da ação");
        return historicoRepository.findByAutorAcaoID(autorAcaoID).stream()
                .map(relationshipMapper::historico).toList();
    }

    public HistoricoResponse buscaHistoricoID(String id) {
        ReferenceIds.require(id, "ID do histórico");
        return historicoRepository.findById(id)
                .map(relationshipMapper::historico)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Histórico não encontrado"));
    }

    public HistoricoResponse atualizaHistorico(Historico historico) {
        ReferenceIds.require(historico.getId(), "ID do histórico");
        if (historicoRepository.existsById(historico.getId())) {
            validar(historico);
            return relationshipMapper.historico(historicoRepository.save(historico));
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Histórico não encontrado");
    }

    public void deletarHistorico(String id) {
        ReferenceIds.require(id, "ID do histórico");
        if (!historicoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Histórico não encontrado");
        }
        historicoRepository.deleteById(id);
    }

    private void validar(Historico historico) {
        relationshipMapper.requireTicket(historico.getTicketID(), "Ticket");
        relationshipMapper.requireUsuario(historico.getAutorAcaoID(), "Autor da ação");
        if (historico.getAcao() == null || historico.getAcao().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ação é obrigatória");
        }
    }
}
