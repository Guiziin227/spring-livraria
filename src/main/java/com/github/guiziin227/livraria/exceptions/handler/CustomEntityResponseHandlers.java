package com.github.guiziin227.livraria.exceptions.handler;

import com.github.guiziin227.livraria.exceptions.DbIntegrityException;
import com.github.guiziin227.livraria.exceptions.ExceptionResponse;
import com.github.guiziin227.livraria.exceptions.NullableRequestException;
import com.github.guiziin227.livraria.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class CustomEntityResponseHandlers extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class})
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex, WebRequest request) {
        ExceptionResponse resp = new ExceptionResponse(
                ex.getMessage(),
                request.getDescription(false),
                new Date()
        );
        return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    public final ResponseEntity<ExceptionResponse> handleNotFoundException(Exception ex, WebRequest request) {
        ExceptionResponse resp = new ExceptionResponse(
                ex.getMessage(),
                request.getDescription(false),
                new Date()
        );
        return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({NullableRequestException.class})
    public final ResponseEntity<ExceptionResponse> handleNullableRequestException(Exception ex, WebRequest request) {
        ExceptionResponse resp = new ExceptionResponse(
                ex.getMessage(),
                request.getDescription(false),
                new Date()
        );
        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({DbIntegrityException.class})
    public final ResponseEntity<ExceptionResponse> handleDbIntegrityException(Exception ex, WebRequest request) {
        ExceptionResponse resp = new ExceptionResponse(
                ex.getMessage(),
                request.getDescription(false),
                new Date()
        );
        return new ResponseEntity<>(resp, HttpStatus.BAD_GATEWAY);
    }

}
