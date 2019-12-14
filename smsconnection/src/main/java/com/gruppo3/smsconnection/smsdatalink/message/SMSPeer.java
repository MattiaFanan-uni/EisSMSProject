package com.gruppo3.smsconnection.smsdatalink.message;


import androidx.annotation.NonNull;

import com.gruppo3.smsconnection.connection.Peer;
import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;

/**
 * @author Mattia Fanan
 * data-link layer sms's peer
 * it's address to be valid must have optional + for country code and from 4 to 15 digits
 */
public class SMSPeer implements Peer<String> {
    public static final String MATCH_EXPR = "\\+?\\d{4,15}";
    private String address;

    /**
     * @param address peer's address
     * @throws InvalidPeerException if a non valid address is found
     */
    public SMSPeer(@NonNull String address) throws InvalidPeerException {
        if (!isValidAddress(address))
            throw new InvalidPeerException();
        this.address = address;
    }

    /**
     * method that decides what is a valid address for the peer
     *
     * @param address peer's address to validate
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

    /**
     * Indicates whether some other object is "equal to" this one
     *
     * @param o The reference object with which to compare.
     * @return <code>true</code> if this object is the same as the obj argument; <code>false</code>otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof SMSPeer))
            return false;

        SMSPeer peer = (SMSPeer) o;

        return address.equals(peer.getAddress());
    }

    /**
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return 31 * 7 * address.hashCode();
    }
}
