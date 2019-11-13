package com.gruppo3.smsconnection.connection;

import com.gruppo3.smsconnection.connection.exception.InvalidDataException;
import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;

/**
 * @author Mattia Fanan
 * abstraction of DataUnit
 * @param P message's peer type
 * @param M message's payload type
 */
public abstract class DataUnit<P extends Peer,M extends Message> {
    protected M message;
    protected P peer;

    /**
     * build the message
     * @param peer peer
     * @param message payload
     * @throws InvalidPeerException when invalid peer passed
     * @throws InvalidDataException when invalid payload passed
     */
    public DataUnit(P peer, M message)throws InvalidPeerException, InvalidDataException {
        if(peer==null || !peer.isValid())
            throw new InvalidPeerException();
        if(message==null || !message.isValid())
            throw new InvalidDataException();
        this.message=message;
        this.peer=peer;
    }
    /**
     * @return D the payloadData contained in the message
     */
    public M getMessage(){ return message; }

    /**
     *@return P message's peer
     */
    public P getPeer(){ return peer; }

    /**
     * set message's payload if a valid one is passed
     * @param message message's payload
     * @return true if the message's payload is valid
     */
    public boolean setMessage(M message){
        if(!message.isValid())
            return false;
        this.message=message;
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
        return peer.isValid()&& message.isValid();
    }


}

