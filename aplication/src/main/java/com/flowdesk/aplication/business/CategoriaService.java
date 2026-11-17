package com.flowdesk.aplication.business;

import com.flowdesk.aplication.common.ReferenceIds;
import com.flowdesk.aplication.common.RelationshipMapper;
import com.flowdesk.aplication.dto.CategoriaResponse;
import com.flowdesk.aplication.dto.TicketResponse;
import com.flowdesk.aplication.models.Categoria;
import com.flowdesk.aplication.repositories.CategoriaRepository;
import com.flowdesk.aplication.repositories.SetorRepository;
import com.flowdesk.aplication.repositories.TicketRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final SetorRepository setorRepository;
    private final TicketRepository ticketRepository;
    private final RelationshipMapper relationshipMapper;

    public CategoriaService(CategoriaRepository categoriaRepository, SetorRepository setorRepository,
                            TicketRepository ticketRepository, RelationshipMapper relationshipMapper) {
        this.categoriaRepository = categoriaRepository;
        this.setorRepository = setorRepository;
        this.ticketRepository = ticketRepository;
        this.relationshipMapper = relationshipMapper;
    }

    public CategoriaResponse salvaCategoria(Categoria categoria) {
        if (categoria.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não informe ID ao criar categoria");
        }
        validar(categoria);
        return relationshipMapper.categoria(categoriaRepository.save(categoria));
    }

    public List<CategoriaResponse> buscaCategorias() {
        return categoriaRepository.findAll().stream().map(relationshipMapper::categoria).toList();
    }

    public CategoriaResponse buscaCategoriaID(String id) {
        ReferenceIds.require(id, "ID da categoria");
        return categoriaRepository.findById(id)
                .map(relationshipMapper::categoria)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada"));
    }

    public List<TicketResponse> buscaTickets(String categoriaID) {
        ReferenceIds.require(categoriaID, "ID da categoria");
        if (!categoriaRepository.existsById(categoriaID)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada");
        }
        return ticketRepository.findByCategoriaID(categoriaID).stream()
                .map(relationshipMapper::ticket).toList();
    }

    public CategoriaResponse atualizaCategoria(Categoria categoria) {
        ReferenceIds.require(categoria.getId(), "ID da categoria");
        if (categoriaRepository.existsById(categoria.getId())) {
            validar(categoria);
            return relationshipMapper.categoria(categoriaRepository.save(categoria));
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada");
    }

    public void deletarCategoria(String id) {
        ReferenceIds.require(id, "ID da categoria");
        if (!categoriaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada");
        }
        if (ticketRepository.existsByCategoriaID(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Não é possível excluir categoria utilizada por tickets");
        }
        categoriaRepository.deleteById(id);
    }

    private void validar(Categoria categoria) {
        if (categoria.getNome() == null || categoria.getNome().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome da categoria é obrigatório");
        }
        String setorId = ReferenceIds.require(categoria.getSetorRelacionadoID(), "Setor da categoria");
        if (!setorRepository.existsById(setorId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Setor da categoria não encontrado");
        }
    }
}
