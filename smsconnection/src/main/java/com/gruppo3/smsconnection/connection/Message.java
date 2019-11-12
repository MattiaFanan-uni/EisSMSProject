package com.gruppo3.smsconnection.connection;

import com.gruppo3.smsconnection.connection.exceptions.InvalidDataException;
import com.gruppo3.smsconnection.connection.exceptions.InvalidPeerException;

/**
 * Interface to implement to create a new Message type
 */
public class Message<P extends Peer,D extends PayloadData> {
    protected D data;
    protected P peer;

    public Message(P peer,D data)throws InvalidPeerException, InvalidDataException {
        if(peer==null || !peer.isValid())
            throw new InvalidPeerException();
        if(data==null || !data.isValid())
            throw new InvalidDataException();
        this.data=data;
        this.peer=peer;
    }
    /**
     * Returns the data contained in the message
     */
    public D getPayloadData(){ return data; }

    /**
     * Returns the Peer of the message
     */
    public P getPeer(){ return peer; }


    public boolean setPayloadData(D data){
        if(!data.isValid())
            return false;
        this.data=data;
        return true;
    }

    public boolean setPeer(P peer){
        if(!peer.isValid())
            return false;
        this.peer=peer;
        return true;
    }

    public boolean isValid(){
        return peer.isValid()&& data.isValid();
    }
}

