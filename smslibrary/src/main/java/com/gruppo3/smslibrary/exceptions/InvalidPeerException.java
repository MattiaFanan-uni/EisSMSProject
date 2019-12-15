package com.gruppo3.smslibrary.exceptions;

/**
 * InvalidPeerException class
 * @author Mattia Fanan
 */
public class InvalidPeerException extends RuntimeException {
    public InvalidPeerException() {
        super();
    }

    public InvalidPeerException(String s) {
        super(s);
    }
}
