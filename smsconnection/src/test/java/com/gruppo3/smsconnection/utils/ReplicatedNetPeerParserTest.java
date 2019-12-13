package com.gruppo3.smsconnection.utils;

import com.gruppo3.smsconnection.replicatednet.message.ReplicatedNetPeer;
import com.gruppo3.smsconnection.replicatednet.message.ReplicatedNetPeerTest;

import org.junit.Assert;
import org.junit.Test;

public class ReplicatedNetPeerParserTest {

    @Test
    public void toStringAndBack() {
        try {
            ReplicatedNetPeer peer = new ReplicatedNetPeer(ReplicatedNetPeerTest.validAddress);

            String a = new ReplicatedNetPeerParser().parseString(peer);

            ReplicatedNetPeer peerB = new ReplicatedNetPeerParser().parseData(a);

            Assert.assertEquals(peer, peerB);

        } catch (Exception e) {
            Assert.fail("error in ReplicatedNetPeerTest");
        }

    }
}
