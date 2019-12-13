package com.gruppo3.smsconnection.utils;

import com.gruppo3.smsconnection.datalink.message.SMSPeerTest;
import com.gruppo3.smsconnection.smsdatalink.message.SMSPeer;

import org.junit.Assert;
import org.junit.Test;

public class SMSPeerParserTest {

    @Test
    public void toStringAndBack(){
        try {
            SMSPeer peer = new SMSPeer(SMSPeerTest.validAddress);

            String a = new SMSPeerParser().parseString(peer);

            SMSPeer peerB = new SMSPeerParser().parseData(a);

            Assert.assertEquals(peer, peerB);

        } catch (Exception e) {
            Assert.fail("error in ReplicatedNetPeerTest");
        }
    }
}
