package com.flowdesk.aplication.business;

import com.flowdesk.aplication.models.Setor;
import com.flowdesk.aplication.repositories.SetorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class SetorService {

    private final SetorRepository setorRepository;

    public SetorService(SetorRepository setorRepository) {
        this.setorRepository = setorRepository;
    }

    public Setor salvaSetor(Setor setor) {
        return setorRepository.save(setor);
    }

    public List<Setor> buscaSetores() {
        return setorRepository.findAll();
    }

    public Setor buscaSetorID(String id) {
        return setorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Setor não encontrado"));
    }

    public Setor atualizaSetor(Setor setor) {
        Optional<Setor> setorExiste = setorRepository.findById(setor.getId());
        if (setorExiste.isPresent()) {
            return setorRepository.save(setor);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Setor não encontrado");
    }

    public void deletarSetor(String id) {
        setorRepository.deleteById(id);
    }
}
