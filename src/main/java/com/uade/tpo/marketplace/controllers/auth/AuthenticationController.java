package com.uade.tpo.marketplace.controllers.auth;

import com.uade.tpo.marketplace.controllers.usuarios.UsuarioRequest;
import com.uade.tpo.marketplace.entity.Usuario;
import com.uade.tpo.marketplace.enums.Estados;
import com.uade.tpo.marketplace.enums.Role;
import com.uade.tpo.marketplace.service.AuthenticationService;
import com.uade.tpo.marketplace.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;
    private final UsuarioService usuarioService;
    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody UsuarioRequest usuarioRequest) {
        try {
            Usuario usuario = Usuario.builder()
                    .nombre(usuarioRequest.getNombre())
                    .apellido(usuarioRequest.getApellido())
                    .email(usuarioRequest.getEmail())
                    .password(usuarioRequest.getPassword())
                    .dni(usuarioRequest.getDni())
                    .role(usuarioRequest.getRole())
                    .estado(Estados.ACTIVO)
                    .build();

            Usuario usuarioCreado = usuarioService.crearUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCreado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(service.authenticate(request));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
