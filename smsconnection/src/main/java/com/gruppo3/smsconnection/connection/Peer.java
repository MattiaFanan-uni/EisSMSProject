package com.gruppo3.smsconnection.connection;


import java.io.Serializable;


public interface Peer<T> extends Serializable {

    /**
     * return the peer's address
     *
     * @return peer's address
     */
    public T getAddress();
}