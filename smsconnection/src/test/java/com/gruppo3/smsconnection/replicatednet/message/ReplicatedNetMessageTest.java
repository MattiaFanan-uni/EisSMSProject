package com.gruppo3.smsconnection.replicatednet.message;

import com.gruppo3.smsconnection.connection.exception.InvalidMessageException;
import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.gruppo3.smsconnection.utils.Utils.getAlphaNumericString;

public class ReplicatedNetMessageTest {
    private ReplicatedNetPeer validPeer;
    private ReplicatedNetPeer nullPeer = null;
    private String validPayload;


    @Before
    public void init() {
        validPayload = getAlphaNumericString(10);
        validPeer = new ReplicatedNetPeer(ReplicatedNetPeerTest.validAddress);
    }


    @Test
    public void setUpBothPeers() {
        ReplicatedNetMessage message = null;
        try {
            message = new ReplicatedNetMessage(validPeer, validPeer, validPayload);
        } catch (InvalidPeerException e) {
            Assert.fail("shouldn't throw InvalidPeerException");
        } catch (InvalidMessageException e) {
            Assert.fail("shouldn't throw InvalidMessageException");
        }

        Assert.assertNotNull(message);
        Assert.assertEquals(message.getSourcePeer(), validPeer);
        Assert.assertEquals(message.getDestinationPeer(), validPeer);
        Assert.assertEquals(message.getData(), validPayload);
    }

    @Test
    public void setUpSource() {
        try {
            new ReplicatedNetMessage(null, validPeer, validPayload);
        } catch (InvalidPeerException e) {
            Assert.fail("shouldn't throw InvalidPeerException");
        } catch (InvalidMessageException e) {
            Assert.fail("shouldn't throw InvalidMessageException");
        }
    }

    @Test
    public void setUpDestination() {
        try {
            new ReplicatedNetMessage(validPeer, null, validPayload);
        } catch (InvalidPeerException e) {
            Assert.fail("shouldn't throw InvalidPeerException");
        } catch (InvalidMessageException e) {
            Assert.fail("shouldn't throw InvalidMessageException");
        }
    }

    @Test(expected = InvalidPeerException.class)
    public void setUpBothNull() {
        new ReplicatedNetMessage(null, null, validPayload);
    }


    @Test
    public void buildFromSDU() {
        ReplicatedNetMessage message = new ReplicatedNetMessage(validPeer, validPeer, validPayload);

        String SDU = message.getSDU();

        try {
            ReplicatedNetMessage rebuildMessage = ReplicatedNetMessage.buildFromSDU(SDU);

            Assert.assertEquals(rebuildMessage.getData(), message.getData());
            Assert.assertEquals(rebuildMessage.getSourcePeer(), message.getSourcePeer());

        } catch (InvalidPeerException e) {
            Assert.fail("shouldn't throw InvalidPeerException");
        } catch (InvalidMessageException e) {
            Assert.fail("shouldn't throw InvalidMessageException");
        }

    }

}
