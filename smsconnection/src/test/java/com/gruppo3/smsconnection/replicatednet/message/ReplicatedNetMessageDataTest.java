package com.gruppo3.smsconnection.replicatednet.message;


import com.gruppo3.smsconnection.connection.exception.InvalidMessageException;
import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static com.gruppo3.smsconnection.utils.Utils.getAlphaNumericString;

public class ReplicatedNetMessageDataTest {

    String validData;
    String nullData = null;
    String tooMuchData;
    String maxData;


    public ReplicatedNetMessageDataTest() {
        validData = getAlphaNumericString(10);
        tooMuchData = getAlphaNumericString(ReplicatedNetMessage.MAX_PAYLOAD_LENGTH + 6);
        maxData = getAlphaNumericString(ReplicatedNetMessage.MAX_PAYLOAD_LENGTH);
    }

    @Test
    public void setUp() {
        ReplicatedNetMessage message;
        try {
            message = new ReplicatedNetMessage(
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
        ReplicatedNetMessage message;
        try {
            message = new ReplicatedNetMessage(
                    new ReplicatedNetPeer(ReplicatedNetPeerTest.validAddress),
                    new ReplicatedNetPeer(ReplicatedNetPeerTest.validAddress),
                    maxData);
        } catch (InvalidMessageException e) {
            Assert.fail("Should not throw InvalidMessageException exception");
        } catch (InvalidPeerException e) {
            Assert.fail("Should not throw InvalidPeerException exception");
        }
    }

    @Test
    public void tooMuchData() {
        ReplicatedNetMessage message;
        try {
            message = new ReplicatedNetMessage(
                    new ReplicatedNetPeer(ReplicatedNetPeerTest.validAddress),
                    new ReplicatedNetPeer(ReplicatedNetPeerTest.validAddress),
                    tooMuchData);
            Assert.fail("Should throw InvalidMessageException ");
        } catch (InvalidMessageException e) { }//correct
        catch (InvalidPeerException e) {
            Assert.fail("Should not throw InvalidPeerException exception");
        }
    }

    @Test
    public void nullData() {
        ReplicatedNetMessage message;
        try {
            message = new ReplicatedNetMessage(
                    new ReplicatedNetPeer(ReplicatedNetPeerTest.validAddress),
                    new ReplicatedNetPeer(ReplicatedNetPeerTest.validAddress),
                    nullData);

            Assert.fail("Should throw NullPointerException ");
        } catch (NullPointerException e) {
        } //correct
        catch (Exception e) {
            Assert.fail("Shouldn't throw this Exception");
        }
    }

    @Test
    public void getDataTest() {
        ReplicatedNetMessage message;
        try {
            message = new ReplicatedNetMessage(
                    new ReplicatedNetPeer(ReplicatedNetPeerTest.validAddress),
                    new ReplicatedNetPeer(ReplicatedNetPeerTest.validAddress),
                    validData);
            if (!message.getData().equals(validData))
                Assert.fail("should be the same data");
        } catch (InvalidMessageException e) {
            Assert.fail("Shouldn't throw InvalidMessageException");
        } catch (Exception e) {
            Assert.fail("Shouldn't throw this Exception");
        }
    }

}