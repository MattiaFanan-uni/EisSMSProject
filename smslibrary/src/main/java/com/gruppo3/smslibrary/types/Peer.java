package com.gruppo3.smslibrary.types;

import androidx.annotation.NonNull;

import com.gruppo3.smslibrary.util.Util;

/**
 * The Peer class represents a client that owns a phone number and a nodeId (optional).
 *
 * @author Mattia Fanan. Reviewed by Giovanni Barca. Corrected by Giovanni Barca.
 * @version 1
 */
public class Peer {
    private static final String ADDRESS_MATCH_EXPR = "\\+?\\d{4,15}"; // To be valid must have (optional) '+' for country code and between 4 and 15 digits
    private String phoneNumber;
    private String nodeId; // Sha1 encrypted

    /**
     * Initializes a newly created Peer object so that has the same phone number as the argument.<br>
     * A phone number, to be balid, must have between 4 and 15 digits (the country code is optional).
     *
     * @param phoneNumber String to assign to the peer phone number
     * @throws IllegalArgumentException If an invalid phone number is passed
     */
    public Peer(@NonNull String phoneNumber) throws IllegalArgumentException {
        if (!isValidAddress(phoneNumber)) {
            throw new IllegalArgumentException("An invalid phone number format was passed.");
        }

        this.phoneNumber = phoneNumber;
        this.nodeId = null;
    }

    /**
     * Initializes a newly created Peer object so that has the same phone number and node ID as the argument.
     *
     * @param phoneNumber String to assign to the peer phone number
     * @param nodeId String containing the sha1 hash of the phone number argument
     * @throws IllegalArgumentException If an invalid phoneNumber or incompatible node ID is passed
     */
    public Peer(@NonNull String phoneNumber, String nodeId) throws IllegalArgumentException {
        if (!isValidAddress(phoneNumber)) {
            throw new IllegalArgumentException("An invalid phone number format was passed.");
        }
        else if (!isValidNodeId(phoneNumber, nodeId)) {
            throw new IllegalArgumentException("An invalid node ID was passed.");
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

    /**
     * Check if the passed phone number is valid.
     *
     * @param phoneNumber String representing the phone number to be checked
     * @return <code>True</code> if phone number is valid, <code>false</code> otherwise
     */
    private boolean isValidAddress(@NonNull String phoneNumber) {
        return phoneNumber.matches(ADDRESS_MATCH_EXPR);
    }

    /**
     * Check if given nodeId is compatible to the phone number.
     * @param phoneNumber String representing the phone number to be checked
     * @param nodeId String representing the node ID to compare (sha1 encrypted)
     * @return <code>True</code> if node id is valid, <code>false</code> otherwise
     */
    private boolean isValidNodeId(String phoneNumber, @NonNull String nodeId) {
        return Util.sha1Hash(phoneNumber).equals(nodeId);
    }
}
