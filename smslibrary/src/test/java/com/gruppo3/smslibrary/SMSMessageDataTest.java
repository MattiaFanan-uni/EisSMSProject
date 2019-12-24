package com.gruppo3.smslibrary;

import com.gruppo3.smslibrary.types.Message;
import com.gruppo3.smslibrary.types.Peer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.gruppo3.smslibrary.utils.Utils.getAlphaNumericString;

public class SMSMessageDataTest {
    private String validData;
    private String tooMuchData;
    private String maxData;


    @Before
    public void init() {

        validData = getAlphaNumericString(10);
        tooMuchData = getAlphaNumericString(Message.MAX_PAYLOAD_LENGTH + 6);
        maxData = getAlphaNumericString(Message.MAX_PAYLOAD_LENGTH);

    }

    @Test
    public void setUp() {
        try {
            new Message(null, new Peer(SMSPeerTest.getValidAddress()), validData);
        } catch (IllegalArgumentException e) {
            Assert.fail("Should not throw InvalidMessageException exception");
        }
    }

    @Test
    public void maxData() {
        try {
            new Message(null, new Peer(SMSPeerTest.getValidAddress()), maxData);
        } catch (IllegalArgumentException e) {
            Assert.fail("Should not throw InvalidMessageException exception");
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void tooMuchData() throws IllegalArgumentException {
        new Message(null, new Peer(SMSPeerTest.getValidAddress()), tooMuchData);
    }

    @Test(expected = NullPointerException.class)
    public void nullData() {
        new Message(null, new Peer(SMSPeerTest.getValidAddress()), null);
    }

    @Test
    public void getDataTest() {
        Message message = new Message(null, new Peer(SMSPeerTest.getValidAddress()), validData);
        Assert.assertEquals(message.getPayload(), validData);
    }

}