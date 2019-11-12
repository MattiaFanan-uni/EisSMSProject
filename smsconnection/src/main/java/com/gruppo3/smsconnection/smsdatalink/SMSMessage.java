package com.gruppo3.smsconnection.smsdatalink;


import com.gruppo3.smsconnection.connection.exception.InvalidDataException;
import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;
import com.gruppo3.smsconnection.connection.Message;
/**
 * @author Mattia Fanan
 * sms implementation of abstract message
 */
public class SMSMessage extends Message<SMSPeer,SMSPayloadData> {

    /**
     * build the smsMessage
     * @param peer
     * @param data
     * @throws InvalidPeerException if a not valid peer is passed
     * @throws InvalidDataException if a not valid payload is passed
     */
    public SMSMessage(SMSPeer peer,SMSPayloadData data) throws InvalidPeerException, InvalidDataException {
        super(peer,data);
    }

    /**
     * adds a header to the payload
     * @param header Sring to add
     * @return true if the headed is correctly added
     */
    public boolean addHeader(String header) {
        return data.setData(data.getData()+header);
    }

    /**
     * get a string rappresentation of the message
     * @return String rappresenting the message
     */
    public String toString() {
        return "SMSPeer: " + getPayloadData().getData() + ", SMSMessage: " + getPeer().getAddress();
    }

}
