package com.flowdesk.aplication.business;

import com.flowdesk.aplication.common.ReferenceIds;
import com.flowdesk.aplication.common.RelationshipMapper;
import com.flowdesk.aplication.dto.ComentarioResponse;
import com.flowdesk.aplication.models.Comentario;
import com.flowdesk.aplication.repositories.ComentarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final RelationshipMapper relationshipMapper;

    public ComentarioService(ComentarioRepository comentarioRepository, RelationshipMapper relationshipMapper) {
        this.comentarioRepository = comentarioRepository;
        this.relationshipMapper = relationshipMapper;
    }

    public ComentarioResponse salvaComentario(Comentario comentario) {
        if (comentario.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não informe ID ao criar comentário");
        }
        validar(comentario);
        comentario.setDataPublicacao(comentario.getDataPublicacao() == null
                ? java.time.LocalDateTime.now() : comentario.getDataPublicacao());
        return relationshipMapper.comentario(comentarioRepository.save(comentario));
    }

    public List<ComentarioResponse> buscaComentarios() {
        return comentarioRepository.findAll().stream().map(relationshipMapper::comentario).toList();
    }

    public List<ComentarioResponse> buscaPorTicket(String ticketID) {
        relationshipMapper.requireTicket(ticketID, "Ticket");
        return comentarioRepository.findByTicketID(ticketID).stream()
                .map(relationshipMapper::comentario).toList();
    }

    public List<ComentarioResponse> buscaPorAutor(String autorID) {
        relationshipMapper.requireUsuario(autorID, "Autor");
        return comentarioRepository.findByAutorID(autorID).stream()
                .map(relationshipMapper::comentario).toList();
    }

    public ComentarioResponse buscaComentarioID(String id) {
        ReferenceIds.require(id, "ID do comentário");
        return comentarioRepository.findById(id)
                .map(relationshipMapper::comentario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comentário não encontrado"));
    }

    public ComentarioResponse atualizaComentario(Comentario comentario) {
        ReferenceIds.require(comentario.getId(), "ID do comentário");
        if (comentarioRepository.existsById(comentario.getId())) {
            validar(comentario);
            return relationshipMapper.comentario(comentarioRepository.save(comentario));
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comentário não encontrado");
    }

    public void deletarComentario(String id) {
        ReferenceIds.require(id, "ID do comentário");
        if (!comentarioRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comentário não encontrado");
        }
        comentarioRepository.deleteById(id);
    }

    private void validar(Comentario comentario) {
        relationshipMapper.requireTicket(comentario.getTicketID(), "Ticket");
        relationshipMapper.requireUsuario(comentario.getAutorID(), "Autor");
        if (comentario.getMensagem() == null || comentario.getMensagem().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mensagem é obrigatória");
        }
    }
}
