package com.flowdesk.aplication.business;

import com.flowdesk.aplication.models.Usuario;
import com.flowdesk.aplication.repositories.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario salvaUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }
    public List<Usuario> buscarUsuarios(){
        return usuarioRepository.findAll();
    }
    public Usuario atualizarUsuario(Usuario usuario){
       Optional<Usuario> usuarioExiste = usuarioRepository.findById(usuario.getId());
       if (usuarioExiste.isPresent()){
          return usuarioRepository.save(usuario);
       }
       throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não encontrado");

    }
    public void deletarUsuario(String id){
        usuarioRepository.deleteById(id);
    }
    public Usuario buscaUsuarioID(String id){
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
    }
}
