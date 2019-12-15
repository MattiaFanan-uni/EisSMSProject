package com.gruppo3.smsconnection.replicatednet.dictionary;

import com.gruppo3.smsconnection.datalink.message.SMSPeerTest;
import com.gruppo3.smsconnection.replicatednet.message.ReplicatedNetPeer;
import com.gruppo3.smsconnection.replicatednet.message.ReplicatedNetPeerTest;
import com.gruppo3.smsconnection.smsdatalink.message.SMSPeer;
import com.gruppo3.smsconnection.utils.StringSelfParser;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.Map;

public class ReplicatedNetDictionaryPeerTest {
    private ReplicatedNetPeer netPeer;
    private ReplicatedNetPeer nullNetPeer = null;
    private ReplicatedNetPeer greaterNetPeer;
    private ReplicatedNetPeer lowerNetPeer;
    private SMSPeer smsPeer;
    private SMSPeer nullSmsPeer = null;
    private SMSPeer otherSmsPeer;


    @Before
    public void init() {
        netPeer = new ReplicatedNetPeer(ReplicatedNetPeerTest.validAddress);
        greaterNetPeer = new ReplicatedNetPeer(ReplicatedNetPeerTest.greaterThanValidAddress);
        lowerNetPeer = new ReplicatedNetPeer(ReplicatedNetPeerTest.lowerThanValidAddress);
        smsPeer = new SMSPeer(SMSPeerTest.validAddress);
        otherSmsPeer = new SMSPeer(SMSPeerTest.validCountryCodeAddress);
    }

    //////////////////////////////////////////////////ADD
    @Test
    public void addPeersAbsent() {
        ReplicatedNetDictionary<String, String> dict = new ReplicatedNetDictionary<>(netPeer, smsPeer, new StringSelfParser(), new StringSelfParser());

        try {
            SMSPeer returnedSMSPeer = dict.putPeerIfAbsent(greaterNetPeer, otherSmsPeer);
            Assert.assertNotNull(returnedSMSPeer);
            Assert.assertEquals(2, dict.numberOfPeers());
        } catch (NullPointerException e) {
            Assert.fail("shouldn't throw NullPointerException");
        } catch (IllegalArgumentException e) {
            Assert.fail("shouldn't throw IllegalArgumentException");
        }
    }

    @Test
    public void addPeersAlreadyIn() {
        ReplicatedNetDictionary<String, String> dict = new ReplicatedNetDictionary<>(netPeer, smsPeer, new StringSelfParser(), new StringSelfParser());

        try {
            SMSPeer returnedSMSPeer = dict.putPeerIfAbsent(netPeer, otherSmsPeer);

            Assert.assertEquals(returnedSMSPeer, smsPeer);
        } catch (NullPointerException e) {
            Assert.fail("shouldn't throw NullPointerException");
        } catch (IllegalArgumentException e) {
            Assert.fail("shouldn't throw IllegalArgumentException");
        }
    }

    @Test(expected = NullPointerException.class)
    public void addPeersNullKey() {
        new ReplicatedNetDictionary<>(
                netPeer,
                smsPeer,
                new StringSelfParser(),
                new StringSelfParser()
        ).putPeerIfAbsent(nullNetPeer, otherSmsPeer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addPeersNullValue() {
        new ReplicatedNetDictionary<>(
                netPeer,
                smsPeer,
                new StringSelfParser(),
                new StringSelfParser()
        ).putPeerIfAbsent(netPeer, nullSmsPeer);
    }

    //////////////////////////////////////////////////REMOVE
    @Test
    public void removePeerAbsent() {
        ReplicatedNetDictionary<String, String> dict = new ReplicatedNetDictionary<>(netPeer, smsPeer, new StringSelfParser(), new StringSelfParser());
        dict.putPeerIfAbsent(greaterNetPeer, smsPeer);

        try {
            Assert.assertNotNull(dict.removePeer(lowerNetPeer));
        } catch (NullPointerException e) {
            Assert.fail("shouldn't throw NullPointerException");
        }
    }

    @Test
    public void removePeersAlreadyIn() {
        ReplicatedNetDictionary<String, String> dict = new ReplicatedNetDictionary<>(netPeer, smsPeer, new StringSelfParser(), new StringSelfParser());
        dict.putPeerIfAbsent(greaterNetPeer, otherSmsPeer);

        try {
            Assert.assertEquals(dict.removePeer(greaterNetPeer), otherSmsPeer);
        } catch (NullPointerException e) {
            Assert.fail("shouldn't throw NullPointerException");
        }
    }

    @Test(expected = NullPointerException.class)
    public void removePeersNullKey() {
        ReplicatedNetDictionary<String, String> dict = new ReplicatedNetDictionary<>(netPeer, smsPeer, new StringSelfParser(), new StringSelfParser());
        dict.putPeerIfAbsent(greaterNetPeer, smsPeer);//if <2 peers return null

        dict.removePeer(nullNetPeer);
    }

    @Test
    public void removePeersLastPeerCount() {
        ReplicatedNetDictionary<String, String> dict = new ReplicatedNetDictionary<>(netPeer, smsPeer, new StringSelfParser(), new StringSelfParser());

        try {
            //when i try to remove last peer it always return null
            Assert.assertNull(dict.removePeer(netPeer));

            Assert.assertEquals(1, dict.numberOfPeers());
        } catch (NullPointerException e) {
            Assert.fail("shouldn't throw NullPointerException");
        }
    }


    //////////////////////////////////////////////////////////NUMBER_OF_PEERS
    @Test
    public void numberOfPeers() {
        ReplicatedNetDictionary<String, String> dict = null;
        dict = new ReplicatedNetDictionary<>(netPeer, smsPeer, new StringSelfParser(), new StringSelfParser());
        dict.putPeerIfAbsent(greaterNetPeer, smsPeer);
        dict.putPeerIfAbsent(lowerNetPeer, otherSmsPeer);

        Assert.assertEquals(3, dict.numberOfPeers());
    }

    //////////////////////////////////////////////////ITERATOR

    @Test
    public void getIterator() {
        ReplicatedNetDictionary<String, String> dict = new ReplicatedNetDictionary<>(netPeer, smsPeer, new StringSelfParser(), new StringSelfParser());
        dict.putPeerIfAbsent(greaterNetPeer, smsPeer);
        dict.putPeerIfAbsent(lowerNetPeer, otherSmsPeer);

        Iterator<Map.Entry<ReplicatedNetPeer, SMSPeer>> iterator = dict.getPeersIteratorAscending();

        Assert.assertNotNull(iterator);
    }

    @Test
    public void getIteratorRightNumberOfItems() {
        ReplicatedNetDictionary<String, String> dict = new ReplicatedNetDictionary<>(netPeer, smsPeer, new StringSelfParser(), new StringSelfParser());
        dict.putPeerIfAbsent(greaterNetPeer, smsPeer);
        dict.putPeerIfAbsent(lowerNetPeer, otherSmsPeer);

        Iterator<Map.Entry<ReplicatedNetPeer, SMSPeer>> iterator = dict.getPeersIteratorAscending();

        int numberOfItems = 0;

        while (iterator.hasNext()) {
            iterator.next();
            numberOfItems++;
        }

        Assert.assertEquals(3, numberOfItems);
    }

    @Test
    public void getIteratorRightItemsOrderAscending() {
        ReplicatedNetDictionary<String, String> dict = new ReplicatedNetDictionary<>(netPeer, smsPeer, new StringSelfParser(), new StringSelfParser());
        dict.putPeerIfAbsent(greaterNetPeer, smsPeer);
        dict.putPeerIfAbsent(lowerNetPeer, otherSmsPeer);

        Iterator<Map.Entry<ReplicatedNetPeer, SMSPeer>> iterator = dict.getPeersIteratorAscending();

        //first item lower
        Map.Entry<ReplicatedNetPeer, SMSPeer> firstElement = iterator.next();
        Assert.assertEquals(firstElement.getKey(),lowerNetPeer);

        //second item
        Map.Entry<ReplicatedNetPeer, SMSPeer> secondElement = iterator.next();
        Assert.assertEquals(secondElement.getKey(),netPeer);

        //last item greater
        Map.Entry<ReplicatedNetPeer, SMSPeer> lastElement = iterator.next();
        Assert.assertEquals(lastElement.getKey(),greaterNetPeer);
    }
}