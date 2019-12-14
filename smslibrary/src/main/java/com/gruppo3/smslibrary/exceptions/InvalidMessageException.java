package com.gruppo3.smslibrary.exceptions;

/**
 * InvalidMessageException class
 * @author Mattia Fanan
 */
public class InvalidMessageException extends RuntimeException {
    public InvalidMessageException() {
        super();
    }

    public InvalidMessageException(String s) {
        super(s);
    }
}
