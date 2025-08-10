package com.restaurante.pos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice // Le dice a Spring que esta clase vigilará todos los controladores
public class GlobalExceptionHandler {

    // Este método se ejecutará cada vez que un controlador lance una IllegalStateException
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Object> handleIllegalStateException(IllegalStateException ex) {
        // Creamos un cuerpo de respuesta JSON claro
        Map<String, Object> body = Map.of(
                "error", "Conflicto de datos",
                "message", ex.getMessage()
        );
        // Devolvemos un error 409 CONFLICT con el mensaje
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }
}