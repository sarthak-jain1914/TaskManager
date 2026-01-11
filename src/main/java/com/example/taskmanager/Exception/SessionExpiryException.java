package com.example.taskmanager.Exception;

public class SessionExpiryException extends RuntimeException {
  public SessionExpiryException(String message) {
    super(message);
  }
}
