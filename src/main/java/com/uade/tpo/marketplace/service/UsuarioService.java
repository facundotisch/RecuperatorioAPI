package com.uade.tpo.marketplace.service;

import com.uade.tpo.marketplace.controllers.usuarios.UsuarioRequest;
import com.uade.tpo.marketplace.controllers.usuarios.UsuarioUpdateRequest;
import com.uade.tpo.marketplace.entity.Usuario;
import com.uade.tpo.marketplace.exceptions.UsuarioDuplicadoException;
import com.uade.tpo.marketplace.exceptions.UsuarioNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    Usuario crearUsuario(Usuario usuario) throws UsuarioDuplicadoException;

    List<Usuario> findAll();

    Optional<Usuario> findById(String id);

    Optional<Usuario> findByEmail(String email);

    List<Usuario> findByNombre(String nombre);

    Usuario updateUsuario(String id, UsuarioUpdateRequest usuario) throws UsuarioDuplicadoException, UsuarioNotFoundException; // Añadido throws

    void deleteUsuario(String id);
}