package com.gruppo3.smslibrary.exceptions;

/**
 * @author Mattia Fanan
 * @version 1
 *
 * InvalidMessageException class.
 */
public class InvalidMessageException extends RuntimeException {
    public InvalidMessageException() {
        super();
    }

    public InvalidMessageException(String s) {
        super(s);
    }
}
