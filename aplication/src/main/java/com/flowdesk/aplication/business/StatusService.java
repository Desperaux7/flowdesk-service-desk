package com.flowdesk.aplication.business;

import com.flowdesk.aplication.models.Status;
import com.flowdesk.aplication.repositories.StatusRepository;
import com.flowdesk.aplication.repositories.TicketRepository;
import com.flowdesk.aplication.common.ReferenceIds;
import com.flowdesk.aplication.common.RelationshipMapper;
import com.flowdesk.aplication.dto.TicketResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class StatusService {

    private final StatusRepository statusRepository;
    private final TicketRepository ticketRepository;
    private final RelationshipMapper relationshipMapper;

    public StatusService(StatusRepository statusRepository, TicketRepository ticketRepository,
                         RelationshipMapper relationshipMapper) {
        this.statusRepository = statusRepository;
        this.ticketRepository = ticketRepository;
        this.relationshipMapper = relationshipMapper;
    }

    public Status salvaStatus(Status status) {
        if (status.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não informe ID ao criar status");
        }
        validar(status);
        return statusRepository.save(status);
    }

    public List<Status> buscaStatus() {
        return statusRepository.findAll();
    }

    public Status buscaStatusID(String id) {
        ReferenceIds.require(id, "ID do status");
        return statusRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Status não encontrado"));
    }

    public List<TicketResponse> buscaTickets(String statusID) {
        ReferenceIds.require(statusID, "ID do status");
        if (!statusRepository.existsById(statusID)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Status não encontrado");
        }
        return ticketRepository.findByStatusID(statusID).stream()
                .map(relationshipMapper::ticket).toList();
    }

    public Status atualizaStatus(Status status) {
        ReferenceIds.require(status.getId(), "ID do status");
        if (statusRepository.existsById(status.getId())) {
            validar(status);
            return statusRepository.save(status);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Status não encontrado");
    }

    public void deletarStatus(String id) {
        ReferenceIds.require(id, "ID do status");
        if (!statusRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Status não encontrado");
        }
        if (ticketRepository.existsByStatusID(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Não é possível excluir status utilizado por tickets");
        }
        statusRepository.deleteById(id);
    }

    private void validar(Status status) {
        if (status.getNome() == null || status.getNome().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome do status é obrigatório");
        }
    }
}
