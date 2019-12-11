package com.gruppo3.smsconnection.connection;

import androidx.annotation.NonNull;

import com.gruppo3.smsconnection.connection.exception.InvalidMessageException;
import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;

public abstract class AbstractStringMessage<P extends Peer> implements Message<P, String> {

    protected P sourcePeer;
    protected P destinationPeer;
    protected String payload;

    /**
     * @param destination message's destination peer
     * @param source      message's source peer
     * @param payload     message's payload
     * @throws InvalidPeerException    when invalid peer found
     * @throws InvalidMessageException when invalid payload found
     */
    public AbstractStringMessage(P destination, P source, String payload, boolean mustHaveBothPeers)
            throws InvalidPeerException, InvalidMessageException {

        if (!isValidData(payload))
            throw new InvalidMessageException();

        this.payload = payload;

        //they can't be both null
        if (destination == null && source == null)
            throw new InvalidPeerException();

        if (mustHaveBothPeers && destination == null)
            throw new InvalidPeerException();

        destinationPeer = destination;

        if (mustHaveBothPeers && source == null)
            throw new InvalidPeerException();

        sourcePeer = source;
    }

    /**
     * method that decides what is a valid data for the message
     *
     * @param data messages's data to validate
     * @return <code>true</code> if valid data is found
     */
    protected abstract boolean isValidData(@NonNull String data);

    /**
     * @return messages's data
     */
    @Override
    public String getData() {
        return payload;
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
    public String getSDU() {

        return getToAddHeader() + "#" + getData();
    }

    /**
     * @return header to add in the SDU
     */
    protected abstract String getToAddHeader();


}
