package com.gruppo3.smsconnection.connection.exception;

/**
 * @author Mattia Fanan
 */
public class InvalidPeerException extends Exception{
    public InvalidPeerException(String s){
        super(s);
    }
    public InvalidPeerException(){
        super();
    }
}
