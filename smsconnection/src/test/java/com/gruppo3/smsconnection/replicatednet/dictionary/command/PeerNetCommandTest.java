package com.gruppo3.smsconnection.replicatednet.dictionary.command;

import com.gruppo3.smsconnection.datalink.message.SMSPeerTest;
import com.gruppo3.smsconnection.replicatednet.dictionary.ReplicatedNetDictionary;
import com.gruppo3.smsconnection.replicatednet.message.ReplicatedNetPeer;
import com.gruppo3.smsconnection.replicatednet.message.ReplicatedNetPeerTest;
import com.gruppo3.smsconnection.smsdatalink.message.SMSPeer;
import com.gruppo3.smsconnection.utils.ReplicatedNetPeerParser;
import com.gruppo3.smsconnection.utils.SMSPeerParser;
import com.gruppo3.smsconnection.utils.StringSelfParser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PeerNetCommandTest {
    ReplicatedNetPeer netPeerA;
    SMSPeer smsPeerA;
    ReplicatedNetPeer netPeerB;
    SMSPeer smsPeerB;
    public static final StringParser<ReplicatedNetPeer> netPeerParser = new ReplicatedNetPeerParser();
    public static final StringParser<SMSPeer> smsPeerParser = new SMSPeerParser();
    public static final StringParser<String> stringParser = new StringSelfParser();


    @Before
    public void init() {
        try {
            netPeerA = new ReplicatedNetPeer(ReplicatedNetPeerTest.validAddress);
            netPeerB = new ReplicatedNetPeer(ReplicatedNetPeerTest.greaterThanValidAddress);
            smsPeerA = new SMSPeer(SMSPeerTest.validAddress);
            smsPeerB = new SMSPeer(SMSPeerTest.validCountryCodeAddress);
        } catch (Exception e) {
        }
    }

    @Test
    public void setUp() {
        try {
            ReplicatedPeerNetCommand command = new ReplicatedPeerNetCommand(netPeerParser, smsPeerParser);
        } catch (NullPointerException e) {
            Assert.fail("shouldn't call NullPointerException");
        }
    }

    @Test(expected = NullPointerException.class)
    public void setUpSomeNullParser() {
        ReplicatedPeerNetCommand command = new ReplicatedPeerNetCommand(null, smsPeerParser);
        command = new ReplicatedPeerNetCommand(netPeerParser, null);
    }

    @Test
    public void executeInsertPeerNotPresent() {
        ReplicatedNetDictionary<String, String> dictionary = new ReplicatedNetDictionary<>(netPeerA, smsPeerA, stringParser, stringParser);
        //B-B is not in
        String insertCommand = dictionary.getAddPeerNetCommand(netPeerB, smsPeerB);
        if (!dictionary.getPeerCommandExecutor().execute(dictionary, insertCommand))
            Assert.fail("should return true");

        if (!dictionary.containsPeerKey(netPeerB))
            Assert.fail("peer key insertion doesn't work");

        if (!dictionary.containsPeerValue(smsPeerB))
            Assert.fail("peer value insertion doesn't work");
    }

    @Test
    public void executeInsertPeerPresent() {
        ReplicatedNetDictionary<String, String> dictionary = new ReplicatedNetDictionary<>(netPeerA, smsPeerA, stringParser, stringParser);
        //B-B is not in
        String insertCommand = dictionary.getAddPeerNetCommand(netPeerA, smsPeerA);
        Assert.assertFalse(dictionary.getPeerCommandExecutor().execute(dictionary, insertCommand));
    }

    @Test
    public void executeRemovePeerPresent() {
        ReplicatedNetDictionary<String, String> dictionary = new ReplicatedNetDictionary<>(netPeerA, smsPeerA, stringParser, stringParser);
        dictionary.putPeerIfAbsent(netPeerB, smsPeerB);
        //B-B is in
        String removeCommand = dictionary.getRemovePeerNetCommand(netPeerB);
        if (!dictionary.getPeerCommandExecutor().execute(dictionary, removeCommand))
            Assert.fail("should return true");

        if (dictionary.containsPeerKey(netPeerB))
            Assert.fail("peer key deletion doesn't work");

        if (dictionary.containsPeerValue(smsPeerB))
            Assert.fail("peer value deletion doesn't work");
    }

    @Test
    public void executeRemovePeerNotPresent() {
        ReplicatedNetDictionary<String, String> dictionary = new ReplicatedNetDictionary<>(netPeerA, smsPeerA, stringParser, stringParser);
        //B-B is not in
        String removeCommand = dictionary.getRemovePeerNetCommand(netPeerB);
        Assert.assertFalse(dictionary.getPeerCommandExecutor().execute(dictionary, removeCommand));
    }

}
