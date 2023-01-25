package com.levelup.library.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ NoSuchElementException.class })
    public ResponseEntity<Map<String, String>> handleNoSuchElementException(NoSuchElementException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ SQLException.class })
    public ResponseEntity<Map<String, String>> handleSQLException(SQLException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        error.put("message", "An error occurred while executing this process, please try again.");
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return  new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ NullPointerException.class })
    public  ResponseEntity<Map<String, String>> handleNullPointerException(NullPointerException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ EmailInUseException.class })
    public ResponseEntity<Map<String, String>> handleEmailInUseException(EmailInUseException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Email in use.");
        error.put("message", ex.getMessage());
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ CpfInUseException.class })
    public ResponseEntity<Map<String, String>> handleCpfInUseException(CpfInUseException ex){
        Map<String, String> error = new HashMap<>();
        error.put("error", "CPF in use.");
        error.put("message", ex.getMessage());
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ InvalidCpfException.class })
    public ResponseEntity<Map<String, String>> handleInvalidCpfException(InvalidCpfException ex){
        Map<String, String> error = new HashMap<>();
        error.put("error", "CPF in use.");
        error.put("message", ex.getMessage());
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ InvalidDateException.class })
    public ResponseEntity<Map<String, String>> handleInvalidDateException(InvalidDateException ex){
        Map<String, String> error = new HashMap<>();
        error.put("error", "Invalid date.");
        error.put("message", ex.getMessage());
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ InvalidReturnException.class })
    public ResponseEntity<Map<String, String>> handleInvalidReturnException(InvalidReturnException ex){
        Map<String, String> error = new HashMap<>();
        error.put("error", "Book already available.");
        error.put("message", ex.getMessage());
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ InvalidWithdrawException.class })
    public ResponseEntity<Map<String, String>> handleInvalidWithdrawException(InvalidWithdrawException ex){
        Map<String, String> error = new HashMap<>();
        error.put("error", "Book already reserved.");
        error.put("message", ex.getMessage());
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({ NoSuchIdException.class })
    public ResponseEntity<Map<String, String>> handleNoSuchIdException(NoSuchIdException ex){
        Map<String, String> error = new HashMap<>();
        error.put("error", "Invalid ID.");
        error.put("message", ex.getMessage());
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

}
