package com.gruppo3.smsconnection.connection.exceptions;

public class InvalidPeerException extends Exception{
    public InvalidPeerException(String s){
        super(s);
    }
    public InvalidPeerException(){
        super();
    }
}
