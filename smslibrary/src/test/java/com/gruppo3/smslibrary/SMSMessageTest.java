package com.gruppo3.smslibrary;


import com.gruppo3.smslibrary.exceptions.InvalidMessageException;
import com.gruppo3.smslibrary.exceptions.InvalidPeerException;
import com.gruppo3.smslibrary.types.Message;
import com.gruppo3.smslibrary.types.Peer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.gruppo3.smslibrary.Utils.Utils.getAlphaNumericString;

public class SMSMessageTest {
    Peer validPeer;
    String validPayload;

    @Before
    public void init() {
        validPayload = getAlphaNumericString(20);
        validPeer = new Peer(SMSPeerTest.validAddress);
    }


    @Test
    public void setUpBothPeers() {
        Message message = null;
        try {
            message = new Message(validPeer, validPeer, validPayload);
        } catch (InvalidMessageException e) {
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
        } catch (InvalidMessageException e) {
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
        } catch (InvalidMessageException e) {
            Assert.fail("shouldn't throw InvalidMessageException");
        }

        Assert.assertFalse(message == null);
        Assert.assertEquals(message.getSource(), validPeer);
        Assert.assertNull(message.getDestination());
        Assert.assertEquals(message.getPayload(), validPayload);
    }

    @Test(expected = InvalidMessageException.class)
    public void setUpBothNull() {
        new Message(null, null, validPayload);
    }

    @Test
    public void buildFromSDU() {
        //try to rebuilt the message from the string received from the getSdu method
        Message message = new Message(null, validPeer, validPayload);

        String SDU = message.getSDU();

        try {
            Message rebuildMessage = Message.buildFromSDU(validPeer.getAddress(), SDU);

            //test equivalence of all fields
            Assert.assertEquals(rebuildMessage.getPayload(), message.getPayload());
            //source is passed from outside
            Assert.assertEquals(rebuildMessage.getSource(), validPeer);
            //when a message is received destination is implicitly me then null
            Assert.assertNull(rebuildMessage.getDestination());

        } catch (InvalidMessageException e) {
            Assert.fail("shouldn't throw InvalidMessageException");
        } catch (InvalidPeerException e) {
            Assert.fail("shouldn't throw InvalidPeerException");
        }
    }

    @Test
    public void toStringTestBothPeer() {

        Message message = new Message(validPeer, validPeer, validPayload);
        String expected = "Message:";
        expected = expected + message.getPayload();
        expected = expected + " ---Destination:" + message.getDestination().getAddress();
        expected = expected + " ---Source:" + message.getSource().getAddress();
        Assert.assertEquals(expected, message.toString());
    }

}
