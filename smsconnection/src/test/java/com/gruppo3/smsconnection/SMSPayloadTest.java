package com.gruppo3.smsconnection;

import org.junit.Assert;
import org.junit.Test;

import com.gruppo3.smsconnection.connection.exception.InvalidPayloadException;
import com.gruppo3.smsconnection.smsdatalink.SMSServiceDataUnit;

public class SMSPayloadTest {

/*
    @Test
    public void setUp(){
        SMSServiceDataUnit payload;
        try {
            payload=new SMSServiceDataUnit("test");
        }
        catch (InvalidPayloadException e){Assert.fail("Should not throw InvalidPayloadException exception");}
        catch (Exception e){Assert.fail("Should not throw this exception");}
    }

    @Test
    public void tooMuchData() {
        SMSServiceDataUnit payload;
        try {
            payload=new SMSServiceDataUnit(getAlphaNumericString(SMSServiceDataUnit.MAX_PAYLOAD_LENGTH+1));
            Assert.fail("Should throw InvalidPeerException ");
        }
        catch (InvalidPayloadException e) {} //correct
        catch (Exception e) {Assert.fail("Shouldn't throw this Exception");}
    }

    @Test
    public void nullData() {
        SMSServiceDataUnit payload;
        try {
            payload= new SMSServiceDataUnit(null);
            Assert.fail("Should throw InvalidPayloadException ");
        }
        catch (InvalidPayloadException e) {} //correct
        catch (Exception e) {Assert.fail("Shouldn't throw this Exception");}
    }

    @Test
    public void getDataTest() {
        SMSServiceDataUnit payload;
        String inputText="qwerty";
        try {
            payload= new SMSServiceDataUnit(inputText);
            if(payload.getData().compareTo(inputText)!=0)
                Assert.fail("should be the same data");
        }
        catch (InvalidPayloadException e) {Assert.fail("Shouldn't throw InvalidPayloadException");}
        catch (Exception e) {Assert.fail("Shouldn't throw this Exception");}
    }

    @Test
    public void setDataTest() {
        SMSServiceDataUnit payload;
        String startData="qwerty";
        String changedData="poiu";
        try {
            payload=  new SMSServiceDataUnit(startData);
            boolean changeResult=payload.setData(changedData);
            if(!changeResult)
                Assert.fail("result should be true");
            if(payload.getData().compareTo(changedData)!=0)
                Assert.fail("data should be changed");
        }
        catch (InvalidPayloadException e) {Assert.fail("Shouldn't throw InvalidPayloadException");}
        catch (Exception e) {Assert.fail("Shouldn't throw this Exception");}
    }

    @Test
    public void setDataNullTest() {
        SMSServiceDataUnit payload;
        String startData="qwerty";
        String changedData=null;
        try {
            payload=  new SMSServiceDataUnit(startData);
            boolean changeResult=payload.setData(changedData);
            if(changeResult)
                Assert.fail("result should be false");
            if(payload.getData().compareTo(startData)!=0)
                Assert.fail("data shouldn't be changed");
        }
        catch (InvalidPayloadException e) {Assert.fail("Shouldn't throw InvalidPayloadException");}
        catch (Exception e) {Assert.fail("Shouldn't throw this Exception");}
    }


    // function to generate a random string of length n
    private String getAlphaNumericString(int n)
    {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

*/
}