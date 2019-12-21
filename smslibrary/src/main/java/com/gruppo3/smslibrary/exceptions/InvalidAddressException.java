package com.gruppo3.smslibrary.exceptions;

/**
 * @author Mattia Fanan
 * @version 1
 *
 * InvalidAddressException class.
 */
public class InvalidAddressException extends RuntimeException {
    public InvalidAddressException() {
        super();
    }

    public InvalidAddressException(String s) {
        super(s);
    }
}
