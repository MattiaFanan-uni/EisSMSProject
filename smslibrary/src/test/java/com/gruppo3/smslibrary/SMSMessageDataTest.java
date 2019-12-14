package com.gruppo3.smslibrary;

import com.gruppo3.smslibrary.exceptions.InvalidMessageException;
import com.gruppo3.smslibrary.types.Message;
import com.gruppo3.smslibrary.types.Peer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.gruppo3.smslibrary.Utils.Utils.getAlphaNumericString;

public class SMSMessageDataTest {

    String validData;
    String tooMuchData;
    String maxData;


    @Before
    public void init() {

        validData = getAlphaNumericString(10);
        tooMuchData = getAlphaNumericString(Message.MAX_PAYLOAD_LENGTH + 6);
        maxData = getAlphaNumericString(Message.MAX_PAYLOAD_LENGTH);

    }

    @Test
    public void setUp() {
        try {
            new Message(null, new Peer(SMSPeerTest.validAddress), validData);
        } catch (InvalidMessageException e) {
            Assert.fail("Should not throw InvalidMessageException exception");
        }
    }

    @Test
    public void maxData() {
        try {
            new Message(null, new Peer(SMSPeerTest.validAddress), maxData);
        } catch (InvalidMessageException e) {
            Assert.fail("Should not throw InvalidMessageException exception");
        }
    }

    @Test(expected = InvalidMessageException.class)
    public void tooMuchData() throws InvalidMessageException {
        new Message(null, new Peer(SMSPeerTest.validAddress), tooMuchData);
    }

    @Test(expected = NullPointerException.class)
    public void nullData() {
        new Message(null, new Peer(SMSPeerTest.validAddress), null);
    }

    @Test
    public void getDataTest() {
        Message message = new Message(null, new Peer(SMSPeerTest.validAddress), validData);
        Assert.assertEquals(message.getPayload(), validData);
    }

}