package com.gruppo3.smsconnection.smsdatalink;

import com.gruppo3.smsconnection.connection.ProtocolControlInformation;
import com.gruppo3.smsconnection.connection.exception.InvalidHeaderException;
import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;

import java.io.Serializable;

/**
 * @author Mattia Fanan
 * used to store info about the SMSDataUnit
 */
public class SMSProtocolControlInformation implements ProtocolControlInformation<SMSPeer,byte[]>{
    public static final String controlStamp=Character.toString((char)0xfeff0298);//ʘ
    public static final int LENGTH=8;//stamp=utf-16 + unsigned short payload length(2 bytes)+ 2 endString
    public static final int MAX_PAYLOAD_LENGTH=134-LENGTH;//134(byte) = 67 utf-16

    private SMSPeer sourcePeer;
    private SMSPeer destinationPeer;
    private short payloadLength;

    /**
     * build the header
     * only one peer could be null
     * @param destination SMSPeer destination peer
     * @param source SMSPeer source peer
     * @throws InvalidHeaderException when all the peers are null
     */
    public SMSProtocolControlInformation(SMSPeer destination, SMSPeer source, SMSServiceDataUnit payload)
            throws InvalidPeerException
    {

        //they can't be both null
        if(destination==null && source==null)
            throw new InvalidPeerException();

        if(destination!=null)
                destinationPeer=destination;

        if(source!=null)
                sourcePeer=source;

        //scale 0_length to Minval_Maxval
        payloadLength=(short)(payload.getSize()+Short.MIN_VALUE);
    }


    /**
     * @return header to add in the ServiceDataUnit
     */
    @Override
    public byte[] getToAddHeader() {
        byte[] header = new byte[LENGTH];
        char pl = (char) payloadLength;
        try {
            byte[] stampBytes = controlStamp.getBytes("UTF-16");
            byte[] payloadLengthBytes = (new String(new char[]{(char) payloadLength})).getBytes("UTF-16");

            System.arraycopy(stampBytes, 0, header, 0, stampBytes.length);
            System.arraycopy(payloadLengthBytes, 0, header, stampBytes.length, payloadLengthBytes.length);

        } catch (Exception e) { }

        return header;
    }

    /**
     * @return source peer in the ProtocolControlInformation
     */
    @Override
    public SMSPeer getSourcePeer() {
        return sourcePeer;
    }

    /**
     * @return destination peer in the ProtocolControlInformation
     */
    @Override
    public SMSPeer getDestinationPeer() {
        return destinationPeer;
    }
}
