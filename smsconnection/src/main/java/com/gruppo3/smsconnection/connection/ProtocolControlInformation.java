package com.gruppo3.smsconnection.connection;

/**
 * ProtocolControlInformation abstract class
 * @author Mattia Fanan
 *
 * @param <P> Peer data-type
 * @param <D> ServiceDataUnit data data-type
 */
public interface ProtocolControlInformation<P extends Peer,D>{

    /**
     * @return header to add in the ServiceDataUnit
     */
    D getToAddHeader();

    /**
     * @return source peer in the ProtocolControlInformation
     */
    P getSourcePeer();

    /**
     * @return destination peer in the ProtocolControlInformation
     */
    P getDestinationPeer();

}
