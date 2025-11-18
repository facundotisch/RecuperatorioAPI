package com.uade.tpo.marketplace.service;

import com.uade.tpo.marketplace.entity.Atributo;
import com.uade.tpo.marketplace.controllers.atributos.AtributoRequest;

import java.util.List;

public interface AtributoService {
    Atributo crearAtributo(AtributoRequest request);

    Atributo actualizarAtributo(String id, AtributoRequest request);

    List<Atributo> findAll();

    Atributo findById(String id);

    void eliminarAtributo(String id);
}
