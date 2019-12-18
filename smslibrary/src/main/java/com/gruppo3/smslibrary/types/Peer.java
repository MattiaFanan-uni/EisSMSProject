package com.gruppo3.smslibrary.types;

import androidx.annotation.NonNull;
import com.gruppo3.smslibrary.exceptions.InvalidAddressException;
import com.gruppo3.smslibrary.util.Util;

/**
 * The Peer class represents a client that owns a phone number and a nodeId (if assigned).
 * @author Mattia Fanan, Giovanni Barca
 */
public class Peer {
    private static final String ADDRESS_MATCH_EXPR = "\\+?\\d{4,15}"; // To be valid must have (optional) '+' for country code and between 4 and 15 digits
    private String phoneNumber;
    private String nodeId; // Sha1 encrypted in hexadecimal

    /**
     * Initializes a newly created Peer object so that has the same phone number as the argument.
     *
     * @param phoneNumber String to assign to the peer phone number
     * @throws InvalidAddressException If an invalid phone number is passed
     */
    public Peer(@NonNull String phoneNumber) throws InvalidAddressException {
        if (!isValidAddress(phoneNumber)) {
            throw new InvalidAddressException();
        }

        this.phoneNumber = phoneNumber;
        this.nodeId = null;
    }

    /**
     * Initializes a newly created Peer object so that has the same phone number and node ID as the argument.
     *
     * @param phoneNumber String to assign to the peer phone number
     * @param nodeId String to assign to the peer node ID
     * @throws InvalidAddressException If an invalid phoneNumber or incompatible node ID is passed
     */
    public Peer(@NonNull String phoneNumber, String nodeId) throws InvalidAddressException {
        if (!isValidAddress(phoneNumber) || !isValidNodeId(phoneNumber, nodeId)) {
            throw new InvalidAddressException();
        }

        this.phoneNumber = phoneNumber;
        this.nodeId = nodeId;
    }

    /**
     * Gets peer phone number.
     *
     * @return A String representing the peer phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Gets peer node ID.
     *
     * @return A String representing the peer node ID
     */
    public String getNodeId() {
        return nodeId;
    }

    /**
     * Check if the passed phone number is valid.
     *
     * @param phoneNumber String representing the phone number to be checked
     * @return <code>True</code> if phone number is valid, <code>false</code> otherwise
     */
    private boolean isValidAddress(String phoneNumber) {
        return phoneNumber.matches(ADDRESS_MATCH_EXPR);
    }

    /**
     * Check if given nodeId is compatible to the phone number.
     * @param phoneNumber String representing the phone number to be checked
     * @param nodeId String representing the node ID to compare (sha1 encrypted)
     * @return <code>True</code> if node id is valid, <code>false</code> otherwise
     */
    private boolean isValidNodeId(String phoneNumber, String nodeId) {
        return Util.sha1Hash(phoneNumber).equals(nodeId);
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

        return phoneNumber.equals(peer.getPhoneNumber()) && phoneNumber.equals(peer.getNodeId());
    }
}
