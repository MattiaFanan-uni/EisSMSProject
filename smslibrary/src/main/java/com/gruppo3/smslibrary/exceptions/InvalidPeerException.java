package com.gruppo3.smslibrary.exceptions;

/**
 * @author Mattia Fanan
 * @version 1
 *
 * InvalidPeerException class.
 */
public class InvalidPeerException extends RuntimeException {
    public InvalidPeerException() {
        super();
    }

    public InvalidPeerException(String s) {
        super(s);
    }
}
