package com.gruppo3.smsconnection.connection;

import java.util.Optional;

/**
 * abstarcton of peer class
 * @param T type of address
 */
public abstract class Peer<T> {
    protected T address;

    protected Peer(T address){
        this.address=address;
    }//only if its valid

    public Optional<T> getAddress(){
        return Optional.ofNullable(address);
    }

    public abstract boolean isValid();

    public enum PeerError{
        TooLong,
        TooShort,
        NotOnlyDigits
    }
}