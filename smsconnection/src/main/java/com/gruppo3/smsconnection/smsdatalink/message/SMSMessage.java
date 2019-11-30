package com.gruppo3.smsconnection.smsdatalink.message;


import androidx.annotation.NonNull;

import com.gruppo3.smsconnection.connection.AbstractByteMessage;
import com.gruppo3.smsconnection.connection.exception.InvalidMessageException;
import com.gruppo3.smsconnection.connection.exception.InvalidPayloadException;
import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;

import java.io.UnsupportedEncodingException;

/**
 * @author Mattia Fanan
 * data-link layer message
 */
public class SMSMessage extends AbstractByteMessage<SMSPeer> {

    public static final String controlStamp=Character.toString((char)0xfeff0298);//ʘ
    //used from SMSPayload to check if it's data can be stored entirely
    public static final int MAX_PAYLOAD_LENGTH=134- STRUCTURE.TOTAL_HEADER.getLength();//134(byte) = 67 utf-16

    /**
     *
     * @param destination   message's destination peer
     * @param source        message's source peer
     * @param payload       message's payload
     * @throws InvalidPeerException     when invalid peer found
     * @throws InvalidPayloadException  when invalid payload found
     */
    public SMSMessage(SMSPeer destination, SMSPeer source, byte[] payload)
            throws InvalidPeerException, InvalidPayloadException {
        super(destination,source,payload,false);
    }



    /**
     * get a SMSMessage from a passed lower layer payload
     * @param sourceAddress     source peer phone number
     * @param lowerLayerSDU     sms PDU
     * @return SMSMessage if is possible to extract one from the SDU
     * @throws InvalidPeerException     when invalid peer found in sourceAddress
     * @throws InvalidPayloadException  when LowerLayerSDU is not suitable for build an SMSMessage
     */
    //if we can manipulate SmaMessage by pdu this method doesn't need peer address
    public static SMSMessage buildFromSDU(String sourceAddress, byte[] lowerLayerSDU)
            throws InvalidPeerException,InvalidPayloadException,InvalidMessageException {

        int stampStartPosition=0;
        int payloadLengthStartPosition = stampStartPosition + STRUCTURE.STAMP.getLength();

        if(lowerLayerSDU.length< STRUCTURE.TOTAL_HEADER.getLength())
            throw new InvalidMessageException();

        //retrieve byte-encoded header's stamp
        byte[] byteStamp=new byte[STRUCTURE.STAMP.getLength()];
        System.arraycopy(lowerLayerSDU, stampStartPosition, byteStamp, 0, STRUCTURE.STAMP.getLength());

        try {
            //retrieve header's stamp
            String controlStamp = new String(byteStamp, "UTF-16");
            //check if retrieved stamp equals with the original
            if (controlStamp.compareTo(controlStamp) != 0)
                throw new InvalidMessageException();
        }
        catch (UnsupportedEncodingException e){}

        //retrieve byte-encoded payload length
        byte[] bytePayloadLength=new byte[STRUCTURE.PAYLOAD_LENGTH.getLength()];
        System.arraycopy(lowerLayerSDU, payloadLengthStartPosition,  bytePayloadLength, 0, STRUCTURE.PAYLOAD_LENGTH.getLength());

        //try decoding payload length
        try{

            //from min_max to 0_length
            short payloadLength=(short)(
                    (short)new String(bytePayloadLength, "UTF-16").charAt(0)-Short.MIN_VALUE
            );

            //retrieve data
            byte[] data=new byte[payloadLength];
            System.arraycopy(lowerLayerSDU, STRUCTURE.TOTAL_HEADER.getLength(), data , 0, payloadLength);

            return new SMSMessage(null,new SMSPeer(sourceAddress), data);
        }
        catch (UnsupportedEncodingException e){}
        //if this code is executed there is an error
        throw new InvalidMessageException();
    }

    /**
     *
     * @return String representing data unit's properties
     */
    @Override
    public String toString()
    {
        String stringRep="Message:";
        try{
            stringRep=stringRep+new String(payload,"UTF-16");
        }
        catch (UnsupportedEncodingException e){}

        if(destinationPeer!=null)
            stringRep=stringRep+" ---Destination:"+destinationPeer.getAddress();

        if(sourcePeer!=null)
            stringRep=stringRep+" ---Source:"+sourcePeer.getAddress();

        return stringRep;
    }

    /**
     * method that decides what is a valid data for the message
     *
     * @param data messages's data to validate
     * @return <code>true</code> if valid data is found
     */
    @Override
    protected boolean isValidData(@NonNull byte[] data) {
        return data.length<=MAX_PAYLOAD_LENGTH;
    }

    /**
     * @return header to add in the SDU
     */
    @Override
    protected byte[] getToAddHeader() {
        byte[] header = new byte[STRUCTURE.TOTAL_HEADER.getLength()];

        int stampStartPosition=0;
        int payloadLengthStartPosition = stampStartPosition + STRUCTURE.STAMP.getLength();

        //from 0_length to min_max
        short payloadLength = (short) (payload.length+Short.MIN_VALUE);


        try {

            byte[] stampBytes = controlStamp.getBytes("UTF-16");
            byte[] payloadLengthBytes = (new String(new char[]{(char) payloadLength})).getBytes("UTF-16");
            //stamp
            System.arraycopy(stampBytes, 0, header, stampStartPosition , STRUCTURE.STAMP.getLength());
            //payload length
            System.arraycopy(payloadLengthBytes, 0, header, payloadLengthStartPosition, STRUCTURE.PAYLOAD_LENGTH.getLength());

        } catch (Exception e) { }

        return header;
    }


    private enum STRUCTURE {
        TOTAL_HEADER(8),
        STAMP(4),
        PAYLOAD_LENGTH(4);

        private int length;

        STRUCTURE(int value){length=value;}

        public int getLength() {
            return this.length;
        }

    }
}
