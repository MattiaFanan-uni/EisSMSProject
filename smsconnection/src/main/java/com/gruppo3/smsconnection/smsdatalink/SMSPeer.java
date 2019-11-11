package com.gruppo3.smsconnection.smsdatalink;


import com.gruppo3.smsconnection.connection.Peer;

public class SMSPeer extends Peer<String> {


    /**
     * Creates and returns an SMSPeer given a valid destination
     */
    public SMSPeer(String destination){
        super(destination);
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
        return !isEmpty() && address.matches("[0-9]{4,15}");
    }
}
