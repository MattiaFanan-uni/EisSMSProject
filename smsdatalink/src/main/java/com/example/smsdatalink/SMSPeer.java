package com.example.smsdatalink;

public class SMSPeer implements Peer {
    private String address;

    /**
     * Creates and returns an SMSPeer given a valid destination
     */
    public SMSPeer(String destination) {
        this.address = destination;
    }

    /**
     * Returns the SMSPeer address if valid null if not
     */
    public String getAddress() {
        return isValid()? address : null;
    }

    /**
     * Helper function to write the SMSPeer as a string
     */
    public String toString() {
        return address;
    }

    /**
     * Returns true if the address is empty
     */
    public boolean isEmpty() {
        return address.equals("");
    }

    /**
     * Returns true if the SMSPeer is valid
     */
    public boolean isValid() {
        return !isEmpty() && address.matches("[0-9]{4,15}");//test if 16 is allowed
    }
}
