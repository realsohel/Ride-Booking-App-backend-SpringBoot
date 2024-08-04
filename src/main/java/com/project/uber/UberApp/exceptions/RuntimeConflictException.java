package com.project.uber.UberApp.exceptions;

public class RuntimeConflictException extends RuntimeException{
    public RuntimeConflictException() {
        super();
    }

    public RuntimeConflictException(String message) {
        super(message);
    }
}
