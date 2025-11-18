package com.uade.tpo.marketplace.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT) // Un conflicto (409) es más apropiado para un recurso duplicado
public class UsuarioDuplicadoException extends RuntimeException {

    // Constructor que acepta un mensaje
    public UsuarioDuplicadoException(String message) {
        super(message);
    }

    // Puedes mantener el constructor sin argumentos si lo necesitas, pero no es estrictamente necesario si siempre pasas un mensaje
    public UsuarioDuplicadoException() {
        super("El usuario ya existe"); // Mensaje por defecto si se usa sin argumentos
    }
}