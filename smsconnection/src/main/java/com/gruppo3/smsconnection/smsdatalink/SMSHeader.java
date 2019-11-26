package com.gruppo3.smsconnection.smsdatalink;

import com.gruppo3.smsconnection.connection.Header;
import com.gruppo3.smsconnection.connection.exception.InvalidHeaderException;

/**
 * @author Mattia Fanan
 * header of the SMSDataUnit
 */
public class SMSHeader extends Header<SMSPeer,String> {
    private static final String stamp=Character.toString((char)0x03A6);//capital phi
    public static final int LENGTH=1;

    /**
     * build the header
     * only one peer could be null
     * @param destination SMSPeer destination peer
     * @param source SMSPeer source peer
     * @throws InvalidHeaderException when all the peers are null
     */
    public SMSHeader(SMSPeer destination,SMSPeer source)throws InvalidHeaderException
    {
        canHaveNullPeer=true;

        //they can't be both null
        if(destination==null && source==null)
            throw new InvalidHeaderException();

        if(destination!=null)
                destinationPeer=destination;

        if(source!=null)
                sourcePeer=source;
    }

    /**
     * get header's data to add to the payload
     * @return String header to add to the payload
     */
    @Override
    public String getStamp() {
        return stamp;
    }

}
