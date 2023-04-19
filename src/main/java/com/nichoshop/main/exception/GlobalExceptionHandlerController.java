package com.nichoshop.main.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandlerController {

  @ExceptionHandler(CustomException.class)
  public ResponseEntity<ExceptionResponse> handleCustomException(CustomException ex) {
    ExceptionResponse exReponse = new ExceptionResponse();
    exReponse.setMessage(ex.getMessage());
    return new ResponseEntity<ExceptionResponse>(exReponse, ex.getHttpStatus());
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ExceptionResponse> handleException(RuntimeException e) {
    ExceptionResponse exReponse = new ExceptionResponse();
    exReponse.setMessage(e.getMessage());
    return new ResponseEntity<ExceptionResponse>(exReponse, HttpStatus.BAD_REQUEST);
  }

}
