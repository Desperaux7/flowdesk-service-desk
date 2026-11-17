package com.flowdesk.aplication.business;
import com.flowdesk.aplication.common.ReferenceIds;
import com.flowdesk.aplication.common.RelationshipMapper;
import com.flowdesk.aplication.dto.TicketResponse;
import com.flowdesk.aplication.models.Ticket;
import com.flowdesk.aplication.repositories.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final UsuarioRepository usuarioRepository;
    private final SetorRepository setorRepository;
    private final CategoriaRepository categoriaRepository;
    private final StatusRepository statusRepository;
    private final ComentarioRepository comentarioRepository;
    private final HistoricoRepository historicoRepository;
    private final AnexoRepository anexoRepository;
    private final RelationshipMapper relationshipMapper;

    public TicketService(TicketRepository ticketRepository, UsuarioRepository usuarioRepository,
                         SetorRepository setorRepository, CategoriaRepository categoriaRepository,
                         StatusRepository statusRepository, ComentarioRepository comentarioRepository,
                         HistoricoRepository historicoRepository, AnexoRepository anexoRepository,
                         RelationshipMapper relationshipMapper) {
        this.ticketRepository = ticketRepository;
        this.usuarioRepository = usuarioRepository;
        this.setorRepository = setorRepository;
        this.categoriaRepository = categoriaRepository;
        this.statusRepository = statusRepository;
        this.comentarioRepository = comentarioRepository;
        this.historicoRepository = historicoRepository;
        this.anexoRepository = anexoRepository;
        this.relationshipMapper = relationshipMapper;
    }

    //create novo ticket :>
    public TicketResponse salvaTicket(Ticket ticket) {
        if (ticket.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não informe ID ao criar ticket");
        }
        validarReferencias(ticket);
        if (ticket.getTitulo() == null || ticket.getTitulo().isBlank()
                || ticket.getDescricao() == null || ticket.getDescricao().isBlank()
                || ticket.getPrioridade() == null || ticket.getPrioridade().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Título, descrição e prioridade são obrigatórios");
        }
        ticket.setDataCriacao(LocalDateTime.now());
        ticket.setDataConclusao(null);
        return relationshipMapper.ticket(ticketRepository.save(ticket));
    }

    //Procura todos os tickets
    public List<TicketResponse> buscaTickets() {
        return ticketRepository.findAll().stream().map(relationshipMapper::ticket).toList();
    }
    // procura todos os tickets de um solicitante específico
    public List<TicketResponse> buscaPorSolicitante(String solicitanteID) {
        validarFiltro(solicitanteID, "Solicitante", usuarioRepository::existsById);
        return ticketRepository.findBySolicitanteID(solicitanteID).stream()
                .map(relationshipMapper::ticket).toList();
    }
    // procura todos os tickets de um responsável específico
    public List<TicketResponse> buscaPorResponsavel(String responsavelID) {
        validarFiltro(responsavelID, "Responsável", usuarioRepository::existsById);
        return ticketRepository.findByResponsavelID(responsavelID).stream()
                .map(relationshipMapper::ticket).toList();
    }
    // procura todos os tickets de uma categoria específica
    public List<TicketResponse> buscaPorCategoria(String categoriaID) {
        validarFiltro(categoriaID, "Categoria", categoriaRepository::existsById);
        return ticketRepository.findByCategoriaID(categoriaID).stream()
                .map(relationshipMapper::ticket).toList();
    }
    // procura todos os tickets de um setor de destino específico
    public List<TicketResponse> buscaPorSetorDestino(String setorDestinoID) {
        validarFiltro(setorDestinoID, "Setor", setorRepository::existsById);
        return ticketRepository.findBySetorDestinoID(setorDestinoID).stream()
                .map(relationshipMapper::ticket).toList();
    }
    // procura todos os tickets de um status específico
    public List<TicketResponse> buscaPorStatus(String statusID) {
        validarFiltro(statusID, "Status", statusRepository::existsById);
        return ticketRepository.findByStatusID(statusID).stream()
                .map(relationshipMapper::ticket).toList();
    }

    //Procura um ticket pelo id :>
    public TicketResponse buscaTicketID(String id) {
        ReferenceIds.require(id, "ID do ticket");
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Ticket não encontrado"));
        return relationshipMapper.ticket(ticket);
    }

    //Update em um ticket :>, caso não tenha esse ticket ele cria um novo
    public TicketResponse atualizaTicket(Ticket ticket) {
        if (ticket.getId() == null || ticket.getId().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "ID do ticket é obrigatório para atualização");
        }
        ReferenceIds.require(ticket.getId(), "ID do ticket");
        Ticket existente = ticketRepository.findById(ticket.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket não encontrado"));
        validarReferencias(ticket);
        if (ticket.getTitulo() == null || ticket.getTitulo().isBlank()
                || ticket.getDescricao() == null || ticket.getDescricao().isBlank()
                || ticket.getPrioridade() == null || ticket.getPrioridade().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Título, descrição e prioridade são obrigatórios");
        }
        if (ticket.getDataCriacao() == null) {
            ticket.setDataCriacao(existente.getDataCriacao());
        }
        return relationshipMapper.ticket(ticketRepository.save(ticket));
    }

    //Deleta um ticket
    public void deletarTicket(String id) {
        ReferenceIds.require(id, "ID do ticket");
        if (!ticketRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket não encontrado");
        }
        if (comentarioRepository.existsByTicketID(id)
                || historicoRepository.existsByTicketID(id)
                || anexoRepository.existsByTicketID(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Não é possível excluir ticket com comentários, histórico ou anexos");
        }
        ticketRepository.deleteById(id);
    }

    private void validarReferencias(Ticket ticket) {
        validarFiltro(ReferenceIds.require(ticket.getSolicitanteID(), "Solicitante"),
                "Solicitante", usuarioRepository::existsById);
        validarOpcional(ticket.getResponsavelID(), "Responsável", usuarioRepository::existsById);
        validarFiltro(ReferenceIds.require(ticket.getSetorDestinoID(), "Setor"),
                "Setor", setorRepository::existsById);
        validarFiltro(ReferenceIds.require(ticket.getCategoriaID(), "Categoria"),
                "Categoria", categoriaRepository::existsById);
        validarFiltro(ReferenceIds.require(ticket.getStatusID(), "Status"),
                "Status", statusRepository::existsById);
    }

    private void validarOpcional(String id, String entidade,
                                 java.util.function.Predicate<String> exists) {
        String validId = ReferenceIds.optional(id, entidade);
        if (validId != null) {
            validarFiltro(validId, entidade, exists);
        }
    }

    private void validarFiltro(String id, String entidade,
                              java.util.function.Predicate<String> exists) {
        ReferenceIds.require(id, entidade);
        if (!exists.test(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, entidade + " não encontrado");
        }
    }
}
