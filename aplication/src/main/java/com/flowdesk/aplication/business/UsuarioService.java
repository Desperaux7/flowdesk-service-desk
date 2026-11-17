package com.flowdesk.aplication.business;

import com.flowdesk.aplication.common.ReferenceIds;
import com.flowdesk.aplication.common.RelationshipMapper;
import com.flowdesk.aplication.dto.UsuarioResumo;
import com.flowdesk.aplication.models.Usuario;
import com.flowdesk.aplication.repositories.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final SetorRepository setorRepository;
    private final PapelRepository papelRepository;
    private final TicketRepository ticketRepository;
    private final ComentarioRepository comentarioRepository;
    private final HistoricoRepository historicoRepository;
    private final NotificacaoRepository notificacaoRepository;
    private final RelationshipMapper relationshipMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, SetorRepository setorRepository,
                          PapelRepository papelRepository, TicketRepository ticketRepository,
                          ComentarioRepository comentarioRepository, HistoricoRepository historicoRepository,
                          NotificacaoRepository notificacaoRepository,
                          RelationshipMapper relationshipMapper) {
        this.usuarioRepository = usuarioRepository;
        this.setorRepository = setorRepository;
        this.papelRepository = papelRepository;
        this.ticketRepository = ticketRepository;
        this.comentarioRepository = comentarioRepository;
        this.historicoRepository = historicoRepository;
        this.notificacaoRepository = notificacaoRepository;
        this.relationshipMapper = relationshipMapper;
    }

    public UsuarioResumo salvaUsuario(Usuario usuario) {
        if (usuario.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Não informe ID ao criar usuário");
        }
        validar(usuario, null);
        usuario.setDataCriacao(usuario.getDataCriacao() == null
                ? LocalDateTime.now() : usuario.getDataCriacao());
        return relationshipMapper.usuario(usuarioRepository.save(usuario));
    }

    public List<UsuarioResumo> buscarUsuarios() {
        return usuarioRepository.findAll().stream().map(relationshipMapper::usuario).toList();
    }

    public List<UsuarioResumo> buscaPorSetor(String setorId) {
        relationshipMapper.requireSetor(setorId, "Setor");
        return usuarioRepository.findBySetorId(setorId).stream()
                .map(relationshipMapper::usuario).toList();
    }

    public List<UsuarioResumo> buscaPorPapel(String papelId) {
        relationshipMapper.requirePapel(papelId, "Papel");
        return usuarioRepository.findByPapelId(papelId).stream()
                .map(relationshipMapper::usuario).toList();
    }

    public UsuarioResumo atualizarUsuario(Usuario usuario) {
        ReferenceIds.require(usuario.getId(), "ID do usuário");
        Usuario existente = usuarioRepository.findById(usuario.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
        validar(usuario, usuario.getId());
        if (usuario.getDataCriacao() == null) {
            usuario.setDataCriacao(existente.getDataCriacao());
        }
        return relationshipMapper.usuario(usuarioRepository.save(usuario));
    }

    public void deletarUsuario(String id) {
        ReferenceIds.require(id, "ID do usuário");
        if (!usuarioRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }
        if (ticketRepository.existsBySolicitanteID(id)
                || ticketRepository.existsByResponsavelID(id)
                || comentarioRepository.existsByAutorID(id)
                || historicoRepository.existsByAutorAcaoID(id)
                || notificacaoRepository.existsByUsuarioID(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Não é possível excluir usuário com tickets, comentários, histórico ou notificações associados");
        }
        usuarioRepository.deleteById(id);
    }

    public UsuarioResumo buscaUsuarioID(String id) {
        ReferenceIds.require(id, "ID do usuário");
        return usuarioRepository.findById(id)
                .map(relationshipMapper::usuario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
    }

    private void validar(Usuario usuario, String idAtual) {
        String setorId = ReferenceIds.require(usuario.getSetorId(), "Setor");
        String papelId = ReferenceIds.require(usuario.getPapelId(), "Papel");
        if (!setorRepository.existsById(setorId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Setor não encontrado");
        }
        if (!papelRepository.existsById(papelId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Papel não encontrado");
        }
        if (usuario.getNome() == null || usuario.getNome().isBlank()
                || usuario.getEmail() == null || usuario.getEmail().isBlank()
                || usuario.getSenha_hash() == null || usuario.getSenha_hash().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Nome, email e senha_hash são obrigatórios");
        }
        usuario.setNome(usuario.getNome().trim());
        usuario.setEmail(usuario.getEmail().trim().toLowerCase(Locale.ROOT));
        usuarioRepository.findByEmailIgnoreCase(usuario.getEmail()).ifPresent(duplicado -> {
            if (!duplicado.getId().equals(idAtual)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Email já cadastrado");
            }
        });
    }
}
