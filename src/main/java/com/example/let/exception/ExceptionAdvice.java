package com.example.let.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(GlobalException.class)
    protected ResponseEntity<ExceptionResponseDto> GlobalException(GlobalException e){
        ExceptionResponseDto responseDto = ExceptionResponseDto.builder()
                .message(e.getMessage())
                .status(e.getHttpStatus().value())
                .error(e.getHttpStatus())
                .build();
        return ResponseEntity.status(e.getHttpStatus())
                .body(responseDto);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    protected ResponseEntity<ExceptionResponseDto> DuplicateKeyException(SQLIntegrityConstraintViolationException e){
        ExceptionResponseDto responseDto = ExceptionResponseDto.builder()
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(responseDto);
    }
}