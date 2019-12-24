package com.gruppo3.smslibrary;

import com.gruppo3.smslibrary.types.Message;
import com.gruppo3.smslibrary.types.Peer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.gruppo3.smslibrary.utils.Utils.getAlphaNumericString;

public class SMSMessageTest {
    private Peer validPeer;
    private String validPayload;

    @Before
    public void init() {
        validPayload = getAlphaNumericString(20);
        validPeer = new Peer(SMSPeerTest.getValidAddress());
    }


    @Test
    public void setUpBothPeers() {
        Message message = null;
        try {
            message = new Message(validPeer, validPeer, validPayload);
        } catch (IllegalArgumentException e) {
            Assert.fail("shouldn't throw InvalidMessageException");
        }

        Assert.assertFalse(message == null);
        Assert.assertEquals(message.getSource(), validPeer);
        Assert.assertEquals(message.getDestination(), validPeer);
        Assert.assertEquals(message.getPayload(), validPayload);
    }

    @Test
    public void setUpSource() {
        Message message = null;
        try {
            message = new Message(null, validPeer, validPayload);
        } catch (IllegalArgumentException e) {
            Assert.fail("shouldn't throw InvalidMessageException");
        }

        Assert.assertFalse(message == null);
        Assert.assertNull(message.getSource());
        Assert.assertEquals(message.getDestination(), validPeer);
        Assert.assertEquals(message.getPayload(), validPayload);
    }

    @Test
    public void setUpDestination() {
        Message message = null;
        try {
            message = new Message(validPeer, null, validPayload);
        } catch (IllegalArgumentException e) {
            Assert.fail("shouldn't throw InvalidMessageException");
        }

        Assert.assertFalse(message == null);
        Assert.assertEquals(message.getSource(), validPeer);
        Assert.assertNull(message.getDestination());
        Assert.assertEquals(message.getPayload(), validPayload);
    }

    @Test
    public void buildFromSDU() {
        //try to rebuilt the message from the string received from the getSdu method
        Message message = new Message(null, validPeer, validPayload);

        String SDU = message.getSDU();

        try {
            Message rebuildMessage = Message.buildFromSDU(validPeer.getPhoneNumber(), SDU);

            //test equivalence of all fields
            Assert.assertEquals(rebuildMessage.getPayload(), message.getPayload());
            //source is passed from outside
            Assert.assertEquals(rebuildMessage.getSource(), validPeer);
            //when a message is received destination is implicitly me then null
            Assert.assertNull(rebuildMessage.getDestination());

        } catch (IllegalArgumentException e) {
            Assert.fail("shouldn't throw InvalidMessageException");
        }
    }

    @Test
    public void toStringTestBothPeer() {

        Message message = new Message(validPeer, validPeer, validPayload);
        String expected = "Message:";
        expected = expected + message.getPayload();
        expected = expected + " ---Destination:" + message.getDestination().getPhoneNumber();
        expected = expected + " ---Source:" + message.getSource().getPhoneNumber();
        Assert.assertEquals(expected, message.toString());
    }

}
