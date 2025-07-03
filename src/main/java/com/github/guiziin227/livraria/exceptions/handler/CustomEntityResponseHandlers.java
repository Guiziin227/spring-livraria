package com.github.guiziin227.livraria.exceptions.handler;

import com.github.guiziin227.livraria.exceptions.DbIntegrityException;
import com.github.guiziin227.livraria.exceptions.ExceptionResponse;
import com.github.guiziin227.livraria.exceptions.NullableRequestException;
import com.github.guiziin227.livraria.exceptions.ResourceNotFoundException;
import com.github.guiziin227.livraria.exceptions.DuplicateResourceException;
import com.github.guiziin227.livraria.exceptions.BusinessRuleException;
import com.github.guiziin227.livraria.exceptions.InvalidOperationException;
import com.github.guiziin227.livraria.exceptions.InvalidDateRangeException;
import com.github.guiziin227.livraria.exceptions.RelationshipException;
import com.github.guiziin227.livraria.exceptions.StockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.stream.Collectors;

@ControllerAdvice
@RestController
public class CustomEntityResponseHandlers {

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

    @ExceptionHandler({DuplicateResourceException.class})
    public final ResponseEntity<ExceptionResponse> handleDuplicateResourceException(Exception ex, WebRequest request) {
        ExceptionResponse resp = new ExceptionResponse(
                ex.getMessage(),
                request.getDescription(false),
                new Date()
        );
        return new ResponseEntity<>(resp, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({BusinessRuleException.class})
    public final ResponseEntity<ExceptionResponse> handleBusinessRuleException(Exception ex, WebRequest request) {
        ExceptionResponse resp = new ExceptionResponse(
                ex.getMessage(),
                request.getDescription(false),
                new Date()
        );
        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidOperationException.class})
    public final ResponseEntity<ExceptionResponse> handleInvalidOperationException(Exception ex, WebRequest request) {
        ExceptionResponse resp = new ExceptionResponse(
                ex.getMessage(),
                request.getDescription(false),
                new Date()
        );
        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidDateRangeException.class})
    public final ResponseEntity<ExceptionResponse> handleInvalidDateRangeException(Exception ex, WebRequest request) {
        ExceptionResponse resp = new ExceptionResponse(
                ex.getMessage(),
                request.getDescription(false),
                new Date()
        );
        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({RelationshipException.class})
    public final ResponseEntity<ExceptionResponse> handleRelationshipException(Exception ex, WebRequest request) {
        ExceptionResponse resp = new ExceptionResponse(
                ex.getMessage(),
                request.getDescription(false),
                new Date()
        );
        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({StockException.class})
    public final ResponseEntity<ExceptionResponse> handleStockException(Exception ex, WebRequest request) {
        ExceptionResponse resp = new ExceptionResponse(
                ex.getMessage(),
                request.getDescription(false),
                new Date()
        );
        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public final ResponseEntity<ExceptionResponse> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ExceptionResponse resp = new ExceptionResponse(
                "Erro de validação: " + message,
                request.getDescription(false),
                new Date()
        );
        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }
}