package com.uade.tpo.marketplace.service.imp;

import com.uade.tpo.marketplace.entity.Categoria;
import com.uade.tpo.marketplace.controllers.categorias.CategoriaRequest;
import com.uade.tpo.marketplace.exceptions.CategoriaNotFoundException;
import com.uade.tpo.marketplace.repository.CategoriaRepository;
import com.uade.tpo.marketplace.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServiceImp implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Autowired
    public CategoriaServiceImp(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public Categoria crearCategoria(CategoriaRequest request) {
        Categoria categoria = new Categoria();
        categoria.setNombre(request.getNombre());
        categoria.setDescripcion(request.getDescripcion());
        categoria.setEstado(request.getEstado() != null ? request.getEstado() : categoria.getEstado());

        if (request.getCategoria() != null) {
            Categoria padre = categoriaRepository.findById(String.valueOf(request.getCategoria()))
                    .orElseThrow(() -> new CategoriaNotFoundException("Categoría padre no encontrada"));
            categoria.setCategoriaPadre(padre);
        }
        return categoriaRepository.save(categoria);
    }

    @Override
    public Categoria actualizarCategoria(String id, CategoriaRequest request) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNotFoundException("Categoría no encontrada con ID: " + id));

        categoria.setNombre(request.getNombre());
        categoria.setDescripcion(request.getDescripcion());
        categoria.setEstado(request.getEstado() != null ? request.getEstado() : categoria.getEstado());

        // Actualizar categoría padre si corresponde
        if (request.getCategoria() != null) {
            Categoria padre = categoriaRepository.findById(request.getCategoria().name())
                    .orElseThrow(() -> new CategoriaNotFoundException("Categoría padre no encontrada"));
            categoria.setCategoriaPadre(padre);
        }

        return categoriaRepository.save(categoria);
    }

    @Override
    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    @Override
    public Categoria findById(String id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNotFoundException("Categoría no encontrada con ID: " + id));
    }

    @Override
    public void eliminarCategoria(String id) {
        if (!categoriaRepository.existsById(id)) {
            throw new CategoriaNotFoundException("Categoría no encontrada con ID: " + id);
        }
        categoriaRepository.deleteById(id);
    }
}
