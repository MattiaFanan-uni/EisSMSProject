package com.gruppo3.smsconnection.connection.exception;

public class InvalidHeaderException extends Exception{
    public InvalidHeaderException(String s){
        super(s);
    }
    public InvalidHeaderException(){
        super();
    }
}
