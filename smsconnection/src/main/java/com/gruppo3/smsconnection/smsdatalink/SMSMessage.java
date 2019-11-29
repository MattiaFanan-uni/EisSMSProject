package com.gruppo3.smsconnection.smsdatalink;


import com.gruppo3.smsconnection.connection.Message;
import com.gruppo3.smsconnection.connection.exception.InvalidMessageException;
import com.gruppo3.smsconnection.connection.exception.InvalidPayloadException;
import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;

import java.io.UnsupportedEncodingException;

/**
 * @author Mattia Fanan
 * data-link level message
 */
public class SMSMessage implements Message<SMSPeer,byte[]> {

    public static final String controlStamp=Character.toString((char)0xfeff0298);//ʘ
    //used from SMSPayload to check if it's data can be stored entirely
    public static final int MAX_PAYLOAD_LENGTH=134- STRUCTURE.TOTAL_HEADER.getLength();//134(byte) = 67 utf-16

    private SMSPeer sourcePeer;
    private SMSPeer destinationPeer;
    SMSPayload payload;

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

        this.payload =new SMSPayload(payload);

        //they can't be both null
        if(destination==null && source==null)
            throw new InvalidPeerException();

        if(destination!=null)
            destinationPeer=destination;

        if(source!=null)
            sourcePeer=source;
    }

    /**
     * get a SMSMessage from a passed lower layer payload
     * @param sourceAddress     source peer phone number
     * @param LowerLayerSDU     sms PDU
     * @return SMSMessage if is possible to extract one from the SDU
     * @throws InvalidPeerException     when invalid peer found in sourceAddress
     * @throws InvalidPayloadException  when LowerLayerSDU is not suitable for build an SMSMessage
     */
    //if we can manipulate SmaMessage by pdu this method doesn't need peer address
    public static SMSMessage buildFromSDU(String sourceAddress, byte[] LowerLayerSDU)
            throws InvalidPeerException,InvalidPayloadException,InvalidMessageException {
        //retrieve byte-encoded header's stamp
        byte[] byteStamp=new byte[STRUCTURE.STAMP.getLength()];
        System.arraycopy(LowerLayerSDU, 0, byteStamp, 0, byteStamp.length);
        //retrieve byte-encoded payload length
        byte[] bytePayloadLength=new byte[STRUCTURE.PAYLOAD_LENGTH.getLength()];
        System.arraycopy(LowerLayerSDU, byteStamp.length,  bytePayloadLength, 0, bytePayloadLength.length);

        //try decoding payload length
        try{
            //from min_max to 0_length
            short payloadLength=(short)(
                    (short)new String(bytePayloadLength, "UTF-16").charAt(0)-Short.MIN_VALUE
            );

            //retrieve header's stamp
            String controlStamp=new String(byteStamp, "UTF-16");
            //check if retrieved stamp equals with the original
            if(controlStamp.compareTo(controlStamp)!=0)
                throw new InvalidMessageException();

            //retrieve data
            byte[] byteData=new byte[payloadLength];
            System.arraycopy(LowerLayerSDU, STRUCTURE.TOTAL_HEADER.getLength(), byteData , 0, payloadLength);

            return new SMSMessage(null,new SMSPeer(sourceAddress),byteData);
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
            stringRep=stringRep+new String(payload.getData(),"UTF-16");
        }
        catch (UnsupportedEncodingException e){}

        if(destinationPeer!=null)
            stringRep=stringRep+" ---Destination:"+destinationPeer.getAddress();

        if(sourcePeer!=null)
            stringRep=stringRep+" ---Source:"+sourcePeer.getAddress();

        return stringRep;
    }

    /**
     * @return source peer
     */
    @Override
    public SMSPeer getSourcePeer() {
        return sourcePeer;
    }

    /**
     * @return destination peer
     */
    @Override
    public SMSPeer getDestinationPeer() {
        return destinationPeer;
    }

    /**
     * @return Message's Payload to pass in the lower level Protocol
     */
    @Override
    public byte[] getSDU() {
        int SDULength=+payload.getData().length;
        byte[] toReturnSDU=new byte[STRUCTURE.TOTAL_HEADER.getLength()+SDULength];

        System.arraycopy(getToAddHeader(), 0, toReturnSDU, 0, STRUCTURE.TOTAL_HEADER.getLength());
        System.arraycopy(payload.getData(),0 , toReturnSDU , STRUCTURE.TOTAL_HEADER.getLength(), SDULength);

        return toReturnSDU;
    }

    /**
     * @return header to add in the SDU
     */
    private byte[] getToAddHeader() {
        byte[] header = new byte[STRUCTURE.TOTAL_HEADER.getLength()];
        //from 0_length to min_max
        char payloadLength = (char) (payload.getSize()+Short.MIN_VALUE);
        try {

            byte[] stampBytes = controlStamp.getBytes("UTF-16");
            byte[] payloadLengthBytes = (new String(new char[]{(char) payloadLength})).getBytes("UTF-16");

            System.arraycopy(stampBytes, 0, header, 0, stampBytes.length);
            System.arraycopy(payloadLengthBytes, 0, header, stampBytes.length, payloadLengthBytes.length);

        } catch (Exception e) { }

        return header;
    }

    /**
     * @return payload's data
     */
    @Override
    public byte[] getData() {
        return payload.getData();
    }

    public enum STRUCTURE {
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
