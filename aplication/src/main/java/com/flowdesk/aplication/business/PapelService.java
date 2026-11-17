package com.flowdesk.aplication.business;

import com.flowdesk.aplication.models.Papel;
import com.flowdesk.aplication.repositories.PapelRepository;
import com.flowdesk.aplication.repositories.UsuarioRepository;
import com.flowdesk.aplication.common.ReferenceIds;
import com.flowdesk.aplication.common.RelationshipMapper;
import com.flowdesk.aplication.dto.UsuarioResumo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PapelService {

    private final PapelRepository papelRepository;
    private final UsuarioRepository usuarioRepository;
    private final RelationshipMapper relationshipMapper;

    public PapelService(PapelRepository papelRepository, UsuarioRepository usuarioRepository,
                        RelationshipMapper relationshipMapper) {
        this.papelRepository = papelRepository;
        this.usuarioRepository = usuarioRepository;
        this.relationshipMapper = relationshipMapper;
    }

    public Papel salvaPapel(Papel papel) {
        if (papel.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não informe ID ao criar papel");
        }
        validar(papel);
        return papelRepository.save(papel);
    }

    public List<Papel> buscaPapeis() {
        return papelRepository.findAll();
    }

    public Papel buscaPapelID(String id) {
        ReferenceIds.require(id, "ID do papel");
        return papelRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Papel não encontrado"));
    }

    public List<UsuarioResumo> buscaUsuarios(String papelID) {
        ReferenceIds.require(papelID, "ID do papel");
        if (!papelRepository.existsById(papelID)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Papel não encontrado");
        }
        return usuarioRepository.findByPapelId(papelID).stream()
                .map(relationshipMapper::usuario).toList();
    }

    public Papel atualizaPapel(Papel papel) {
        ReferenceIds.require(papel.getId(), "ID do papel");
        if (papelRepository.existsById(papel.getId())) {
            validar(papel);
            return papelRepository.save(papel);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Papel não encontrado");
    }

    public void deletarPapel(String id) {
        ReferenceIds.require(id, "ID do papel");
        if (!papelRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Papel não encontrado");
        }
        if (usuarioRepository.existsByPapelId(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Não é possível excluir papel atribuído a usuários");
        }
        papelRepository.deleteById(id);
    }

    private void validar(Papel papel) {
        if (papel.getNome() == null || papel.getNome().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome do papel é obrigatório");
        }
    }
}
