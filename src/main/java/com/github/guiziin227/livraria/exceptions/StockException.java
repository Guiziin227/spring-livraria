package com.github.guiziin227.livraria.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class StockException extends RuntimeException {
    public StockException(String message) {
        super(message);
    }

    public StockException(String message, Throwable cause) {
        super(message, cause);
    }
}
