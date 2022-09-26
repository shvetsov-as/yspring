package com.edu.ulab.app.exception;

public class BookNotSavedException extends RuntimeException {
    public BookNotSavedException() {
        super("Internal error. Could not save the book");
    }
}
