package com.example.studentdemo.exception;

public class DataAlreadyExistsException extends RuntimeException {
    
    public DataAlreadyExistsException(String message) {
        super(message);
    }
}

