package com.gruppo3.smsconnection.connection;


import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;

/**
 * @author Mattia Fanan
 * abstarcton of peer class
 * @param T type of address
 */
public interface Peer<T> {

    /**
     * return the peer's address
     * @return peer's address
     */
    public T getAddress();
}