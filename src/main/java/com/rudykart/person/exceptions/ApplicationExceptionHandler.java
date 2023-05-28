package com.rudykart.person.exceptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.rudykart.person.dto.ValidateResponse;

@RestControllerAdvice
public class ApplicationExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        ValidateResponse responseError = new ValidateResponse();
        responseError.setMessage("Validation error");
        responseError.setStatus(HttpStatus.BAD_REQUEST.value());
        responseError.setErrors(new HashMap<>());

        ex.getBindingResult().getFieldErrors().forEach((error) -> {
            String fieldName = error.getField();
            String fieldNameConvert = fieldName.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();

            String message = error.getDefaultMessage();
            List<String> fieldErrors = responseError.getErrors().get(fieldNameConvert);

            if (fieldErrors == null) {
                fieldErrors = new ArrayList<>();
                responseError.getErrors().put(fieldNameConvert, fieldErrors);
            }

            fieldErrors.add(message);
        });

        return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<ErrorObject> handlePersonNotFoundException(PersonNotFoundException ex, WebRequest request) {

        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WeaponNotFoundException.class)
    public ResponseEntity<ErrorObject> handleWeaponNotFoundException(WeaponNotFoundException ex, WebRequest request) {

        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.NOT_FOUND);
    }
}
