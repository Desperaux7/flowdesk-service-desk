package com.flowdesk.aplication.controllers;

import com.flowdesk.aplication.business.UsuarioService;
import com.flowdesk.aplication.models.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Usuario> salvaUsuario(@RequestBody Usuario usuario){
        return ResponseEntity.ok().body(usuarioService.salvaUsuario(usuario));
    }
    @GetMapping
    public ResponseEntity<List<Usuario>> buscaUsuario(){
        return ResponseEntity.ok().body(usuarioService.buscarUsuarios());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscaUsuarioID(@PathVariable String id){
        return ResponseEntity.ok().body(usuarioService.buscaUsuarioID(id));
    }
    @PutMapping
    public ResponseEntity<Usuario> atualizarUsuario(@RequestBody Usuario usuario){
        return ResponseEntity.ok().body(usuarioService.atualizarUsuario(usuario));
    }
    @DeleteMapping
    public ResponseEntity<Void> deletarUsuario(@RequestParam("id") String id){
        usuarioService.deletarUsuario(id);
        return ResponseEntity.accepted().build();
    }
}
