package com.flowdesk.aplication.business;

import com.flowdesk.aplication.models.Anexo;
import com.flowdesk.aplication.repositories.AnexoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class AnexoService {

    private final AnexoRepository anexoRepository;

    public AnexoService(AnexoRepository anexoRepository) {
        this.anexoRepository = anexoRepository;
    }

    public Anexo salvaAnexo(Anexo anexo) {
        return anexoRepository.save(anexo);
    }

    public List<Anexo> buscaAnexos() {
        return anexoRepository.findAll();
    }

    public Anexo buscaAnexoID(String id) {
        return anexoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Anexo não encontrado"));
    }

    public Anexo atualizaAnexo(Anexo anexo) {
        Optional<Anexo> anexoExiste = anexoRepository.findById(anexo.getId());
        if (anexoExiste.isPresent()) {
            return anexoRepository.save(anexo);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Anexo não encontrado");
    }

    public void deletarAnexo(String id) {
        anexoRepository.deleteById(id);
    }
}
