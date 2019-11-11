package com.gruppo3.smsconnection.connection;


import com.gruppo3.smsconnection.connection.Exceptions.InvalidPeerException;

/**
 * abstarcton of peer class
 * @param T type of address
 */
public abstract class Peer<T> {
    protected T address;

    public Peer(T address)throws InvalidPeerException {
        if(!isValidAddress(address))
            throw new InvalidPeerException();
        this.address=address;
    }

    public T getAddress(){
        return address;
    }

    public boolean setAddress(T address){
        if(!isValidAddress(address))
            return false;
        this.address=address;
        return true;
    }

    protected abstract boolean isValidAddress(T address);

    public boolean isValid(){
        return isValidAddress(address);
    }
}