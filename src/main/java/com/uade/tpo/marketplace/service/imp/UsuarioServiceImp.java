package com.uade.tpo.marketplace.service.imp;

import com.uade.tpo.marketplace.controllers.usuarios.UsuarioRequest;
import com.uade.tpo.marketplace.controllers.usuarios.UsuarioUpdateRequest;
import com.uade.tpo.marketplace.entity.Usuario;
import com.uade.tpo.marketplace.exceptions.UsuarioDuplicadoException;
import com.uade.tpo.marketplace.exceptions.UsuarioNotFoundException;
import com.uade.tpo.marketplace.repository.UsuarioRepository;
import com.uade.tpo.marketplace.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; // <--- Esta es la importación CLAVE
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImp implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioServiceImp(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Usuario crearUsuario(Usuario usuario) throws UsuarioDuplicadoException {
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new UsuarioDuplicadoException("El email ya está registrado");
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario updateUsuario(String id, UsuarioUpdateRequest usuarioUpdate) {
        return usuarioRepository.findById(id).map(existing -> {
            // Validar email solo si se está cambiando
            if (usuarioUpdate.getEmail() != null &&
                    !existing.getEmail().equals(usuarioUpdate.getEmail()) &&
                    usuarioRepository.findByEmail(usuarioUpdate.getEmail()).isPresent()) {
                throw new UsuarioDuplicadoException("El email ya está registrado");
            }

            // Actualizar solo los campos que no son null
            if (usuarioUpdate.getNombre() != null) {
                existing.setNombre(usuarioUpdate.getNombre());
            }
            if (usuarioUpdate.getApellido() != null) {
                existing.setApellido(usuarioUpdate.getApellido());
            }
            if (usuarioUpdate.getEmail() != null) {
                existing.setEmail(usuarioUpdate.getEmail());
            }
            if (usuarioUpdate.getPassword() != null && !usuarioUpdate.getPassword().isEmpty()) {
                existing.setPassword(passwordEncoder.encode(usuarioUpdate.getPassword()));
            }
            if (usuarioUpdate.getDni() != null) {
                existing.setDni(usuarioUpdate.getDni());
            }
            if (usuarioUpdate.getRole() != null) {
                existing.setRole(usuarioUpdate.getRole());
            }
            if (usuarioUpdate.getEstado() != null) {
                existing.setEstado(usuarioUpdate.getEstado());
            }

            return usuarioRepository.save(existing);
        }).orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado"));
    }

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> findById(String id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public List<Usuario> findByNombre(String nombre) {
        return usuarioRepository.findByNombre(nombre);
    }

    @Override
    public void deleteUsuario(String id) {
        if (!usuarioRepository.existsById(id)) {
            throw new UsuarioNotFoundException("Usuario no encontrado con ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }
}