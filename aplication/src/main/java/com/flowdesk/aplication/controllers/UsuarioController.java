package com.flowdesk.aplication.controllers;

import com.flowdesk.aplication.business.UsuarioService;
import com.flowdesk.aplication.dto.UsuarioResumo;
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
    public ResponseEntity<UsuarioResumo> salvaUsuario(@RequestBody Usuario usuario){
        return ResponseEntity.status(201).body(usuarioService.salvaUsuario(usuario));
    }
    @GetMapping
    public ResponseEntity<List<UsuarioResumo>> buscaUsuario(){
        return ResponseEntity.ok().body(usuarioService.buscarUsuarios());
    }

    @GetMapping("/setor/{setorId}")
    public ResponseEntity<List<UsuarioResumo>> buscaPorSetor(@PathVariable String setorId) {
        return ResponseEntity.ok(usuarioService.buscaPorSetor(setorId));
    }

    @GetMapping("/papel/{papelId}")
    public ResponseEntity<List<UsuarioResumo>> buscaPorPapel(@PathVariable String papelId) {
        return ResponseEntity.ok(usuarioService.buscaPorPapel(papelId));
    }
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResumo> buscaUsuarioID(@PathVariable String id){
        return ResponseEntity.ok().body(usuarioService.buscaUsuarioID(id));
    }
    @PutMapping
    public ResponseEntity<UsuarioResumo> atualizarUsuario(@RequestBody Usuario usuario){
        return ResponseEntity.ok().body(usuarioService.atualizarUsuario(usuario));
    }
    @DeleteMapping
    public ResponseEntity<Void> deletarUsuario(@RequestParam("id") String id){
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
