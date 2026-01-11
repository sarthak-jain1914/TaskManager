package com.example.taskmanager.Advice;

import com.example.taskmanager.DTOs.ErrorDto;
import com.example.taskmanager.Exception.AuthorizationFailedException;
import com.example.taskmanager.Exception.SessionExpiryException;
import com.example.taskmanager.Exception.TaskNotFoundException;
import com.example.taskmanager.Exception.UserExistanceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class ControllerAdvice {

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorDto> handleTaskNotFoundException() {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setErrorMessage("Invalid task or Task not found");
        errorDto.setErrorCode("400");
        return ResponseEntity.badRequest().body(errorDto);
    }

    @ExceptionHandler(UserExistanceException.class)
    public ResponseEntity<ErrorDto> handleUserExistanceException(){
        ErrorDto errorDto = new ErrorDto();
        errorDto.setErrorMessage("Invalid user or User does not exist");
        errorDto.setErrorCode("401");
        return ResponseEntity.badRequest().body(errorDto);
    }
    @ExceptionHandler(AuthorizationFailedException.class)
    public ResponseEntity<ErrorDto> handleAuthorizationFailedException(){
        ErrorDto errorDto = new ErrorDto();
        errorDto.setErrorMessage("Authorization failed. Please login again.");
        errorDto.setErrorCode("402");
        return ResponseEntity.badRequest().body(errorDto);
    }
    @ExceptionHandler(SessionExpiryException.class)
    public ResponseEntity<ErrorDto> handleSessionExpiryException(){
        ErrorDto errorDto = new ErrorDto();
        errorDto.setErrorMessage("Authorization failed. Please login again.");
        errorDto.setErrorCode("403");
        return ResponseEntity.badRequest().body(errorDto);
    }

}
