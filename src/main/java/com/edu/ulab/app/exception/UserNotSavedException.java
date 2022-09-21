package com.edu.ulab.app.exception;

public class UserNotSavedException extends RuntimeException {
    public UserNotSavedException() {
        super("Internal error. Could not save user's data");
    }
}
