package com.example.let.exception;

public class CustomException extends Exception {
    private final int ERR_CODE;

    CustomException(String msg, int errCode) {

        super(msg);

        ERR_CODE = errCode;

    }

    public CustomException(String msg) {

        this(msg, 100);

    }

    public int getErrCode() {

        return ERR_CODE;

    }

}