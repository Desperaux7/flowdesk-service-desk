package com.flowdesk.aplication.common;

import com.flowdesk.aplication.dto.*;
import com.flowdesk.aplication.models.*;
import com.flowdesk.aplication.repositories.*;
import java.util.function.Function;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class RelationshipMapper {
    private final UsuarioRepository usuarioRepository;
    private final SetorRepository setorRepository;
    private final PapelRepository papelRepository;
    private final CategoriaRepository categoriaRepository;
    private final StatusRepository statusRepository;
    private final TicketRepository ticketRepository;

    public RelationshipMapper(UsuarioRepository usuarioRepository,
                              SetorRepository setorRepository,
                              PapelRepository papelRepository,
                              CategoriaRepository categoriaRepository,
                              StatusRepository statusRepository,
                              TicketRepository ticketRepository) {
        this.usuarioRepository = usuarioRepository;
        this.setorRepository = setorRepository;
        this.papelRepository = papelRepository;
        this.categoriaRepository = categoriaRepository;
        this.statusRepository = statusRepository;
        this.ticketRepository = ticketRepository;
    }

    public UsuarioResumo usuario(Usuario usuario) {
        Setor setor = resolveOptional(usuario.getSetorId(), "Setor",
                setorRepository::findById);
        Papel papel = resolveOptional(usuario.getPapelId(), "Papel",
                papelRepository::findById);
        return new UsuarioResumo(usuario.getId(), usuario.getNome(), usuario.getEmail(),
                usuario.getSetorId(), usuario.getPapelId(), setor, papel);
    }

    public TicketResponse ticket(Ticket ticket) {
        UsuarioResumo solicitante = resolveUser(ticket.getSolicitanteID(), "Solicitante");
        UsuarioResumo responsavel = resolveUser(ticket.getResponsavelID(), "Responsável");
        Categoria categoriaDocumento = resolveOptional(ticket.getCategoriaID(), "Categoria",
                categoriaRepository::findById);
        Setor setor = resolveOptional(ticket.getSetorDestinoID(), "Setor",
                setorRepository::findById);
        Status status = resolveOptional(ticket.getStatusID(), "Status",
                statusRepository::findById);

        return new TicketResponse(ticket.getId(), ticket.getTitulo(), ticket.getDescricao(),
                ticket.getPrioridade(), ticket.getDataCriacao(), ticket.getDataConclusao(),
                ticket.getSolicitanteID(), ticket.getResponsavelID(), ticket.getCategoriaID(),
                ticket.getSetorDestinoID(), ticket.getStatusID(), solicitante, responsavel,
                categoriaDocumento == null ? null : categoria(categoriaDocumento), setor, status);
    }

    public CategoriaResponse categoria(Categoria categoria) {
        Setor setor = resolveOptional(categoria.getSetorRelacionadoID(), "Setor da categoria",
                setorRepository::findById);
        return new CategoriaResponse(categoria.getId(), categoria.getNome(),
                categoria.getDescricao(), categoria.getSetorRelacionadoID(), setor);
    }

    public ComentarioResponse comentario(Comentario comentario) {
        Ticket ticket = resolveRequired(comentario.getTicketID(), "Ticket",
                ticketRepository::findById);
        Usuario autor = resolveRequired(comentario.getAutorID(), "Autor",
                usuarioRepository::findById);
        return new ComentarioResponse(comentario.getId(), comentario.getMensagem(),
                comentario.getDataPublicacao(), comentario.getTicketID(), comentario.getAutorID(),
                ticket(ticket), usuario(autor));
    }

    public HistoricoResponse historico(Historico historico) {
        Ticket ticket = resolveRequired(historico.getTicketID(), "Ticket",
                ticketRepository::findById);
        Usuario autor = resolveRequired(historico.getAutorAcaoID(), "Autor da ação",
                usuarioRepository::findById);
        return new HistoricoResponse(historico.getId(), historico.getAcao(),
                historico.getValor_anterior(), historico.getValor_novo(), historico.getDataHora(),
                historico.getTicketID(), historico.getAutorAcaoID(), ticket(ticket), usuario(autor));
    }

    public AnexoResponse anexo(Anexo anexo) {
        Ticket ticket = resolveRequired(anexo.getTicketID(), "Ticket",
                ticketRepository::findById);
        return new AnexoResponse(anexo.getId(), anexo.getNome_arquivo(), anexo.getUrl_caminho(),
                anexo.getExtensao(), anexo.getTamanho(), anexo.getTicketID(), ticket(ticket));
    }

    public NotificacaoResponse notificacao(Notificacao notificacao) {
        Usuario usuario = resolveRequired(notificacao.getUsuarioID(), "Usuário",
                usuarioRepository::findById);
        return new NotificacaoResponse(notificacao.getId(), notificacao.getMensagem(),
                notificacao.getLida(), notificacao.getDataEnvio(), notificacao.getUsuarioID(),
                usuario(usuario));
    }

    public Usuario requireUsuario(String id, String label) {
        return resolveRequired(id, label, usuarioRepository::findById);
    }

    public Setor requireSetor(String id, String label) {
        return resolveRequired(id, label, setorRepository::findById);
    }

    public Papel requirePapel(String id, String label) {
        return resolveRequired(id, label, papelRepository::findById);
    }

    public Categoria requireCategoria(String id, String label) {
        return resolveRequired(id, label, categoriaRepository::findById);
    }

    public Status requireStatus(String id, String label) {
        return resolveRequired(id, label, statusRepository::findById);
    }

    public Ticket requireTicket(String id, String label) {
        return resolveRequired(id, label, ticketRepository::findById);
    }

    private UsuarioResumo resolveUser(String id, String label) {
        return id == null ? null : usuario(requireUsuario(id, label));
    }

    private <T> T resolveOptional(String id, String label,
                                  Function<String, java.util.Optional<T>> finder) {
        return id == null ? null : resolveRequired(id, label, finder);
    }

    private <T> T resolveRequired(String id, String label,
                                  Function<String, java.util.Optional<T>> finder) {
        ReferenceIds.require(id, label);
        return finder.apply(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, label + " não encontrado"));
    }
}
