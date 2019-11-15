package com.gruppo3.smsconnection.connection.exception;
/**
 * @author Mattia Fanan
 */
public class InvalidPayloadException extends Exception {
    public InvalidPayloadException(String s){
        super(s);
    }
    public InvalidPayloadException(){
        super();
    }
}
