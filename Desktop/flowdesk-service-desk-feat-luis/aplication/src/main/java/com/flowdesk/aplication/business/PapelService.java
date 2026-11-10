package com.flowdesk.aplication.business;

import com.flowdesk.aplication.models.Papel;
import com.flowdesk.aplication.repositories.PapelRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class PapelService {

    private final PapelRepository papelRepository;

    public PapelService(PapelRepository papelRepository) {
        this.papelRepository = papelRepository;
    }

    public Papel salvaPapel(Papel papel) {
        return papelRepository.save(papel);
    }

    public List<Papel> buscaPapeis() {
        return papelRepository.findAll();
    }

    public Papel buscaPapelID(String id) {
        return papelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Papel não encontrado"));
    }

    public Papel atualizaPapel(Papel papel) {
        Optional<Papel> papelExiste = papelRepository.findById(papel.getId());
        if (papelExiste.isPresent()) {
            return papelRepository.save(papel);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Papel não encontrado");
    }

    public void deletarPapel(String id) {
        papelRepository.deleteById(id);
    }
}
