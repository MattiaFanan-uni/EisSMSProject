package com.gruppo3.smsconnection.connection.exception;

/**
 * InvalidItemException class
 * @author Riccardo Crociani
 */
public class InvalidItemException extends RuntimeException{
    public InvalidItemException(){
        super();
    }

    public InvalidItemException(String s){
        super(s);
    }
}
