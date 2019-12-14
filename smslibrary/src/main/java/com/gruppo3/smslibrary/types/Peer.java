package com.gruppo3.smslibrary.types;

import androidx.annotation.NonNull;
import com.gruppo3.smslibrary.exceptions.InvalidAddressException;

/**
 * The Peer class represents a client that owns an univocal address.
 * @author Mattia Fanan, Giovanni Barca
 */
public class Peer {
    private static final String ADDRESS_MATCH_EXPR = "\\+?\\d{4,15}"; // To be valid must have (optional) '+' for country code and between 4 and 15 digits
    private String address;

    /**
     * Initializes a newly created Peer object so that has the same address as the argument.
     *
     * @param address String to assign to the peer address
     * @throws InvalidAddressException If an invalid address is passed
     */
    public Peer(@NonNull String address) throws InvalidAddressException {
        if (!isValidAddress(address)) {
            throw new InvalidAddressException();
        }

        this.address = address;
    }

    /**
     * Gets peer address.
     *
     * @return A String representing the peer address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Check if the passed address is valid.
     *
     * @param address Address to be checked
     * @return <code>True</code> if address is valid, <code>false</code> otherwise
     */
    private boolean isValidAddress(String address) {
        return address.matches(ADDRESS_MATCH_EXPR);
    }

    /**
     * Compares this peer to the specified object.
     *
     * @param obj The object to compare this Peer against
     * @return <code>True</code> if the given object represents a Peer equivalent to this peer, <code>false</code> otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof Peer))
            return false;

        Peer peer = (Peer)obj;

        return address.equals(peer.getAddress());
    }

    /**
     * Returns an hash code for this peer. The hash code for a Peer object is computed as <pre>31 * 7 * address.hashCode()</pre>
     *
     * @return An hash code value for this peer
     */
    @Override
    public int hashCode() {
        return 31 * 7 * address.hashCode();
    }
}
