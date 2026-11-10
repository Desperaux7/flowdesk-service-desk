package com.flowdesk.aplication.business;

import com.flowdesk.aplication.models.Status;
import com.flowdesk.aplication.repositories.StatusRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class StatusService {

    private final StatusRepository statusRepository;

    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public Status salvaStatus(Status status) {
        return statusRepository.save(status);
    }

    public List<Status> buscaStatus() {
        return statusRepository.findAll();
    }

    public Status buscaStatusID(String id) {
        return statusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Status não encontrado"));
    }

    public Status atualizaStatus(Status status) {
        Optional<Status> statusExiste = statusRepository.findById(status.getId());
        if (statusExiste.isPresent()) {
            return statusRepository.save(status);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Status não encontrado");
    }

    public void deletarStatus(String id) {
        statusRepository.deleteById(id);
    }
}
