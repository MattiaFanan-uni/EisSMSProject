package com.gruppo3.smsconnection.replicatednet.dictionary.command;

import com.gruppo3.smsconnection.datalink.message.SMSPeerTest;
import com.gruppo3.smsconnection.replicatednet.dictionary.ReplicatedNetDictionary;
import com.gruppo3.smsconnection.replicatednet.message.ReplicatedNetPeer;
import com.gruppo3.smsconnection.replicatednet.message.ReplicatedNetPeerTest;
import com.gruppo3.smsconnection.smsdatalink.message.SMSPeer;

import org.junit.Assert;
import org.junit.Test;

public class AddResourceNetCommandTest {
    String key="AAA";
    String value="ZZZ";


    ReplicatedNetPeer netPeer;
    SMSPeer smsPeer;

    public AddResourceNetCommandTest() {

        try {
            netPeer = new ReplicatedNetPeer(ReplicatedNetPeerTest.validAddress);
            smsPeer = new SMSPeer(SMSPeerTest.validAddress);
        } catch (Exception e) { }
    }

    @Test
    public void setUp(){
        ReplicatedNetDictionary<String,String> dictionary=new ReplicatedNetDictionary<>(netPeer,smsPeer);
        try {
            AddResourceNetCommand c = dictionary.getAddResourceCommand(key, value);
        }catch (NullPointerException e){
            Assert.fail("shouldn't call NullPointerException"); }
    }

    @Test
    public void setUpNullReplicatedNetPeer(){
        ReplicatedNetDictionary<String,String> dictionary=new ReplicatedNetDictionary<>(netPeer,smsPeer);
        try {
            AddResourceNetCommand c = dictionary.getAddResourceCommand(null, value);
            Assert.fail("should call NullPointerException");
        }catch (NullPointerException e){ }//correct
    }

    @Test
    public void setUpNullSMSPeer(){
        ReplicatedNetDictionary<String,String> dictionary=new ReplicatedNetDictionary<>(netPeer,smsPeer);
        try {
            AddResourceNetCommand c = dictionary.getAddResourceCommand(key, null);
            Assert.fail("should call NullPointerException");
        }catch (NullPointerException e){ }//correct
    }

    @Test
    public void executeTest(){
        ReplicatedNetDictionary<String,String> dictionary=new ReplicatedNetDictionary<>(netPeer,smsPeer);

        AddResourceNetCommand c = dictionary.getAddResourceCommand(key, value);
        c.execute(dictionary);

        if(!dictionary.containsResourceKey(key))
            Assert.fail("resource key insertion doesn't work");

        if(!dictionary.containsResourceValue(value))
            Assert.fail("resource value insertion doesn't work");
    }
}
