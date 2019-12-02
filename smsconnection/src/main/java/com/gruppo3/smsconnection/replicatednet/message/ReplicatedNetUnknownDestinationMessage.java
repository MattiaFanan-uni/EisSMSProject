package com.gruppo3.smsconnection.replicatednet.message;

import androidx.annotation.NonNull;

import com.gruppo3.smsconnection.connection.AbstractByteMessage;
import com.gruppo3.smsconnection.connection.exception.InvalidMessageException;
import com.gruppo3.smsconnection.connection.exception.InvalidPayloadException;
import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;
import com.gruppo3.smsconnection.smsdatalink.message.SMSMessage;

import java.io.UnsupportedEncodingException;

public class ReplicatedNetUnknownDestinationMessage extends AbstractByteMessage<ReplicatedNetPeer> {

    public static final String controlStamp=Character.toString('2');//is the id of this protocol
    //used from SMSPayload to check if it's data can be stored entirely
    public static final int MAX_PAYLOAD_LENGTH= SMSMessage.MAX_PAYLOAD_LENGTH - STRUCTURE.TOTAL_HEADER.getLength();//134(byte) = 67 utf-16

    /**
     *
     * @param source        message's source peer
     * @param payload       message's payload
     * @throws InvalidPeerException     when invalid peer found
     * @throws InvalidPayloadException  when invalid payload found
     */
    public ReplicatedNetUnknownDestinationMessage(ReplicatedNetPeer source, byte[] payload)
            throws InvalidPeerException, InvalidPayloadException {
        super(null,source,payload,false);
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
     * get a ReplicatedNetMessage from a passed lower layer payload
     * @param lowerLayerSDU     replicatedNetMessage PDU
     * @return ReplicatedNetMessage if is possible to extract one from the SDU
     * @throws InvalidPeerException     when invalid peer found in sourceAddress
     * @throws InvalidPayloadException  when lowerLayerSDU is not suitable for build an SMSMessage
     */
    public static ReplicatedNetUnknownDestinationMessage buildFromSDU(byte[] lowerLayerSDU)
            throws InvalidPeerException,InvalidPayloadException, InvalidMessageException {

        int stampStartPosition=0;
        int sourceAddressStartPosition = stampStartPosition + STRUCTURE.STAMP.getLength();
        int payloadLengthStartPosition = sourceAddressStartPosition + STRUCTURE.SOURCE_PEER.getLength();

        if(lowerLayerSDU.length < STRUCTURE.TOTAL_HEADER.getLength())
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

        //retrieve sourceAddress
        byte[] sourceAddress=new byte[STRUCTURE.SOURCE_PEER.getLength()];
        System.arraycopy(lowerLayerSDU, sourceAddressStartPosition,  sourceAddress, 0, STRUCTURE.SOURCE_PEER.getLength());

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

            return new ReplicatedNetUnknownDestinationMessage(new ReplicatedNetPeer(sourceAddress), data);
        }
        catch (UnsupportedEncodingException e){}
        //if this code is executed there is an error
        throw new InvalidMessageException();
    }


    @Override
    protected byte[] getToAddHeader() {
        byte[] header = new byte[STRUCTURE.TOTAL_HEADER.getLength()];

        int stampStartPosition=0;
        int sourceAddressStartPosition = stampStartPosition + STRUCTURE.STAMP.getLength();
        int payloadLengthStartPosition = sourceAddressStartPosition + STRUCTURE.SOURCE_PEER.getLength();

        //from 0_length to min_max
        short payloadLength = (short) (payload.length+Short.MIN_VALUE);


        try {

            byte[] stampBytes = controlStamp.getBytes("UTF-16");
            byte[] payloadLengthBytes = (new String(new char[]{(char) payloadLength})).getBytes("UTF-16");
            //stamp
            System.arraycopy(stampBytes, 0, header, stampStartPosition , STRUCTURE.STAMP.getLength());
            //source address
            System.arraycopy(sourcePeer.getAddress(), 0, header, sourceAddressStartPosition, STRUCTURE.SOURCE_PEER.getLength());
            //payload length
            System.arraycopy(payloadLengthBytes, 0, header, payloadLengthStartPosition, STRUCTURE.PAYLOAD_LENGTH.getLength());

        } catch (Exception e) { }

        return header;
    }


    private enum STRUCTURE {
        TOTAL_HEADER(8+ReplicatedNetPeer.LENGTH),
        STAMP(4),
        SOURCE_PEER(ReplicatedNetPeer.LENGTH),
        PAYLOAD_LENGTH(4);

        private int length;

        STRUCTURE(int value){length=value;}

        public int getLength() {
            return this.length;
        }

    }
}
