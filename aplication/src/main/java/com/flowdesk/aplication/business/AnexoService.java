package com.flowdesk.aplication.business;

import com.flowdesk.aplication.common.ReferenceIds;
import com.flowdesk.aplication.common.RelationshipMapper;
import com.flowdesk.aplication.dto.AnexoResponse;
import com.flowdesk.aplication.models.Anexo;
import com.flowdesk.aplication.repositories.AnexoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
public class AnexoService {

    private final AnexoRepository anexoRepository;
    private final RelationshipMapper relationshipMapper;

    public AnexoService(AnexoRepository anexoRepository, RelationshipMapper relationshipMapper) {
        this.anexoRepository = anexoRepository;
        this.relationshipMapper = relationshipMapper;
    }

    public AnexoResponse salvaAnexo(Anexo anexo) {
        if (anexo.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não informe ID ao criar anexo");
        }
        validar(anexo);
        return relationshipMapper.anexo(anexoRepository.save(anexo));
    }

    public List<AnexoResponse> buscaAnexos() {
        return anexoRepository.findAll().stream().map(relationshipMapper::anexo).toList();
    }

    public List<AnexoResponse> buscaPorTicket(String ticketID) {
        relationshipMapper.requireTicket(ticketID, "Ticket");
        return anexoRepository.findByTicketID(ticketID).stream()
                .map(relationshipMapper::anexo).toList();
    }

    public AnexoResponse buscaAnexoID(String id) {
        ReferenceIds.require(id, "ID do anexo");
        return anexoRepository.findById(id)
                .map(relationshipMapper::anexo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anexo não encontrado"));
    }

    public AnexoResponse atualizaAnexo(Anexo anexo) {
        ReferenceIds.require(anexo.getId(), "ID do anexo");
        if (anexoRepository.existsById(anexo.getId())) {
            validar(anexo);
            return relationshipMapper.anexo(anexoRepository.save(anexo));
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Anexo não encontrado");
    }

    public void deletarAnexo(String id) {
        ReferenceIds.require(id, "ID do anexo");
        if (!anexoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Anexo não encontrado");
        }
        anexoRepository.deleteById(id);
    }

    private void validar(Anexo anexo) {
        relationshipMapper.requireTicket(anexo.getTicketID(), "Ticket");
        if (anexo.getNome_arquivo() == null || anexo.getNome_arquivo().isBlank()
                || anexo.getUrl_caminho() == null || anexo.getUrl_caminho().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Nome do arquivo e URL/caminho são obrigatórios");
        }
        if (anexo.getTamanho() != null && anexo.getTamanho() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tamanho não pode ser negativo");
        }
    }
}
