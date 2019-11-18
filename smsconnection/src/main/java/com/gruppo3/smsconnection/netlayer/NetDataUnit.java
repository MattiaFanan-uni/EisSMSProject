package com.gruppo3.smsconnection.netlayer;

import com.gruppo3.smsconnection.connection.DataUnit;
import com.gruppo3.smsconnection.connection.exception.InvalidDataException;
import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;

/**
 * @author Mattia Fanan
 */
public class NetDataUnit extends DataUnit<NetPeer,NetMessage> {
    /**
     * build the message
     *
     * @param peer    peer
     * @param message payload
     * @throws InvalidPeerException when invalid peer passed
     * @throws InvalidDataException when invalid payload passed
     */
    public NetDataUnit(NetPeer peer, NetMessage message) throws InvalidPeerException, InvalidDataException {
        super(peer, message);
    }
}
