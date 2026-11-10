package com.flowdesk.aplication.business;

import com.flowdesk.aplication.models.Comentario;
import com.flowdesk.aplication.repositories.ComentarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;

    public ComentarioService(ComentarioRepository comentarioRepository) {
        this.comentarioRepository = comentarioRepository;
    }

    public Comentario salvaComentario(Comentario comentario) {
        return comentarioRepository.save(comentario);
    }

    public List<Comentario> buscaComentarios() {
        return comentarioRepository.findAll();
    }

    public Comentario buscaComentarioID(String id) {
        return comentarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comentário não encontrado"));
    }

    public Comentario atualizaComentario(Comentario comentario) {
        Optional<Comentario> comentarioExiste = comentarioRepository.findById(comentario.getId());
        if (comentarioExiste.isPresent()) {
            return comentarioRepository.save(comentario);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comentário não encontrado");
    }

    public void deletarComentario(String id) {
        comentarioRepository.deleteById(id);
    }
}
