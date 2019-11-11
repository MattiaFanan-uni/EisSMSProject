package com.gruppo3.smsconnection.connection;

import java.util.Optional;

/**
 * Interface to implement to create a new Message type
 */
public abstract class Message<D,P extends Peer> {
    protected D data;
    protected P peer;
    /**
     * Returns the data contained in the message
     */
    public Optional<D> getData(){
        return Optional.ofNullable(data);
    }

    /**
     * Returns the Peer of the message
     */
    public Optional<P> getPeer(){
        return peer.getAddress();
    }

    public abstract void setData(D data);

    public abstract void setPeer(P peer);

    protected abstract boolean hasValidData();

    public boolean isValid(){
        return peer.isValid()&& hasValidData();
    }
}

