package com.gruppo3.smsconnection.smsdatalink.message;


import androidx.annotation.NonNull;

import com.gruppo3.smsconnection.connection.AbstractStringMessage;
import com.gruppo3.smsconnection.connection.exception.InvalidMessageException;
import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;

/**
 * @author Mattia Fanan
 * data-link layer message
 */
public class SMSMessage extends AbstractStringMessage<SMSPeer> {

    public static final char controlStamp = '&';
    //used from SMSPayload to check if it's data can be stored entirely
    public static final int MAX_PAYLOAD_LENGTH = 160 - 2; //control stamp + # header payload separator

    /**
     * @param destination message's destination peer
     * @param source      message's source peer
     * @param payload     message's payload
     * @throws InvalidPeerException    when invalid peer found
     * @throws InvalidMessageException when invalid payload found
     */
    public SMSMessage(SMSPeer destination, SMSPeer source, String payload)
            throws InvalidPeerException, InvalidMessageException {
        super(destination, source, payload, false);
    }


    /**
     * get a SMSMessage from a passed lower layer payload
     *
     * @param sourceAddress source peer phone number
     * @param lowerLayerSDU sms PDU
     * @return SMSMessage if is possible to extract one from the SDU
     * @throws InvalidPeerException    when invalid peer found in sourceAddress
     * @throws InvalidMessageException when LowerLayerSDU is not suitable for build an SMSMessage
     */
    //if we can manipulate SmaMessage by pdu this method doesn't need peer address
    public static SMSMessage buildFromSDU(String sourceAddress, String lowerLayerSDU)
            throws InvalidPeerException, InvalidMessageException {

        //try build SMSPeer
        SMSPeer peer = new SMSPeer(sourceAddress);

        String header = lowerLayerSDU.substring(0, lowerLayerSDU.indexOf("#"));

        if (header.charAt(0) != controlStamp)
            throw new InvalidMessageException();

        String payload = lowerLayerSDU.substring(lowerLayerSDU.indexOf("#") + 1);

        return new SMSMessage(null, peer, payload);
    }

    /**
     * @return String representing data unit's properties
     */
    @Override
    public String toString() {
        String stringRep = "Message:";
        stringRep = stringRep + payload;

        if (destinationPeer != null)
            stringRep = stringRep + " ---Destination:" + destinationPeer.getAddress();

        if (sourcePeer != null)
            stringRep = stringRep + " ---Source:" + sourcePeer.getAddress();

        return stringRep;
    }

    /**
     * method that decides what is a valid data for the message
     *
     * @param data messages's data to validate
     * @return <code>true</code> if valid data is found
     */
    @Override
    protected boolean isValidData(@NonNull String data) {
        return data.length() <= MAX_PAYLOAD_LENGTH;
    }

    /**
     * @return header to add in the SDU
     */
    @Override
    protected String getToAddHeader() {
        return controlStamp + "";
    }
}
