package com.github.guiziin227.livraria.exceptions;

import java.util.Date;

public record ExceptionResponse(
    String message,
    String details,
    Date timestamp
) {
    public ExceptionResponse(String message, String details, Date timestamp) {
        this.message = message;
        this.details = details;
        this.timestamp = timestamp;

    }
}
