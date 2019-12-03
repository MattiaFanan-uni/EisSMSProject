package com.gruppo3.smsconnection.replicatednet.dictionary.command;

import com.gruppo3.smsconnection.datalink.message.SMSPeerTest;
import com.gruppo3.smsconnection.replicatednet.dictionary.ReplicatedNetDictionary;
import com.gruppo3.smsconnection.replicatednet.message.ReplicatedNetPeer;
import com.gruppo3.smsconnection.replicatednet.message.ReplicatedNetPeerTest;
import com.gruppo3.smsconnection.smsdatalink.message.SMSPeer;

import org.junit.Assert;
import org.junit.Test;

public class AddPeerNetCommandTest {
    ReplicatedNetPeer netPeerA;
    SMSPeer smsPeerA;
    ReplicatedNetPeer netPeerB;
    SMSPeer smsPeerB;

    public AddPeerNetCommandTest(){
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
            AddPeerNetCommand c = dictionary.getAddPeerNetCommand(netPeerA, smsPeerA);
        }catch (NullPointerException e){Assert.fail("shouldn't call NullPointerException"); }
    }

    @Test
    public void setUpNullReplicatedNetPeer(){
        ReplicatedNetDictionary<String,String> dictionary=new ReplicatedNetDictionary<>(netPeerA,smsPeerA);

        try {
            AddPeerNetCommand c = dictionary.getAddPeerNetCommand(null, smsPeerA);
            Assert.fail("should call NullPointerException");
        }catch (NullPointerException e){ }//correct
    }

    @Test
    public void setUpNullSMSPeer(){
        ReplicatedNetDictionary<String,String> dictionary=new ReplicatedNetDictionary<>(netPeerA,smsPeerA);

        try {
            AddPeerNetCommand c = dictionary.getAddPeerNetCommand(netPeerA, null);
            Assert.fail("should call NullPointerException");
        }catch (NullPointerException e){ }//correct
    }

    @Test
    public void executeTest(){
        ReplicatedNetDictionary<String,String> dictionary=new ReplicatedNetDictionary<>(netPeerA,smsPeerA);
        AddPeerNetCommand c = dictionary.getAddPeerNetCommand(netPeerB, smsPeerB);
        c.execute(dictionary);

        if(!dictionary.containsPeerKey(netPeerB))
            Assert.fail("peer key insertion doesn't work");

        if(!dictionary.containsPeerValue(smsPeerB))
            Assert.fail("peer value insertion doesn't work");
    }
}
