package com.example.user_practice_1.exception;

public class IncompleteRequestBodyException extends RuntimeException{
    public IncompleteRequestBodyException(String message) {
        super(message);
    }
}
