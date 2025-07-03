package com.github.guiziin227.livraria.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RelationshipException extends RuntimeException {
    public RelationshipException(String message) {
        super(message);
    }

    public RelationshipException(String message, Throwable cause) {
        super(message, cause);
    }
}
