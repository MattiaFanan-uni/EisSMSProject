package com.gruppo3.smsconnection;

import com.gruppo3.smsconnection.smsdatalink.SMSDataUnit;
import com.gruppo3.smsconnection.smsdatalink.SMSHeader;
import com.gruppo3.smsconnection.smsdatalink.SMSPayload;
import com.gruppo3.smsconnection.smsdatalink.SMSPeer;
import com.gruppo3.smsconnection.smsdatalink.core.APIMessage;
import com.gruppo3.smsconnection.smsdatalink.core.SMSAdapter;

import org.junit.Assert;
import org.junit.Test;

public class SMSAdapterTest {

    @Test
    public void toAPIMessage()
    {
        String scAddress="12345678";
        String desAddress="9876543";
        String messageText="test";
        SMSDataUnit dataUnit=null;
        try {
            dataUnit=new SMSDataUnit(new SMSHeader(new SMSPeer(desAddress),new SMSPeer(scAddress)),new SMSPayload("test"));
        }
        catch (Exception e){}

        APIMessage apiMessage= SMSAdapter.adaptToAPIMessage(dataUnit);

        if(apiMessage.getDestination().compareTo(desAddress)!=0)
            Assert.fail("destinations's address should be the same");
        if(apiMessage.getSource().compareTo(scAddress)!=0)
            Assert.fail("sources's address should be the same");
        //APIMessage text message is stamp+dataunit's message
        if(apiMessage.getTextMessage().compareTo(dataUnit.getHeader().getStamp()+messageText)!=0)
            Assert.fail("messages's text should be the same");

    }

    @Test
    public void toSMSDataUnit()
    {
        String scAddress="12345678";
        String desAddress="9876543";
        String messageText="test";
        SMSDataUnit startDataUnit=null;
        try {
            startDataUnit=new SMSDataUnit(new SMSHeader(new SMSPeer(desAddress),new SMSPeer(scAddress)),new SMSPayload("test"));
        }
        catch (Exception e){}

        APIMessage apiMessage= SMSAdapter.adaptToAPIMessage(startDataUnit);

        SMSDataUnit dataUnit=null;
        try{
            dataUnit=SMSAdapter.adaptToSMSDataUnit(apiMessage);
        }
        catch (Exception e){Assert.fail("shouldn't throw exceptions");}

        if(dataUnit==null)
            Assert.fail("shouldn't be null");

        if(startDataUnit.getHeader().getDestinationPeer().getAddress().compareTo(dataUnit.getHeader().getDestinationPeer().getAddress())!=0)
            Assert.fail("destinations's address should be the same");
        if(startDataUnit.getHeader().getSourcePeer().getAddress().compareTo(dataUnit.getHeader().getSourcePeer().getAddress())!=0)
            Assert.fail("sources's address should be the same");
        if(startDataUnit.getPayload().getData().compareTo(dataUnit.getPayload().getData())!=0)
            Assert.fail("messages's text should be the same");
    }


}
