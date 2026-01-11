package com.example.taskmanager.Exception;

public class UserExistanceException extends RuntimeException {
    public UserExistanceException(String message) {
        super(message);
    }
}
