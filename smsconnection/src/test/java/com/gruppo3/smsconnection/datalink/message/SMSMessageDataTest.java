package com.gruppo3.smsconnection.datalink.message;

import com.gruppo3.smsconnection.connection.exception.InvalidMessageException;
import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;
import com.gruppo3.smsconnection.smsdatalink.message.SMSMessage;
import com.gruppo3.smsconnection.smsdatalink.message.SMSPeer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static com.gruppo3.smsconnection.utils.Utils.getAlphaNumericString;

public class SMSMessageDataTest {

    String validData;
    String nullData = null;
    String tooMuchData;
    String maxData;


    @Before
    public void init() {


        validData = getAlphaNumericString(10);
        tooMuchData = getAlphaNumericString(SMSMessage.MAX_PAYLOAD_LENGTH+6);
        maxData = getAlphaNumericString(SMSMessage.MAX_PAYLOAD_LENGTH);

    }

    @Test
    public void setUp() {
        SMSMessage message;
        try {
            message = new SMSMessage(null, new SMSPeer(SMSPeerTest.validAddress), validData);
        } catch (InvalidMessageException e) {
            Assert.fail("Should not throw InvalidMessageException exception");
        } catch (InvalidPeerException e) {
            Assert.fail("Should not throw InvalidPeerException");
        }
    }

    @Test
    public void maxData() {
        SMSMessage message;
        try {
            message = new SMSMessage(null, new SMSPeer(SMSPeerTest.validAddress), maxData);
        } catch (InvalidMessageException e) {
            Assert.fail("Should not throw InvalidMessageException exception");
        } catch (InvalidPeerException e) {
            Assert.fail("Should not throw InvalidPeerException");
        }
    }

    @Test
    public void tooMuchData() {
        SMSMessage message;
        try {
            message = new SMSMessage(null, new SMSPeer(SMSPeerTest.validAddress), tooMuchData);
            Assert.fail("Should throw InvalidPeerException ");
        } catch (InvalidMessageException e) {
        } //correct
        catch (InvalidPeerException e) {
            Assert.fail("Should not throw InvalidPeerException");
        }
    }

    @Test
    public void nullData() {
        SMSMessage message;
        try {
            message = new SMSMessage(null, new SMSPeer(SMSPeerTest.validAddress), nullData);
            Assert.fail("Should throw NullPointerException ");
        } catch (NullPointerException e) {
        } //correct
        catch (Exception e) {
            Assert.fail("Shouldn't throw this Exception");
        }
    }

    @Test
    public void getDataTest() {
        SMSMessage message;
        try {
            message = new SMSMessage(null, new SMSPeer(SMSPeerTest.validAddress), validData);
            Assert.assertEquals(message.getData(),validData);
        } catch (Exception e) {
            Assert.fail("Shouldn't throw this Exception");
        }
    }

}