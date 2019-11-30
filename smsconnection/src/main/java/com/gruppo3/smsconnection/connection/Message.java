package com.gruppo3.smsconnection.connection;

/**
 * Message interface
 * interface for Message
 * @author Mattia Fanan
 *
 * @param P Header data-type
 * @param D Payload data-type
 */
public interface Message<P extends Peer,T>{

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

