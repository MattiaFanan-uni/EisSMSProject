package com.gruppo3.smsconnection.smsdatalink;


import androidx.annotation.NonNull;

import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;
import com.gruppo3.smsconnection.connection.Peer;
/**
 * @author Mattia Fanan
 * sms implementation of Peer
 */
public class SMSPeer implements Peer<String> {
    public static final String MATCH_EXPR="\\+?\\d{4,15}";
    private String address;

    /**
     * build smsPeer
     * @param address
     * @throws InvalidPeerException if a non valid address is passed
     */
    public SMSPeer(@NonNull String address) throws InvalidPeerException {
        if(!isValidAddress(address))
            throw new InvalidPeerException();
        this.address=address;
    }

    /**
     * method that decides what is a valid address for the peer
     * @param address to validate
     * @return true if address matches MaTCH_EXPR
     */

    private boolean isValidAddress(String address) {
        return address.matches(MATCH_EXPR);
    }

    /**
     * return the peer's address
     *
     * @return peer's address
     */
    @Override
    @NonNull
    public String getAddress() {
        return address;
    }
}
