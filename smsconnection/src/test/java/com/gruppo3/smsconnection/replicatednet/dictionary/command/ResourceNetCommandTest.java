package com.gruppo3.smsconnection.replicatednet.dictionary.command;

import com.gruppo3.smsconnection.datalink.message.SMSPeerTest;
import com.gruppo3.smsconnection.replicatednet.dictionary.ReplicatedNetDictionary;
import com.gruppo3.smsconnection.replicatednet.message.ReplicatedNetPeer;
import com.gruppo3.smsconnection.replicatednet.message.ReplicatedNetPeerTest;
import com.gruppo3.smsconnection.smsdatalink.message.SMSPeer;
import com.gruppo3.smsconnection.utils.StringSelfParser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ResourceNetCommandTest {

    private ReplicatedNetPeer netPeerA;
    private SMSPeer smsPeerA;

    private static final String keyA = "A";
    private static final String valueA = "B";

    public static final StringParser<String> stringParser = new StringSelfParser();

    @Before
    public void init() {
        netPeerA = new ReplicatedNetPeer(ReplicatedNetPeerTest.validAddress);
        smsPeerA = new SMSPeer(SMSPeerTest.validAddress);

    }

    @Test
    public void setUp() {
        try {
            new ReplicatedResourceNetCommand<>(stringParser, stringParser);
        } catch (IllegalArgumentException e) {
            Assert.fail("shouldn't call NullPointerException");
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void setUpSomeNullParser() {
        new ReplicatedResourceNetCommand<>(null, stringParser);
        new ReplicatedResourceNetCommand<>(stringParser, null);
    }

    @Test
    public void executeInsertResourceNotPresent() {
        ReplicatedNetDictionary<String, String> dictionary = new ReplicatedNetDictionary<>(netPeerA, smsPeerA, stringParser, stringParser);
        //keyA-valueA is not in
        String insertCommand = dictionary.getAddResourceCommand(keyA, valueA);
        if (!dictionary.getResourceCommandExecutor().execute(dictionary, insertCommand))
            Assert.fail("should return true");

        if (!dictionary.containsResourceKey(keyA))
            Assert.fail("peer key insertion doesn't work");

        if (!dictionary.containsResourceValue(valueA))
            Assert.fail("peer value insertion doesn't work");
    }

    @Test
    public void executeInsertResourcePresent() {
        ReplicatedNetDictionary<String, String> dictionary = new ReplicatedNetDictionary<>(netPeerA, smsPeerA, stringParser, stringParser);
        //put keyA-valueA
        dictionary.putResourceIfAbsent(keyA, valueA);
        //keyA-valueA is in
        String insertCommand = dictionary.getAddResourceCommand(keyA, valueA);
        Assert.assertFalse(dictionary.getResourceCommandExecutor().execute(dictionary, insertCommand));
    }

    @Test
    public void executeRemoveResourcePresent() {
        ReplicatedNetDictionary<String, String> dictionary = new ReplicatedNetDictionary<>(netPeerA, smsPeerA, stringParser, stringParser);
        //put keyA-valueA
        dictionary.putResourceIfAbsent(keyA, valueA);
        //try to remove keyA-valueA
        String removeCommand = dictionary.getRemoveResourceCommand(keyA);
        if (!dictionary.getResourceCommandExecutor().execute(dictionary, removeCommand))
            Assert.fail("should return true");

        if (dictionary.containsResourceKey(keyA))
            Assert.fail("peer key deletion doesn't work");

        if (dictionary.containsResourceValue(valueA))
            Assert.fail("peer value deletion doesn't work");
    }

    @Test
    public void executeRemoveResourceNotPresent() {
        ReplicatedNetDictionary<String, String> dictionary = new ReplicatedNetDictionary<>(netPeerA, smsPeerA, stringParser, stringParser);
        //there is not keyA-valueA
        String removeCommand = dictionary.getRemoveResourceCommand(keyA);
        Assert.assertFalse(dictionary.getResourceCommandExecutor().execute(dictionary, removeCommand));

    }
}
