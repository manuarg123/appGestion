package com.example.api.common;

public class NotValidException extends RuntimeException {
    public NotValidException(String message) {
        super(message);
    }
}