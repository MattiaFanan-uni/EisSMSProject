package com.gruppo3.smsconnection.smsdatalink.message;


import androidx.annotation.NonNull;

import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;
import com.gruppo3.smsconnection.connection.Peer;
/**
 * @author Mattia Fanan
 * data-link layer sms's peer
 * it's address to be valid must have optional + for country code and from 4 to 15 digits
 */
public class SMSPeer implements Peer<String> {
    public static final String MATCH_EXPR="\\+?\\d{4,15}";
    private String address;

    /**
     *
     * @param address   peer's address
     * @throws InvalidPeerException if a non valid address is found
     */
    public SMSPeer(@NonNull String address) throws InvalidPeerException {
        if(!isValidAddress(address))
            throw new InvalidPeerException();
        this.address=address;
    }

    /**
     * method that decides what is a valid address for the peer
     * @param address   peer's address to validate
     * @return <code>true</code> if address matches MATCH_EXPR
     */

    private boolean isValidAddress(String address) {
        return address.matches(MATCH_EXPR);
    }

    /**
     * @return peer's address
     */
    @Override
    @NonNull
    public String getAddress() {
        return address;
    }
}
