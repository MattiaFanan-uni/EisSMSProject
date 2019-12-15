package com.gruppo3.smslibrary.exceptions;

/**
 * InvalidAddressException class
 * @author Mattia Fanan
 */
public class InvalidAddressException extends RuntimeException {
    public InvalidAddressException() {
        super();
    }

    public InvalidAddressException(String s) {
        super(s);
    }
}
