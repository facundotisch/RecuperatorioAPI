package com.uade.tpo.marketplace.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public final class FavoriteExceptions {

    private FavoriteExceptions() {}

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class FavoriteResourceNotFoundException extends RuntimeException {
        public FavoriteResourceNotFoundException(String message) {
            super(message);
        }
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    public static class FavoriteAlreadyExistsException extends RuntimeException {
        public FavoriteAlreadyExistsException(String message) {
            super(message);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static class FavoriteBadRequestException extends RuntimeException {
        public FavoriteBadRequestException(String message) {
            super(message);
        }
    }
}

