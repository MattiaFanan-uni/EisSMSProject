package com.gruppo3.smsconnection;

import com.gruppo3.smsconnection.connection.exception.InvalidHeaderException;
import com.gruppo3.smsconnection.connection.exception.InvalidPayloadException;
import com.gruppo3.smsconnection.smsdatalink.SMSDataUnit;
import com.gruppo3.smsconnection.smsdatalink.SMSProtocolControlInformation;
import com.gruppo3.smsconnection.smsdatalink.SMSServiceDataUnit;
import com.gruppo3.smsconnection.smsdatalink.SMSPeer;

import org.junit.Assert;
import org.junit.Test;

public class SMSDataUnitTest {
 /*   byte[] data=("").getBytes("UTF-16");

    @Test
    public void setUp()
    {
        SMSDataUnit dataUnit=null;
        SMSProtocolControlInformation header=null;
        SMSServiceDataUnit payload=null;
        try{
            header=new SMSProtocolControlInformation(new SMSPeer("123685"),null);
            payload=new SMSServiceDataUnit("qwertui");
        }
        catch (Exception e){Assert.fail("problems unchecked in other tests ");}

        try{
            dataUnit=new SMSDataUnit(header,payload);
        }
        catch (InvalidHeaderException e){ Assert.fail("shouldn't throw InvalidHeaderException");}
        catch (InvalidPayloadException e){ Assert.fail("shouldn't throw InvalidPayloadException");}
        if(dataUnit==null)
            Assert.fail("sholudn't be null");
    }
    @Test
    public void setUpNullHeader()
    {
        SMSDataUnit dataUnit=null;
        SMSProtocolControlInformation header=null;
        SMSServiceDataUnit payload=null;
        try{
            payload=new SMSServiceDataUnit("qwertui");
        }
        catch (Exception e){Assert.fail("problems unchecked in other tests ");}

        try{
            dataUnit=new SMSDataUnit(header,payload);
        }
        catch (InvalidHeaderException e){}//correct
        catch (InvalidPayloadException e){ Assert.fail("shouldn't throw InvalidPayloadException");}
    }
    @Test
    public void setUpNullPayload()
    {
        SMSDataUnit dataUnit=null;
        SMSProtocolControlInformation header=null;
        SMSServiceDataUnit payload=null;
        try{
            header=new SMSProtocolControlInformation(new SMSPeer("123685"),null);
        }
        catch (Exception e){Assert.fail("problems unchecked in other tests ");}

        try{
            dataUnit=new SMSDataUnit(header,payload);
        }
        catch (InvalidHeaderException e){ Assert.fail("shouldn't throw InvalidHeaderException");}
        catch (InvalidPayloadException e){}//correct
    }


    @Test
    public void toStringTestBothPeer() {
        SMSDataUnit dataUnit=null;
        String text="test";
        String destination="1234567";
        String source="987654";

        try {
            dataUnit = new SMSDataUnit(
                    new SMSProtocolControlInformation(new SMSPeer(destination),new SMSPeer(source))
                    , new SMSServiceDataUnit(text));
        }
        catch(Exception e){Assert.fail("Should not throw an exception");}
        String expected="";

        expected=expected+"Message:" + text;
        expected=expected+" ---Destination:" + destination;
        expected=expected+" ---Source:" + source;


        Assert.assertEquals(expected , dataUnit.toString());
    }

    @Test
    public void toStringTestOnlyDestination() {
        SMSDataUnit dataUnit=null;
        String text="test";
        String destination="1234567";

        try {
            dataUnit = new SMSDataUnit(
                    new SMSProtocolControlInformation(new SMSPeer(destination),null)
                    , new SMSServiceDataUnit(text));
        }
        catch(Exception e){Assert.fail("Should not throw an exception");}
        String expected="";

        expected=expected+"Message:" + text;
        expected=expected+" ---Destination:" + destination;


        Assert.assertEquals(expected , dataUnit.toString());
    }
    @Test
    public void toStringTestOnlySource() {
        SMSDataUnit dataUnit=null;
        String text="test";
        String source="987654";

        try {
            dataUnit = new SMSDataUnit(
                    new SMSProtocolControlInformation(null,new SMSPeer(source))
                    , new SMSServiceDataUnit(text));
        }
        catch(Exception e){Assert.fail("Should not throw an exception");}
        String expected="";

        expected=expected+"Message:" + text;
        expected=expected+" ---Source:" + source;


        Assert.assertEquals(expected , dataUnit.toString());
    }*/
}
