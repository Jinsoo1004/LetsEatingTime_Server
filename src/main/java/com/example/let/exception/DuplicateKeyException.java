package com.example.let.exception;

public class DuplicateKeyException extends org.springframework.dao.DuplicateKeyException {
    public DuplicateKeyException(String msg) {
        super(msg);
    }

    public DuplicateKeyException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
