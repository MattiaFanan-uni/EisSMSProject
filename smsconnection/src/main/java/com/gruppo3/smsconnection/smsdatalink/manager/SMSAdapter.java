package com.gruppo3.smsconnection.smsdatalink.manager;

import com.gruppo3.smsconnection.connection.Peer;
import com.gruppo3.smsconnection.connection.exception.InvalidDataException;
import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;
import com.gruppo3.smsconnection.smsdatalink.SMSDataUnit;
import com.gruppo3.smsconnection.smsdatalink.SMSMessage;
import com.gruppo3.smsconnection.smsdatalink.SMSPeer;

/**
 * @author Mattia Fanan
 */
public class SMSAdapter {
    SMSDataUnit dataUnit;
    String smsText;
    String smsAddress;
    public SMSAdapter(SMSDataUnit dataUnit) throws InvalidPeerException, InvalidDataException
    {
        this.dataUnit=dataUnit;
        //check validity + exception
        smsAddress=dataUnit.getPeer().getAddress();
        smsText=dataUnit.getMessage().getData();
    }
    public String getSMSAddress()
    {
        return dataUnit.getPeer().getAddress();
    }

    public String getSMSText()
    {
        return dataUnit.getMessage().getData();
    }

    public SMSAdapter(String smsAddress,String smsText) throws InvalidPeerException, InvalidDataException
    {
        this.smsText=smsText;
        this.smsAddress=smsAddress;
        this.dataUnit= new SMSDataUnit(new SMSPeer(smsAddress),new SMSMessage(smsText));
    }
    public SMSDataUnit getSMSDataUnit()
    {
        return dataUnit;
    }
}
