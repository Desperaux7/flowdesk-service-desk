package com.flowdesk.aplication.business;

import com.flowdesk.aplication.models.Categoria;
import com.flowdesk.aplication.repositories.CategoriaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public Categoria salvaCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public List<Categoria> buscaCategorias() {
        return categoriaRepository.findAll();
    }

    public Categoria buscaCategoriaID(String id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
    }

    public Categoria atualizaCategoria(Categoria categoria) {
        Optional<Categoria> categoriaExiste = categoriaRepository.findById(categoria.getId());
        if (categoriaExiste.isPresent()) {
            return categoriaRepository.save(categoria);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada");
    }

    public void deletarCategoria(String id) {
        categoriaRepository.deleteById(id);
    }
}
