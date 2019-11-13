package com.gruppo3.smsconnection.connection.exception;

/**
 * @author Mattia Fanan
 */
public class UnknownPeerException extends Exception{
        public UnknownPeerException(String s){
            super(s);
        }
        public UnknownPeerException(){
            super();
        }
}

