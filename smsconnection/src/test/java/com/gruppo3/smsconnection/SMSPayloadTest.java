package com.gruppo3.smsconnection;

import com.gruppo3.smsconnection.connection.exception.InvalidPayloadException;
import com.gruppo3.smsconnection.smsdatalink.SMSMessage;
import com.gruppo3.smsconnection.smsdatalink.SMSPayload;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

public class SMSPayloadTest {

    byte[] validData;
    byte[] nullData=null;
    byte[] tooMuchData;
    byte[] maxData;


    public SMSPayloadTest(){
        try {
            //2 * 10 bytes + 2 endstring bytes = 22 bytes
            validData = getAlphaNumericString(10).getBytes("UTF-16");
            //2 * MAXPAYLOAD_LENGTH +2
            tooMuchData=getAlphaNumericString(SMSMessage.MAX_PAYLOAD_LENGTH).getBytes("UTF-16");
            // 2 * ((MAXPAYLOAD_LENGTH/2 -2) +2) = MAXPAYLOAD_LENGTH
            maxData=getAlphaNumericString((SMSMessage.MAX_PAYLOAD_LENGTH/2)-2).getBytes("UTF-16");
        }
        catch (UnsupportedEncodingException e){}
    }

    @Test
    public void setUp(){
        SMSPayload payload;
        try {
            payload=new SMSPayload(validData);
        }
        catch (InvalidPayloadException e){
            Assert.fail("Should not throw InvalidPayloadException exception");}
        catch (Exception e){Assert.fail("Should not throw this exception");}
    }

    @Test
    public void maxData() {
        SMSPayload payload;
        try {
            payload=new SMSPayload(maxData);
        }
        catch (InvalidPayloadException e) { Assert.fail("Shouldn't throw InvalidPeerException ");}
        catch (Exception e) {Assert.fail("Shouldn't throw this Exception");}
    }

    @Test
    public void tooMuchData() {
        SMSPayload payload;
        try {
            payload=new SMSPayload(tooMuchData);
            Assert.fail("Should throw InvalidPeerException ");
        }
        catch (InvalidPayloadException e) {} //correct
        catch (Exception e) {Assert.fail("Shouldn't throw this Exception");}
    }

    @Test
    public void nullData() {
        SMSPayload payload;
        try {
            payload= new SMSPayload(nullData);
            Assert.fail("Should throw NullPointerException ");
        }
        catch (NullPointerException e) {} //correct
        catch (Exception e) {Assert.fail("Shouldn't throw this Exception");}
    }

    @Test
    public void getDataTest() {
        SMSPayload payload;
        try {
            payload= new SMSPayload(validData);
            if( !Arrays.equals( payload.getData(), validData))
                Assert.fail("should be the same data");
        }
        catch (InvalidPayloadException e) {Assert.fail("Shouldn't throw InvalidPayloadException");}
        catch (Exception e) {Assert.fail("Shouldn't throw this Exception");}
    }

    @Test
    public void getSize(){
        SMSPayload payload;
        try {
            payload= new SMSPayload(validData);
            if( payload.getSize()!=validData.length)
                Assert.fail("should be the same size");
        }
        catch (InvalidPayloadException e) {Assert.fail("Shouldn't throw InvalidPayloadException");}
        catch (Exception e) {Assert.fail("Shouldn't throw this Exception");}
    }

    // function to generate a random string of length n
    public static String getAlphaNumericString(int n)
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


}