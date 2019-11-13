package com.gruppo3.smsconnection.smsdatalink;


import com.gruppo3.smsconnection.connection.DataUnit;
import com.gruppo3.smsconnection.connection.exception.InvalidDataException;
import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;

/**
 * @author Mattia Fanan
 * sms implementation of abstract message
 */
public class SMSDataUnit extends DataUnit<SMSPeer, SMSMessage> {

    /**
     * build the smsMessage
     * @param peer
     * @param message
     * @throws InvalidPeerException if a not valid peer is passed
     * @throws InvalidDataException if a not valid payload is passed
     */
    public SMSDataUnit(SMSPeer peer, SMSMessage message) throws InvalidPeerException, InvalidDataException {
        super(peer,message);
    }

    /**
     * adds a header to the payload
     * @param header Sring to add
     * @return true if the headed is correctly added
     */
    public boolean addHeader(String header) {
        return message.setData(message.getData()+header);
    }

    /**
     * get a string rappresentation of the message
     * @return String rappresenting the message
     */
    public String toString() {
        return "SMSPeer: " + getPeer().getAddress() + ", SMSDataUnit: " + getMessage().getData();
    }

}
