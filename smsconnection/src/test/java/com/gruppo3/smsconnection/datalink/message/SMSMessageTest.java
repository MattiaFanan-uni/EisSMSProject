package com.gruppo3.smsconnection.datalink.message;

import com.gruppo3.smsconnection.connection.exception.InvalidMessageException;
import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;
import com.gruppo3.smsconnection.smsdatalink.message.SMSMessage;
import com.gruppo3.smsconnection.smsdatalink.message.SMSPeer;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static com.gruppo3.smsconnection.utils.Utils.getAlphaNumericString;

public class SMSMessageTest {
    SMSPeer validPeer;
    SMSPeer nullPeer = null;
    String validPayload;


    public SMSMessageTest() {
        try {
            validPayload = getAlphaNumericString(20);
            validPeer = new SMSPeer(SMSPeerTest.validAddress);
        } catch (Exception e) {
        }
    }


    @Test
    public void setUpBothPeers() {
        SMSMessage message = null;
        try {
            message = new SMSMessage(validPeer, validPeer, validPayload);
        } catch (InvalidPeerException e) {
            Assert.fail("shouldn't throw InvalidPeerException");
        } catch (InvalidMessageException e) {
            Assert.fail("shouldn't throw InvalidMessageException");
        }

        if (message == null)
            Assert.fail("shouldn't be null");
        if (!message.getSourcePeer().equals(validPeer))
            Assert.fail("source peers should be the same");
        if (!message.getDestinationPeer().equals(validPeer))
            Assert.fail("destination peers should be the same");
        Assert.assertEquals(message.getData(), validPayload);
    }

    @Test
    public void setUpSource() {
        SMSMessage message = null;
        try {
            message = new SMSMessage(nullPeer, validPeer, validPayload);
        } catch (InvalidPeerException e) {
            Assert.fail("shouldn't throw InvalidPeerException");
        } catch (InvalidMessageException e) {
            Assert.fail("shouldn't throw InvalidMessageException");
        }

        if (message == null)
            Assert.fail("shouldn't be null");
        if (!message.getSourcePeer().equals(validPeer))
            Assert.fail("source peers should be the same");
        if (message.getDestinationPeer() != nullPeer)
            Assert.fail("destination peer should be null");
        Assert.assertEquals(message.getData(), validPayload);
    }

    @Test
    public void setUpDestination() {
        SMSMessage message = null;
        try {
            message = new SMSMessage(validPeer, nullPeer, validPayload);
        } catch (InvalidPeerException e) {
            Assert.fail("shouldn't throw InvalidPeerException");
        } catch (InvalidMessageException e) {
            Assert.fail("shouldn't throw InvalidMessageException");
        }

        if (message == null)
            Assert.fail("shouldn't be null");
        if (!message.getDestinationPeer().equals(validPeer))
            Assert.fail("source peers should be the same");
        if (message.getSourcePeer() != nullPeer)
            Assert.fail("destination peer should be null");
        Assert.assertEquals(message.getData(), validPayload);
    }

    @Test
    public void setUpBothNull() {
        SMSMessage message = null;
        try {
            message = new SMSMessage(nullPeer, nullPeer, validPayload);
            Assert.fail("should throw InvalidPeerException");
        } catch (InvalidPeerException e) {
        }//correct
        catch (InvalidMessageException e) {
            Assert.fail("shouldn't throw InvalidMessageException");
        }
    }

    @Test
    public void buildFromSDU() {
        SMSMessage message = null;
        try {
            message = new SMSMessage(nullPeer, validPeer, validPayload);
        } catch (InvalidPeerException e) {
            Assert.fail("shouldn't throw InvalidPeerException");
        } catch (InvalidMessageException e) {
            Assert.fail("shouldn't throw InvalidMessageException");
        }

        String SDU = message.getSDU();

        try {
            SMSMessage rebuildMessage = SMSMessage.buildFromSDU(validPeer.getAddress(), SDU);

            if (!rebuildMessage.getData().equals(message.getData()))
                Assert.fail("data should be unchanged");
            //null!=null
            if (rebuildMessage.getDestinationPeer() != message.getDestinationPeer())
                Assert.fail("destination should be unchanged");
            //address!=address
            if (rebuildMessage.getSourcePeer().getAddress().compareTo(message.getSourcePeer().getAddress()) != 0)
                Assert.fail("source should be unchanged");
        } catch (InvalidMessageException e) {
            Assert.fail("shouldn't throw InvalidMessageException");
        } catch (InvalidPeerException e) {
            Assert.fail("shouldn't throw InvalidPeerException");
        }
    }

    @Test
    public void toStringTestBothPeer() {
        SMSMessage message = null;
        try {
            message = new SMSMessage(validPeer, validPeer, validPayload);
        } catch (InvalidPeerException e) {
            Assert.fail("shouldn't throw InvalidPeerException");
        } catch (InvalidMessageException e) {
            Assert.fail("shouldn't throw InvalidMessageException");
        }

        String expected = "Message:";
        expected = expected + message.getData();
        expected = expected + " ---Destination:" + message.getDestinationPeer().getAddress();
        expected = expected + " ---Source:" + message.getSourcePeer().getAddress();
        Assert.assertEquals(expected, message.toString());
    }

}
