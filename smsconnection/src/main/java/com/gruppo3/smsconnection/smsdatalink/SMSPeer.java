package com.gruppo3.smsconnection.smsdatalink;


import com.gruppo3.smsconnection.connection.Exceptions.InvalidPeerException;
import com.gruppo3.smsconnection.connection.Peer;

public class SMSPeer extends Peer<String> {
    private static final String METCH_EXPR="[0-9]{4,15}";

    public SMSPeer(String address) throws InvalidPeerException {
        super(address);
    }

    @Override
    protected boolean isValidAddress(String address) {
        return address.matches(METCH_EXPR);
    }
}
