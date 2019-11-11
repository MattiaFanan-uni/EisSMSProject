package com.gruppo3.smsconnection.smsdatalink;


import com.gruppo3.smsconnection.connection.Message;

public class SMSMessage extends Message<String,SMSPeer> {
    private static final int MAX_LENGHT=160;

    /**
     * Builds and returns an SMS message given a valid SMSPeer and a valid message to send
     */
    public SMSMessage(SMSPeer destination, String message) {
        peer = destination;
        data = message;
    }

    @Override
    public void setData(String data) {//maybe boolean
        if (data!=null && data.length()<=MAX_LENGHT)
            this.data=data;
    }

    @Override
    public void setPeer(SMSPeer peer) {//maybe boolean
        if (peer.isValid())
            this.peer=peer;
    }

    @Override
    protected boolean hasValidData() {
        return data!=null && data.length()<=MAX_LENGHT;
    }


    /**
     * Adds a string header before the message
     */
    public boolean addHeader(String header) {
        if(data.length()+header.length()<MAX_LENGHT) {
            data = header + data;
            return true;
        }
        return false;
    }

    /**
     * Helper function to write the message as a string
     */
    public String toString() {
        return "SMSPeer: " + getData() + ", SMSMessage: " + getPeer();
    }

}
