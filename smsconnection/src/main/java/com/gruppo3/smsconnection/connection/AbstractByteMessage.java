package com.gruppo3.smsconnection.connection;

import androidx.annotation.NonNull;

import com.gruppo3.smsconnection.connection.Message;
import com.gruppo3.smsconnection.connection.Peer;
import com.gruppo3.smsconnection.connection.exception.InvalidPayloadException;
import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;

public abstract class AbstractByteMessage<P extends Peer> implements Message<P,byte[]> {

    protected P sourcePeer;
    protected P destinationPeer;
    protected byte[] payload;

    /**
     *
     * @param destination   message's destination peer
     * @param source        message's source peer
     * @param payload       message's payload
     * @throws InvalidPeerException     when invalid peer found
     * @throws InvalidPayloadException  when invalid payload found
     */
    public AbstractByteMessage(P destination, P source, byte[] payload, boolean mustHaveBothPeers)
            throws InvalidPeerException, InvalidPayloadException {

        if(!isValidData(payload))
            throw new InvalidPayloadException();

        this.payload =payload;

        //they can't be both null
        if(destination==null && source==null)
            throw new InvalidPeerException();

        if(mustHaveBothPeers && destination==null)
            throw new InvalidPeerException();

        destinationPeer=destination;

        if(mustHaveBothPeers && source==null)
            throw new InvalidPeerException();

        sourcePeer=source;
    }

    /**
     * method that decides what is a valid data for the message
     * @param data messages's data to validate
     * @return <code>true</code> if valid data is found
     */
    protected abstract boolean isValidData(@NonNull byte[] data);

    /**
     * @return messages's data
     */
    @Override
    public byte[] getData(){
        return payload.clone();
    }


    /**
     * @return source peer
     */
    @Override
    public P getSourcePeer() {
        return sourcePeer;
    }

    /**
     * @return destination peer
     */
    @Override
    public P getDestinationPeer() {
        return destinationPeer;
    }

    /**
     * @return Message's Payload to pass in the lower level Protocol
     */
    @Override
    public byte[] getSDU() {

        byte[] header=getToAddHeader();

        byte[] toReturnSDU=new byte[header.length + payload.length];

        System.arraycopy(getToAddHeader(), 0, toReturnSDU, 0, header.length);
        System.arraycopy(payload,0 , toReturnSDU , header.length , payload.length);

        return toReturnSDU;
    }

    /**
     * @return header to add in the SDU
     */
    protected abstract byte[] getToAddHeader();




}
