package com.flowdesk.aplication.business;

import com.flowdesk.aplication.models.Setor;
import com.flowdesk.aplication.common.ReferenceIds;
import com.flowdesk.aplication.common.RelationshipMapper;
import com.flowdesk.aplication.dto.CategoriaResponse;
import com.flowdesk.aplication.dto.TicketResponse;
import com.flowdesk.aplication.dto.UsuarioResumo;
import com.flowdesk.aplication.repositories.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class SetorService {

    private final SetorRepository setorRepository;
    private final UsuarioRepository usuarioRepository;
    private final TicketRepository ticketRepository;
    private final CategoriaRepository categoriaRepository;
    private final RelationshipMapper relationshipMapper;

    public SetorService(SetorRepository setorRepository, UsuarioRepository usuarioRepository,
                        TicketRepository ticketRepository, CategoriaRepository categoriaRepository,
                        RelationshipMapper relationshipMapper) {
        this.setorRepository = setorRepository;
        this.usuarioRepository = usuarioRepository;
        this.ticketRepository = ticketRepository;
        this.categoriaRepository = categoriaRepository;
        this.relationshipMapper = relationshipMapper;
    }

    public Setor salvaSetor(Setor setor) {
        if (setor.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não informe ID ao criar setor");
        }
        validar(setor);
        return setorRepository.save(setor);
    }

    public List<Setor> buscaSetores() {
        return setorRepository.findAll();
    }

    public Setor buscaSetorID(String id) {
        validarId(id);
        return setorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Setor não encontrado"));
    }

    public List<UsuarioResumo> buscaUsuarios(String setorID) {
        validarSetorExiste(setorID);
        return usuarioRepository.findBySetorId(setorID).stream()
                .map(relationshipMapper::usuario).toList();
    }

    public List<TicketResponse> buscaTickets(String setorID) {
        validarSetorExiste(setorID);
        return ticketRepository.findBySetorDestinoID(setorID).stream()
                .map(relationshipMapper::ticket).toList();
    }

    public List<CategoriaResponse> buscaCategorias(String setorID) {
        validarSetorExiste(setorID);
        return categoriaRepository.findBySetorRelacionadoID(setorID).stream()
                .map(relationshipMapper::categoria).toList();
    }

    public Setor atualizaSetor(Setor setor) {
        validarId(setor.getId());
        if (setorRepository.existsById(setor.getId())) {
            validar(setor);
            return setorRepository.save(setor);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Setor não encontrado");
    }

    public void deletarSetor(String id) {
        validarId(id);
        if (!setorRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Setor não encontrado");
        }
        if (usuarioRepository.existsBySetorId(id)
                || ticketRepository.existsBySetorDestinoID(id)
                || categoriaRepository.existsBySetorRelacionadoID(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Não é possível excluir setor com usuários, tickets ou categorias associados");
        }
        setorRepository.deleteById(id);
    }

    private void validar(Setor setor) {
        if (setor.getNome() == null || setor.getNome().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome do setor é obrigatório");
        }
    }

    private void validarId(String id) {
        ReferenceIds.require(id, "ID do setor");
    }

    private void validarSetorExiste(String id) {
        validarId(id);
        if (!setorRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Setor não encontrado");
        }
    }
}
