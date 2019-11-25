package com.gruppo3.smsconnection;

import com.gruppo3.smsconnection.connection.exception.InvalidHeaderException;
import com.gruppo3.smsconnection.connection.exception.InvalidPayloadException;
import com.gruppo3.smsconnection.smsdatalink.SMSDataUnit;
import com.gruppo3.smsconnection.smsdatalink.SMSHeader;
import com.gruppo3.smsconnection.smsdatalink.SMSPayload;
import com.gruppo3.smsconnection.smsdatalink.SMSPeer;

import org.junit.Assert;
import org.junit.Test;

public class SMSDataUnitTest {

    @Test
    public void setUp()
    {
        SMSDataUnit dataUnit=null;
        SMSHeader header=null;
        SMSPayload payload=null;
        try{
            header=new SMSHeader(new SMSPeer("123685"),null);
            payload=new SMSPayload("qwertui");
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
        SMSHeader header=null;
        SMSPayload payload=null;
        try{
            payload=new SMSPayload("qwertui");
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
        SMSHeader header=null;
        SMSPayload payload=null;
        try{
            header=new SMSHeader(new SMSPeer("123685"),null);
        }
        catch (Exception e){Assert.fail("problems unchecked in other tests ");}

        try{
            dataUnit=new SMSDataUnit(header,payload);
        }
        catch (InvalidHeaderException e){ Assert.fail("shouldn't throw InvalidHeaderException");}
        catch (InvalidPayloadException e){}//correct
    }

    @Test
    public void setHeaderTest()
    {
        SMSDataUnit dataUnit=null;
        SMSHeader header=null;
        SMSHeader newHeader=null;
        SMSPayload payload=null;
        try{
            header=new SMSHeader(new SMSPeer("123685"),null);
            payload=new SMSPayload("qwertui");
            newHeader=new SMSHeader(new SMSPeer("98765"),null);
            dataUnit=new SMSDataUnit(header,payload);
        }
        catch (Exception e){Assert.fail("problems unchecked in other tests ");}

        boolean changeResult=dataUnit.setHeader(newHeader);

        if(!changeResult)
            Assert.fail("result should be true");
        if(!dataUnit.getHeader().equals(newHeader))
            Assert.fail("dataUnit's header should be the new header");
    }

    @Test
    public void setHeaderNullTest()
    {
        SMSDataUnit dataUnit=null;
        SMSHeader header=null;
        SMSHeader newHeader=null;
        SMSPayload payload=null;
        try{
            header=new SMSHeader(new SMSPeer("123685"),null);
            payload=new SMSPayload("qwertui");
            dataUnit=new SMSDataUnit(header,payload);
        }
        catch (Exception e){Assert.fail("problems unchecked in other tests ");}

        boolean changeResult=dataUnit.setHeader(newHeader);

        if(changeResult)
            Assert.fail("result should be false");
        if(!dataUnit.getHeader().equals(header))
            Assert.fail("dataUnit's header should be the old header");
    }
    @Test
    public void setPayloadTest()
    {
        SMSDataUnit dataUnit=null;
        SMSHeader header=null;
        SMSPayload newPayload=null;
        SMSPayload payload=null;
        try{
            header=new SMSHeader(new SMSPeer("123685"),null);
            payload=new SMSPayload("qwertui");
            newPayload=new SMSPayload("poiuy");
            dataUnit=new SMSDataUnit(header,payload);
        }
        catch (Exception e){Assert.fail("problems unchecked in other tests ");}

        boolean changeResult=dataUnit.setPayload(newPayload);

        if(!changeResult)
            Assert.fail("result should be true");
        if(!dataUnit.getPayload().equals(newPayload))
            Assert.fail("dataUnit's payload should be the new payload");
    }

    @Test
    public void setPayloadNullTest()
    {
        SMSDataUnit dataUnit=null;
        SMSHeader header=null;
        SMSPayload newPayload=null;
        SMSPayload payload=null;
        try{
            header=new SMSHeader(new SMSPeer("123685"),null);
            payload=new SMSPayload("qwertui");
            dataUnit=new SMSDataUnit(header,payload);
        }
        catch (Exception e){Assert.fail("problems unchecked in other tests ");}

        boolean changeResult=dataUnit.setPayload(newPayload);

        if(changeResult)
            Assert.fail("result should be false");
        if(!dataUnit.getPayload().equals(payload))
            Assert.fail("dataUnit's payload should be the old payload");
    }

    @Test
    public void toStringTestBothPeer() {
        SMSDataUnit dataUnit=null;
        String text="test";
        String destination="1234567";
        String source="987654";

        try {
            dataUnit = new SMSDataUnit(
                    new SMSHeader(new SMSPeer(destination),new SMSPeer(source))
                    , new SMSPayload(text));
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
                    new SMSHeader(new SMSPeer(destination),null)
                    , new SMSPayload(text));
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
                    new SMSHeader(null,new SMSPeer(source))
                    , new SMSPayload(text));
        }
        catch(Exception e){Assert.fail("Should not throw an exception");}
        String expected="";

        expected=expected+"Message:" + text;
        expected=expected+" ---Source:" + source;


        Assert.assertEquals(expected , dataUnit.toString());
    }
}
