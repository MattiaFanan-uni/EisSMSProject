package com.gruppo3.smsconnection.replicatednet.dictionary.command;

import com.gruppo3.smsconnection.datalink.message.SMSPeerTest;
import com.gruppo3.smsconnection.replicatednet.dictionary.ReplicatedNetDictionary;
import com.gruppo3.smsconnection.replicatednet.message.ReplicatedNetPeer;
import com.gruppo3.smsconnection.replicatednet.message.ReplicatedNetPeerTest;
import com.gruppo3.smsconnection.smsdatalink.message.SMSPeer;

import org.junit.Assert;
import org.junit.Test;

public class RemovePeerNetCommandTest {

    ReplicatedNetPeer netPeerA;
    SMSPeer smsPeerA;
    ReplicatedNetPeer netPeerB;
    SMSPeer smsPeerB;

    public RemovePeerNetCommandTest(){
        try {
            netPeerA =new ReplicatedNetPeer(ReplicatedNetPeerTest.validAddress);
            netPeerB=new ReplicatedNetPeer(ReplicatedNetPeerTest.greaterThanValidAddress);
            smsPeerA=new SMSPeer(SMSPeerTest.validAddress);
            smsPeerB=new SMSPeer(SMSPeerTest.validCountryCodeAddress);
        }catch (Exception e){}
    }

    @Test
    public void setUp(){
        ReplicatedNetDictionary<String,String> dictionary=new ReplicatedNetDictionary<>(netPeerA,smsPeerA);
        try {
            RemovePeerNetCommand c = dictionary.getRemovePeerNetCommand(netPeerA);
        }catch (NullPointerException e){
            Assert.fail("shouldn't call NullPointerException"); }
    }

    @Test
    public void setUpNullReplicatedNetPeer(){
        ReplicatedNetDictionary<String,String> dictionary=new ReplicatedNetDictionary<>(netPeerA,smsPeerA);
        try {
            RemovePeerNetCommand c = dictionary.getRemovePeerNetCommand(null);
            Assert.fail("should call NullPointerException");
        }catch (NullPointerException e){ }//correct
    }


    @Test
    public void executeTest(){
        ReplicatedNetDictionary<String,String> dictionary=new ReplicatedNetDictionary<>(netPeerA,smsPeerA);
        dictionary.putPeerIfAbsent(netPeerB,smsPeerB);
        RemovePeerNetCommand c = dictionary.getRemovePeerNetCommand(netPeerB);
        c.execute(dictionary);

        if(dictionary.containsPeerKey(netPeerB))
            Assert.fail("peer key delete doesn't work");

        if(dictionary.containsPeerValue(smsPeerB))
            Assert.fail("peer value delete doesn't work");
    }
}
