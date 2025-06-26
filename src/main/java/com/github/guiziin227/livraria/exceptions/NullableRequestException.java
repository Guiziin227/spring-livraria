package com.github.guiziin227.livraria.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NullableRequestException extends RuntimeException {
    public NullableRequestException(String message) {
        super(message);
    }
}
