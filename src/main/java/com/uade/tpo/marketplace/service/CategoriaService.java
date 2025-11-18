package com.uade.tpo.marketplace.service;

import com.uade.tpo.marketplace.entity.Categoria;
import com.uade.tpo.marketplace.controllers.categorias.CategoriaRequest;

import java.util.List;

public interface CategoriaService {
    Categoria crearCategoria(CategoriaRequest request) throws Exception;

    Categoria actualizarCategoria(String id, CategoriaRequest request);

    List<Categoria> findAll();

    Categoria findById(String id);

    void eliminarCategoria(String id);
}
