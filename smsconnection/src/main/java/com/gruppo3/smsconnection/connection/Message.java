package com.gruppo3.smsconnection.connection;

/**
 * Message interface
 * interface for Message
 *
 * @param <P> Peer data-type
 * @param <T> Payload data-type
 * @author Mattia Fanan
 */
public interface Message<P extends Peer, T> {

    /**
     * @return source peer in the Header
     */
    P getSourcePeer();

    /**
     * @return destination peer in the Header
     */
    P getDestinationPeer();

    /**
     * @return Message's Payload to pass in the next Protocol
     */
    T getSDU();

    T getData();
}

