package com.gruppo3.smsconnection.smsdatalink.manager;

import com.gruppo3.smsconnection.connection.exception.InvalidPayloadException;
import com.gruppo3.smsconnection.connection.exception.InvalidHeaderException;
import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;
import com.gruppo3.smsconnection.smsdatalink.SMSDataUnit;
import com.gruppo3.smsconnection.smsdatalink.SMSHeader;
import com.gruppo3.smsconnection.smsdatalink.SMSPayload;
import com.gruppo3.smsconnection.smsdatalink.SMSPeer;

/**
 * @author Mattia Fanan
 * adapter from android smsMessage to SMSMessage
 */
public class SMSAdapter {
    SMSDataUnit dataUnit;
    String smsText;
    String smsAddress;

    /**
     * build the adapter from SMSDataUnit
     * @param dataUnit
     * @throws InvalidPeerException when invalid peer in found
     * @throws InvalidPayloadException when invalid payload is found
     */
    public SMSAdapter(SMSDataUnit dataUnit) throws InvalidPeerException, InvalidPayloadException
    {
        this.dataUnit=dataUnit;
        //check validity + exception
        smsAddress=dataUnit.getHeader().getDestinationPeer().getAddress();
        smsText=dataUnit.getHeader().getToAddHeder()+dataUnit.getPayload().getData();
    }

    /**
     * get data unit's destination sms address for android smsMessage
     * @return sms address
     */
    public String getSMSAddress()
    {
        return smsAddress;
    }

    /**
     * get data unit's sms text for android smsMessage
     * @return sms text
     */
    public String getSMSText()
    {
        return smsText;
    }

    /**
     * build adapter from text and source address
     * @param smsAddress String source address
     * @param smsText String payload text
     * @throws InvalidPeerException when address is not valid
     * @throws InvalidPayloadException when text is not valid
     * @throws InvalidHeaderException  when message hasn't check stamp
     */
    public SMSAdapter(String smsAddress,String smsText) throws InvalidPeerException, InvalidPayloadException, InvalidHeaderException
    {
        this.smsAddress=smsAddress;
        //address is source data unit's peer address
        SMSHeader header=new SMSHeader(null,new SMSPeer(smsAddress));
        //extract data unit header
        String stamp=smsText.substring(0,SMSHeader.LENGTH);
        //compare the two headers
        if(stamp.compareTo(header.getToAddHeder())!=0)
            throw new InvalidPayloadException();
        //extract payload
        this.smsText=smsText.substring(SMSHeader.LENGTH);

        this.dataUnit= new SMSDataUnit(header,new SMSPayload(this.smsText));
    }

    /**
     * get the data unit
     * @return SMSDataUnit
     */
    public SMSDataUnit getSMSDataUnit()
    {
        return dataUnit;
    }
}
