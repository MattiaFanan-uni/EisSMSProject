package com.gruppo3.smsconnection.connection.exception;

/**
 * InvalidMessageException class
 * @author Mattia Fanan
 */
public class InvalidMessageException extends Exception {
    public InvalidMessageException(){
        super();
    }

    public InvalidMessageException(String s){
        super(s);
    }
}
