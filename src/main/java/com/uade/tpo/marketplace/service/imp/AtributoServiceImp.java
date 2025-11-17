package com.uade.tpo.marketplace.service.imp;

import com.uade.tpo.marketplace.entity.Atributo;
import com.uade.tpo.marketplace.controllers.atributos.AtributoRequest;
import com.uade.tpo.marketplace.exceptions.AtributoNotFoundException;
import com.uade.tpo.marketplace.repository.AtributoRepository;
import com.uade.tpo.marketplace.service.AtributoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AtributoServiceImp implements AtributoService {

    private final AtributoRepository atributoRepository;

    @Autowired
    public AtributoServiceImp(AtributoRepository atributoRepository) {
        this.atributoRepository = atributoRepository;
    }

    @Override
    public Atributo crearAtributo(AtributoRequest request) {
        Atributo atributo = new Atributo();
        atributo.setNombre(request.getNombre());
        return atributoRepository.save(atributo);
    }

    @Override
    public Atributo actualizarAtributo(String id, AtributoRequest request) {
        Atributo atributo = atributoRepository.findById(id)
                .orElseThrow(() -> new AtributoNotFoundException("Atributo no encontrado con ID: " + id));
        atributo.setNombre(request.getNombre());
        return atributoRepository.save(atributo);
    }

    @Override
    public List<Atributo> findAll() {
        return atributoRepository.findAll();
    }

    @Override
    public Atributo findById(String id) {
        return atributoRepository.findById(id)
                .orElseThrow(() -> new AtributoNotFoundException("Atributo no encontrado con ID: " + id));
    }

    @Override
    public void eliminarAtributo(String id) {
        if (!atributoRepository.existsById(id)) {
            throw new AtributoNotFoundException("Atributo no encontrado con ID: " + id);
        }
        atributoRepository.deleteById(id);
    }
}
