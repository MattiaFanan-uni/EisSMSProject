package com.gruppo3.smsconnection.replicatednet.message;

import androidx.annotation.NonNull;

import com.gruppo3.smsconnection.connection.AbstractStringMessage;
import com.gruppo3.smsconnection.connection.exception.InvalidMessageException;
import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;
import com.gruppo3.smsconnection.smsdatalink.message.SMSMessage;
import com.gruppo3.smsconnection.utils.ReplicatedNetPeerParser;

/**
 * @author Mattia Fanana
 * net layer message
 */
public class ReplicatedNetMessage extends AbstractStringMessage<ReplicatedNetPeer> {


    public static final char controlStamp = 'A';//is the id of this protocol
    //used from SMSPayload to check if it's data can be stored entirely
    public static final int MAX_PAYLOAD_LENGTH = SMSMessage.MAX_PAYLOAD_LENGTH - 2 - 32;//control code+hex 16 bytes address+ #

    /**
     * @param destination message's destination peer
     * @param source      message's source peer
     * @param payload     message's payload
     * @throws InvalidPeerException    when invalid peer found
     * @throws InvalidMessageException when invalid payload found
     */
    public ReplicatedNetMessage(ReplicatedNetPeer destination, ReplicatedNetPeer source, String payload)
            throws InvalidPeerException, InvalidMessageException {
        super(destination, source, payload, false);
    }


    /**
     * get a ReplicatedNetMessage from a passed lower layer payload
     *
     * @param lowerLayerSDU sms PDU
     * @return ReplicatedNetMessage if is possible to extract one from the SDU
     * @throws InvalidPeerException    when invalid peer found in sourceAddress
     * @throws InvalidMessageException when lowerLayerSDU is not suitable for build an SMSMessage
     */
    public static ReplicatedNetMessage buildFromSDU(String lowerLayerSDU) throws InvalidMessageException, InvalidPeerException {

        String header = lowerLayerSDU.substring(0, lowerLayerSDU.indexOf("#"));

        if (header.charAt(0) != controlStamp)
            throw new InvalidMessageException();

        //try build SMSPeer
        ReplicatedNetPeer source = new ReplicatedNetPeerParser().parseData(header.substring(1));

        if (source == null)
            throw new InvalidPeerException();

        String payload = lowerLayerSDU.substring(lowerLayerSDU.indexOf("#") + 1);

        return new ReplicatedNetMessage(null, source, payload);
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

        String sourceHex = new ReplicatedNetPeerParser().parseString(sourcePeer);
        return controlStamp + sourceHex;
    }
}
