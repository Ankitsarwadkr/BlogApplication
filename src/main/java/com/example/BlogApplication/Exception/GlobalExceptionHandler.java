package com.example.BlogApplication.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class )
    public ResponseEntity<Map<String,String>> handleResourceNotFound(ResourceNotFoundException ex)
    {
        Map<String,String> error=new HashMap<>();
        error.put("Status","404");
        error.put("error", ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> handleValidationExceptions(MethodArgumentNotValidException ex)
    {
        Map<String,Object> response=new HashMap<>();
        response.put("Status","400");
        response.put("error","Validation failed");

        Map<String,String> fieldErrors =new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error)->{
            String fieldName=((FieldError)error).getField();
            String message= error.getDefaultMessage();
            fieldErrors.put(fieldName,message);
        });
        response.put("fields",fieldErrors);
        return  new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,String>> handleAllExceptions(Exception ex)
    {
        Map<String,String> error=new HashMap<>();
        error.put("Status","500");
        error.put("error","Internal Server Error");
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
