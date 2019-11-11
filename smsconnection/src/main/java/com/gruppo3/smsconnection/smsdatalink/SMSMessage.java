package com.gruppo3.smsconnection.smsdatalink;


import com.gruppo3.smsconnection.connection.exceptions.InvalidDataException;
import com.gruppo3.smsconnection.connection.exceptions.InvalidPeerException;
import com.gruppo3.smsconnection.connection.Message;

public class SMSMessage extends Message<SMSPeer,SMSPayloadData> {


    public SMSMessage(SMSPeer peer,SMSPayloadData data) throws InvalidPeerException, InvalidDataException {
        super(peer,data);
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
