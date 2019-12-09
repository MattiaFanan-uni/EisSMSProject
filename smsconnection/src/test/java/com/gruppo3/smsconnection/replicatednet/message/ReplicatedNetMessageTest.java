package com.gruppo3.smsconnection.replicatednet.message;

import com.gruppo3.smsconnection.connection.exception.InvalidMessageException;
import com.gruppo3.smsconnection.connection.exception.InvalidPayloadException;
import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static com.gruppo3.smsconnection.utils.Utils.getAlphaNumericString;

public class ReplicatedNetMessageTest {
    ReplicatedNetPeer validPeer;
    ReplicatedNetPeer nullPeer = null;
    byte[] validPayolad;


    public ReplicatedNetMessageTest() {
        try {
            validPayolad = getAlphaNumericString(10).getBytes("UTF-16");
            validPeer = new ReplicatedNetPeer(ReplicatedNetPeerTest.validAddress);
        } catch (Exception e) {
        }
    }


    @Test
    public void setUpBothPeers() {
        ReplicatedNetMessage message = null;
        try {
            message = new ReplicatedNetMessage(validPeer, validPeer, validPayolad);
        } catch (InvalidPeerException e) {
            Assert.fail("shouldn't throw InvalidPeerException");
        } catch (InvalidPayloadException e) {
            Assert.fail("shouldn't throw InvalidPayloadException");
        }

        if (message == null)
            Assert.fail("shouldn't be null");
        if (!message.getSourcePeer().equals(validPeer))
            Assert.fail("source peers should be the same");
        if (!message.getDestinationPeer().equals(validPeer))
            Assert.fail("destination peers should be the same");
        if (!Arrays.equals(message.getData(), validPayolad))
            Assert.fail("data should be the same");
    }

    @Test
    public void setUpSource() {
        ReplicatedNetMessage message = null;
        try {
            message = new ReplicatedNetMessage(null, validPeer, validPayolad);
            Assert.fail("should throw InvalidPeerException");
        } catch (InvalidPeerException e) {
        }//correct
        catch (InvalidPayloadException e) {
            Assert.fail("shouldn't throw InvalidPayloadException");
        }
    }

    @Test
    public void setUpDestination() {
        ReplicatedNetMessage message = null;
        try {
            message = new ReplicatedNetMessage(validPeer, null, validPayolad);
            Assert.fail("should throw InvalidPeerException");
        } catch (InvalidPeerException e) {
        }//correct
        catch (InvalidPayloadException e) {
            Assert.fail("shouldn't throw InvalidPayloadException");
        }
    }

    @Test
    public void setUpBothNull() {
        ReplicatedNetMessage message = null;
        try {
            message = new ReplicatedNetMessage(null, null, validPayolad);
            Assert.fail("should throw InvalidPeerException");
        } catch (InvalidPeerException e) {
        }//correct
        catch (InvalidPayloadException e) {
            Assert.fail("shouldn't throw InvalidPayloadException");
        }
    }


    @Test
    public void buildFromSDU() {
        ReplicatedNetMessage message = null;
        try {
            message = new ReplicatedNetMessage(validPeer, validPeer, validPayolad);
        } catch (InvalidPeerException e) {
            Assert.fail("shouldn't throw InvalidPeerException");
        } catch (InvalidPayloadException e) {
            Assert.fail("shouldn't throw InvalidPayloadException");
        }

        byte[] SDU = message.getSDU();

        try {
            ReplicatedNetMessage rebuildMessage = ReplicatedNetMessage.buildFromSDU(SDU);

            if (!Arrays.equals(rebuildMessage.getData(), message.getData()))
                Assert.fail("data should be unchanged");

            //address!=address
            if (!Arrays.equals(
                    rebuildMessage.getDestinationPeer().getAddress(),
                    message.getDestinationPeer().getAddress()
            ))
                Assert.fail("destination should be unchanged");

            //address!=address
            if (!Arrays.equals(
                    rebuildMessage.getSourcePeer().getAddress(),
                    message.getSourcePeer().getAddress()
            ))
                Assert.fail("source should be unchanged");
        } catch (InvalidPayloadException e) {
            Assert.fail("shouldn't throw InvalidPayloadException");
        } catch (InvalidPeerException e) {
            Assert.fail("shouldn't throw InvalidPeerException");
        } catch (InvalidMessageException e) {
            Assert.fail("shouldn't throw InvalidMessageException");
        }

    }

}
