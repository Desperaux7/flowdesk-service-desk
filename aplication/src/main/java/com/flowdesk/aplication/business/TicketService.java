package com.flowdesk.aplication.business;
import com.flowdesk.aplication.dto.TicketResponse;
import com.flowdesk.aplication.dto.UsuarioResumo;
import com.flowdesk.aplication.models.Ticket;
import com.flowdesk.aplication.repositories.TicketRepository;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.flowdesk.aplication.repositories.UsuarioRepository;
import com.flowdesk.aplication.repositories.SetorRepository;
import com.flowdesk.aplication.repositories.CategoriaRepository;
import com.flowdesk.aplication.repositories.StatusRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final UsuarioRepository usuarioRepository;
    private final SetorRepository setorRepository;
    private final CategoriaRepository categoriaRepository;
    private final StatusRepository statusRepository;

    public TicketService(TicketRepository ticketRepository, UsuarioRepository usuarioRepository, SetorRepository setorRepository, CategoriaRepository categoriaRepository, StatusRepository statusRepository) {
        this.ticketRepository = ticketRepository;
        this.usuarioRepository = usuarioRepository;
        this.setorRepository = setorRepository;
        this.categoriaRepository = categoriaRepository;
        this.statusRepository = statusRepository;
    }

    //create novo ticket :>
    public TicketResponse salvaTicket(Ticket ticket) {
        validarReferencias(ticket);
        return detalhar(ticketRepository.save(ticket));
    }

    //Procura todos os tickets
    public List<TicketResponse> buscaTickets() {
        return ticketRepository.findAll().stream().map(this::detalhar).toList();
    }

    public List<TicketResponse> buscaPorSolicitante(String solicitanteID) {
        return ticketRepository.findBySolicitanteID(solicitanteID).stream()
                .map(this::detalhar).toList();
    }

    public List<TicketResponse> buscaPorResponsavel(String responsavelID) {
        return ticketRepository.findByResponsavelID(responsavelID).stream()
                .map(this::detalhar).toList();
    }

    public List<TicketResponse> buscaPorCategoria(String categoriaID) {
        return ticketRepository.findByCategoriaID(categoriaID).stream()
                .map(this::detalhar).toList();
    }

    public List<TicketResponse> buscaPorSetorDestino(String setorDestinoID) {
        return ticketRepository.findBySetorDestinoID(setorDestinoID).stream()
                .map(this::detalhar).toList();
    }

    public List<TicketResponse> buscaPorStatus(String statusID) {
        return ticketRepository.findByStatusID(statusID).stream()
                .map(this::detalhar).toList();
    }

    //Procura um ticket pelo id :>
    public TicketResponse buscaTicketID(String id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Ticket não encontrado"));
        return detalhar(ticket);
    }

    //Update em um ticket :>, caso não tenha esse ticket ele cria um novo
    public TicketResponse atualizaTicket(Ticket ticket) {
        if (ticket.getId() == null || ticket.getId().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "ID do ticket é obrigatório para atualização");
        }
        Optional<Ticket> ticketExiste = ticketRepository.findById(ticket.getId());
        if (ticketExiste.isPresent()) {
            validarReferencias(ticket);
            return detalhar(ticketRepository.save(ticket));
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket não encontrado");
    }

    //Deleta um ticket
    public void deletarTicket(String id) {
        ticketRepository.deleteById(id);
    }

    private void validarReferencias(Ticket ticket) {
        validarReferencia(ticket.getSolicitanteID(), "Solicitante", true,
                usuarioRepository::existsById);
        validarReferencia(ticket.getResponsavelID(), "Responsável", false,
                usuarioRepository::existsById);
        validarReferencia(ticket.getSetorDestinoID(), "Setor", false,
                setorRepository::existsById);
        validarReferencia(ticket.getCategoriaID(), "Categoria", false,
                categoriaRepository::existsById);
        validarReferencia(ticket.getStatusID(), "Status", false,
                statusRepository::existsById);
    }

    private void validarReferencia(String id, String entidade, boolean obrigatoria,
                                   Predicate<String> existePorId) {
        if (id == null) {
            if (obrigatoria) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, entidade + " é obrigatório");
            }
            return;
        }

        if (!ObjectId.isValid(id)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "ID de " + entidade + " inválido");
        }

        if (!existePorId.test(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, entidade + " não encontrado");
        }
    }

    private TicketResponse detalhar(Ticket ticket) {
        var solicitante = ticket.getSolicitanteID() == null ? null
                : usuarioRepository.findById(ticket.getSolicitanteID())
                .map(usuario -> new UsuarioResumo(
                        usuario.getId(), usuario.getNome(), usuario.getEmail()))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Solicitante referenciado pelo ticket não encontrado"));
        var responsavel = ticket.getResponsavelID() == null ? null
                : usuarioRepository.findById(ticket.getResponsavelID())
                .map(usuario -> new UsuarioResumo(
                        usuario.getId(), usuario.getNome(), usuario.getEmail()))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Responsável referenciado pelo ticket não encontrado"));
        var categoria = ticket.getCategoriaID() == null ? null
                : categoriaRepository.findById(ticket.getCategoriaID())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Categoria referenciada pelo ticket não encontrada"));
        var setorDestino = ticket.getSetorDestinoID() == null ? null
                : setorRepository.findById(ticket.getSetorDestinoID())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Setor referenciado pelo ticket não encontrado"));
        var status = ticket.getStatusID() == null ? null
                : statusRepository.findById(ticket.getStatusID())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Status referenciado pelo ticket não encontrado"));

        return new TicketResponse(
                ticket.getId(),
                ticket.getTitulo(),
                ticket.getDescricao(),
                ticket.getPrioridade(),
                ticket.getDataCriacao(),
                ticket.getDataConclusao(),
                ticket.getSolicitanteID(),
                ticket.getResponsavelID(),
                ticket.getCategoriaID(),
                ticket.getSetorDestinoID(),
                ticket.getStatusID(),
                solicitante,
                responsavel,
                categoria,
                setorDestino,
                status
        );
    }
}
