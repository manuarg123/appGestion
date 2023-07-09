package com.example.api.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@Component
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity handleException(Exception e){

        APIResponse apiResponse = new APIResponse();
        apiResponse.setError("Oops..Something went wrong!");
        apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(apiResponse);
    }

    @ExceptionHandler
    public ResponseEntity handleBadRequestException(BadRequestException e){

        APIResponse apiResponse = new APIResponse();
        apiResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        apiResponse.setError(e.getErrors());

        return ResponseEntity.status(400).body(apiResponse);
    }

    @ExceptionHandler
    public ResponseEntity handleAccessDeniedException(AccessDeniedException e){

        APIResponse apiResponse = new APIResponse();
        apiResponse.setStatus(HttpStatus.UNAUTHORIZED.value());

        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    /**
     * Manejo de excepciones en cuanto a validez de datos que se intentan agregar a clases
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleValidationException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        //Procesa errores de validación y genera respuesta
        List<String> errors = new ArrayList<>();
        for (FieldError fieldError : fieldErrors){
            errors.add(fieldError.getDefaultMessage());
        }

        APIResponse apiResponse = new APIResponse();
        apiResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        apiResponse.setError(errors);

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(DuplicateRecordException.class)
    public ResponseEntity<Object> handleDuplicateRecordException(DuplicateRecordException e) {
        APIResponse apiResponse = new APIResponse();
        apiResponse.setStatus(HttpStatus.CONFLICT.value());
        apiResponse.setError(e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(apiResponse);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException e){
        APIResponse apiResponse = new APIResponse();
        apiResponse.setStatus(HttpStatus.NOT_FOUND.value());
        apiResponse.setError(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(apiResponse);
    }

    @ExceptionHandler(NotValidException.class)
    public ResponseEntity<Object> handleNotValidException(NotValidException e){
        APIResponse apiResponse = new APIResponse();
        apiResponse.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
        apiResponse.setError(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE.value()).body(apiResponse);
    }
}