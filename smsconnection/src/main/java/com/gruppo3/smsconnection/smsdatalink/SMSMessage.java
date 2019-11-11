package com.gruppo3.smsconnection.smsdatalink;


import com.gruppo3.smsconnection.connection.Exceptions.InvalidDataException;
import com.gruppo3.smsconnection.connection.Exceptions.InvalidPeerException;
import com.gruppo3.smsconnection.connection.Message;

public class SMSMessage extends Message<SMSPayloadData,SMSPeer> {


    public SMSMessage(SMSPayloadData data, SMSPeer peer) throws InvalidPeerException, InvalidDataException {
        super(data, peer);
    }

    /**
     * Adds a string header before the message
     */
    public boolean addHeader(String header) {
        return data.setData(data.getData()+header);
    }

    /**
     * Helper function to write the message as a string
     */
    public String toString() {
        return "SMSPeer: " + getPayloadData().getData() + ", SMSMessage: " + getPeer().getAddress();
    }

}
