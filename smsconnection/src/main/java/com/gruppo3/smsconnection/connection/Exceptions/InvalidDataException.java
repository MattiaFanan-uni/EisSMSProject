package com.gruppo3.smsconnection.connection.exceptions;

public class InvalidDataException extends Exception {
    public InvalidDataException(String s){
        super(s);
    }
    public InvalidDataException(){
        super();
    }
}
