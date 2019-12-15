package com.gruppo3.smsconnection.replicatednet.dictionary;

import com.gruppo3.smsconnection.datalink.message.SMSPeerTest;
import com.gruppo3.smsconnection.replicatednet.message.ReplicatedNetPeer;
import com.gruppo3.smsconnection.replicatednet.message.ReplicatedNetPeerTest;
import com.gruppo3.smsconnection.smsdatalink.message.SMSPeer;
import com.gruppo3.smsconnection.utils.StringSelfParser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class ReplicatedNetDictionaryTest {

    private ReplicatedNetPeer netPeer;
    private ReplicatedNetPeer nullNetPeer = null;
    private SMSPeer smsPeer;
    private SMSPeer nullSmsPeer = null;


    @Before
    public void init() {
        netPeer = new ReplicatedNetPeer(ReplicatedNetPeerTest.validAddress);
        smsPeer = new SMSPeer(SMSPeerTest.validAddress);
    }

    @Test
    public void setUp() {
        try {
            ReplicatedNetDictionary<String, String> dict = new ReplicatedNetDictionary<>(netPeer, smsPeer, new StringSelfParser(), new StringSelfParser());
            if (dict.numberOfPeers() != 1)
                Assert.fail("should start with 1 peer");
        } catch (NullPointerException e) {
            Assert.fail("shouldn't throw NullPointerException");
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void setUpNullNetPeer() {
        new ReplicatedNetDictionary<>(nullNetPeer, smsPeer, new StringSelfParser(), new StringSelfParser());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setUpNullSMSPeer() {
        new ReplicatedNetDictionary<>(netPeer, nullSmsPeer, new StringSelfParser(), new StringSelfParser());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setUpNullResourceKeyParser() {
        new ReplicatedNetDictionary<>(netPeer, smsPeer, null, new StringSelfParser());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setUpNullResourceValueParser() {
        new ReplicatedNetDictionary<>(netPeer, smsPeer, new StringSelfParser(), null);
    }

}
