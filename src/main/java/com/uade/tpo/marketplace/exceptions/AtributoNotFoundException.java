package com.uade.tpo.marketplace.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AtributoNotFoundException extends RuntimeException{
    public AtributoNotFoundException(String message){
        super(message);
    }
}
