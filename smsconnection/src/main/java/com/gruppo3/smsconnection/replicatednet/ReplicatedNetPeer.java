package com.gruppo3.smsconnection.replicatednet;

import com.gruppo3.smsconnection.connection.Peer;
import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;

/**
 * @author  Mattia Fanan
 * net layer message's peer
 * it's address to be valid must be 16 bytes [128 bit] long
 */
public class ReplicatedNetPeer implements Peer<byte[]> {

    public static final int LENGTH=16;//sha_1-->128 bit
    byte[] address;
    public ReplicatedNetPeer(byte[] address) throws InvalidPeerException{
        if(!isValidData(address))
            throw new InvalidPeerException();
        this.address=address;
    }

    private boolean isValidData(byte[] address)
    {
        return address.length==LENGTH;
    }

    /**
     * return the peer's address
     *
     * @return peer's address
     */
    @Override
    public byte[] getAddress() {
        return address.clone();
    }
}
