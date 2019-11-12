package com.gruppo3.smsconnection.connection;

import com.gruppo3.smsconnection.connection.exception.InvalidDataException;
import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;

/**
 * @author Mattia Fanan
 * abstraction of Message
 * @param P message's peer type
 * @param D message's payload type
 */
public abstract class Message<P extends Peer,D extends PayloadData> {
    protected D data;
    protected P peer;

    /**
     * build the message
     * @param peer peer
     * @param data payload
     * @throws InvalidPeerException when invalid peer passed
     * @throws InvalidDataException when invalid payload passed
     */
    public Message(P peer,D data)throws InvalidPeerException, InvalidDataException {
        if(peer==null || !peer.isValid())
            throw new InvalidPeerException();
        if(data==null || !data.isValid())
            throw new InvalidDataException();
        this.data=data;
        this.peer=peer;
    }
    /**
     * @return D the payloadData contained in the message
     */
    public D getPayloadData(){ return data; }

    /**
     *@return P message's peer
     */
    public P getPeer(){ return peer; }

    /**
     * set message's payload if a valid one is passed
     * @param data message's payload
     * @return true if the message's payload is valid
     */
    public boolean setPayloadData(D data){
        if(!data.isValid())
            return false;
        this.data=data;
        return true;
    }

    /**
     * set message's peer if a valid one is passed
     * @param peer message's peer
     * @return true if the message's peer is valid
     */
    public boolean setPeer(P peer){
        if(!peer.isValid())
            return false;
        this.peer=peer;
        return true;
    }

    /**
     * method for check message's validity
     * @return true if is valid
     */
    public boolean isValid(){
        return peer.isValid()&& data.isValid();
    }
}

