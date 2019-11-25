package com.gruppo3.smsconnection.smsdatalink;


import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;
import com.gruppo3.smsconnection.connection.Peer;
/**
 * @author Mattia Fanan
 * sms implementation of Peer
 */
public class SMSPeer extends Peer<String> {
    public static final String MATCH_EXPR="\\+?\\d{4,15}";

    /**
     * build smsPeer
     * @param address
     * @throws InvalidPeerException if a non valid address is passed
     */
    public SMSPeer(String address) throws InvalidPeerException {
        super(address);
    }

    /**
     * method that decides what is a valid address for the peer
     * @param address to validate
     * @return true if address matches MaTCH_EXPR
     */
    @Override
    protected boolean isValidAddress(String address) {
        return address.matches(MATCH_EXPR);
    }
}
