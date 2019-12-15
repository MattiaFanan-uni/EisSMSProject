package com.gruppo3.smsconnection.replicatednet.message;


import com.gruppo3.smsconnection.connection.exception.InvalidMessageException;
import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.gruppo3.smsconnection.utils.Utils.getAlphaNumericString;

public class ReplicatedNetMessageDataTest {

    private String validData;
    private String nullData = null;
    private String tooMuchData;
    private String maxData;

    @Before
    public void init() {
        validData = getAlphaNumericString(10);
        tooMuchData = getAlphaNumericString(ReplicatedNetMessage.MAX_PAYLOAD_LENGTH + 6);
        maxData = getAlphaNumericString(ReplicatedNetMessage.MAX_PAYLOAD_LENGTH);
    }

    @Test
    public void setUp() {
        try {
            new ReplicatedNetMessage(
                    new ReplicatedNetPeer(ReplicatedNetPeerTest.validAddress),
                    new ReplicatedNetPeer(ReplicatedNetPeerTest.validAddress),
                    validData);
        } catch (InvalidMessageException e) {
            Assert.fail("Should not throw InvalidMessageException exception");
        } catch (InvalidPeerException e) {
            Assert.fail("Should not throw InvalidPeerException exception");
        }
    }

    @Test
    public void maxData() {
        try {
            new ReplicatedNetMessage(
                    new ReplicatedNetPeer(ReplicatedNetPeerTest.validAddress),
                    new ReplicatedNetPeer(ReplicatedNetPeerTest.validAddress),
                    maxData);
        } catch (InvalidMessageException e) {
            Assert.fail("Should not throw InvalidMessageException exception");
        } catch (InvalidPeerException e) {
            Assert.fail("Should not throw InvalidPeerException exception");
        }
    }

    @Test(expected = InvalidMessageException.class)
    public void tooMuchData() {
        new ReplicatedNetMessage(
                    new ReplicatedNetPeer(ReplicatedNetPeerTest.validAddress),
                    new ReplicatedNetPeer(ReplicatedNetPeerTest.validAddress),
                    tooMuchData);
    }

    @Test(expected = NullPointerException.class)
    public void nullData() {
        new ReplicatedNetMessage(
                    new ReplicatedNetPeer(ReplicatedNetPeerTest.validAddress),
                    new ReplicatedNetPeer(ReplicatedNetPeerTest.validAddress),
                    nullData);
    }

    @Test
    public void getDataTest() {
        ReplicatedNetMessage message = new ReplicatedNetMessage(
                    new ReplicatedNetPeer(ReplicatedNetPeerTest.validAddress),
                    new ReplicatedNetPeer(ReplicatedNetPeerTest.validAddress),
                    validData);

        Assert.assertEquals(message.getData(),validData);
    }

}