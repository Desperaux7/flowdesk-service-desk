package com.flowdesk.aplication.business;

import com.flowdesk.aplication.models.Historico;
import com.flowdesk.aplication.repositories.HistoricoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class HistoricoService {

    private final HistoricoRepository historicoRepository;

    public HistoricoService(HistoricoRepository historicoRepository) {
        this.historicoRepository = historicoRepository;
    }

    public Historico salvaHistorico(Historico historico) {
        return historicoRepository.save(historico);
    }

    public List<Historico> buscaHistoricos() {
        return historicoRepository.findAll();
    }

    public Historico buscaHistoricoID(String id) {
        return historicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Histórico não encontrado"));
    }

    public Historico atualizaHistorico(Historico historico) {
        Optional<Historico> historicoExiste = historicoRepository.findById(historico.getId());
        if (historicoExiste.isPresent()) {
            return historicoRepository.save(historico);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Histórico não encontrado");
    }

    public void deletarHistorico(String id) {
        historicoRepository.deleteById(id);
    }
}
