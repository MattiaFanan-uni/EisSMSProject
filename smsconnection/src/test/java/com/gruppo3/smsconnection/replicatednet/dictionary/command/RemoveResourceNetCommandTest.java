package com.gruppo3.smsconnection.replicatednet.dictionary.command;

import com.gruppo3.smsconnection.datalink.message.SMSPeerTest;
import com.gruppo3.smsconnection.replicatednet.dictionary.ReplicatedNetDictionary;
import com.gruppo3.smsconnection.replicatednet.message.ReplicatedNetPeer;
import com.gruppo3.smsconnection.replicatednet.message.ReplicatedNetPeerTest;
import com.gruppo3.smsconnection.smsdatalink.message.SMSPeer;

import org.junit.Assert;
import org.junit.Test;

public class RemoveResourceNetCommandTest {

    String key="AAA";
    String value="ZZZ";


    ReplicatedNetPeer netPeer;
    SMSPeer smsPeer;

    public RemoveResourceNetCommandTest() {
        try {
            netPeer = new ReplicatedNetPeer(ReplicatedNetPeerTest.validAddress);
            smsPeer = new SMSPeer(SMSPeerTest.validAddress);
        } catch (Exception e) { }
    }

    @Test
    public void setUp(){
        ReplicatedNetDictionary<String,String> dictionary=new ReplicatedNetDictionary<>(netPeer,smsPeer);
        try {
            RemoveResourceNetCommand c = dictionary.getRemoveResourceCommand(key);
        }catch (NullPointerException e){
            Assert.fail("shouldn't call NullPointerException"); }
    }

    @Test
    public void setUpNullReplicatedNetPeer(){
        ReplicatedNetDictionary<String,String> dictionary=new ReplicatedNetDictionary<>(netPeer,smsPeer);
        try {
            RemoveResourceNetCommand c = dictionary.getRemoveResourceCommand(null);
            Assert.fail("shouldn't call NullPointerException");
        }catch (NullPointerException e){ }//correct
    }


    @Test
    public void executeTest(){
        ReplicatedNetDictionary<String,String> dictionary=new ReplicatedNetDictionary<>(netPeer,smsPeer);

        dictionary.putResourceIfAbsent(key,value);

        RemoveResourceNetCommand c = dictionary.getRemoveResourceCommand(key);
        c.execute(dictionary);

        if(dictionary.containsResourceKey(key))
            Assert.fail("peer key delete doesn't work");

        if(dictionary.containsResourceValue(value))
            Assert.fail("peer value delete doesn't work");
    }
}
