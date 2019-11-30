package com.gruppo3.smsconnection.datalink.message;

import com.gruppo3.smsconnection.connection.exception.InvalidPayloadException;
import com.gruppo3.smsconnection.smsdatalink.message.SMSMessage;
import com.gruppo3.smsconnection.smsdatalink.message.SMSPeer;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static com.gruppo3.smsconnection.utils.Utils.getAlphaNumericString;

public class SMSMessageDataTest {

    byte[] validData;
    byte[] nullData=null;
    byte[] tooMuchData;
    byte[] maxData;


    public SMSMessageDataTest(){
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
        SMSMessage message;
        try {
            message=new SMSMessage(null, new SMSPeer(SMSPeerTest.validAddress) ,validData);
        }
        catch (InvalidPayloadException e){
            Assert.fail("Should not throw InvalidPayloadException exception");}
        catch (Exception e){Assert.fail("Should not throw this exception");}
    }

    @Test
    public void maxData() {
        SMSMessage message;
        try {
            message=new SMSMessage(null, new SMSPeer(SMSPeerTest.validAddress) ,maxData);
        }
        catch (InvalidPayloadException e) { Assert.fail("Shouldn't throw InvalidPeerException ");}
        catch (Exception e) {Assert.fail("Shouldn't throw this Exception");}
    }

    @Test
    public void tooMuchData() {
        SMSMessage message;
        try {
            message=new SMSMessage(null, new SMSPeer(SMSPeerTest.validAddress) ,tooMuchData);
            Assert.fail("Should throw InvalidPeerException ");
        }
        catch (InvalidPayloadException e) {} //correct
        catch (Exception e) {Assert.fail("Shouldn't throw this Exception");}
    }

    @Test
    public void nullData() {
        SMSMessage message;
        try {
            message=new SMSMessage(null, new SMSPeer(SMSPeerTest.validAddress) ,nullData);
            Assert.fail("Should throw NullPointerException ");
        }
        catch (NullPointerException e) {} //correct
        catch (Exception e) {Assert.fail("Shouldn't throw this Exception");}
    }

    @Test
    public void getDataTest() {
        SMSMessage message;
        try {
            message=new SMSMessage(null, new SMSPeer(SMSPeerTest.validAddress) ,validData);
            if( !Arrays.equals( message.getData(), validData))
                Assert.fail("should be the same data");
        }
        catch (InvalidPayloadException e) {Assert.fail("Shouldn't throw InvalidPayloadException");}
        catch (Exception e) {Assert.fail("Shouldn't throw this Exception");}
    }

}