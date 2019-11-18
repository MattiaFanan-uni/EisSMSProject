package com.gruppo3.smsconnection.netlayer;

import com.gruppo3.smsconnection.connection.Peer;
import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;

/**
 * @author Mattia Fanan
 */
public class NetPeer extends Peer<String> {

    /**
     * build the peer
     *
     * @param address
     * @throws InvalidPeerException if a non valid address is passed
     */
    public NetPeer(String address) throws InvalidPeerException {
        super(address);
    }

    @Override
    protected boolean isValidAddress(String address) {
        return address!=null && address!="";
    }
}
