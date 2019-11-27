package com.gruppo3.smsconnection.connection;

/**
 * ProtocolDataUnit interface
 * interface for ProtocolDataUnit
 * @author Mattia Fanan
 *
 * @param P ProtocolControlInformation data-type
 * @param D ServiceDataUnit data-type
 */
public interface ProtocolDataUnit<P extends Peer,T>{

    /**
     * @return source peer in the ProtocolControlInformation
     */
    P getSourcePeer();

    /**
     * @return destination peer in the ProtocolControlInformation
     */
    P getDestinationPeer();

    /**
     * @return ProtocolDataUnit's ServiceDataUnit to pass in the next Protocol
     */
    T getSDU();

    T getData();
}

