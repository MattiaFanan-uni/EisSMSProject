package com.gruppo3.smsconnection.replicatednet.dictionary;

import com.gruppo3.smsconnection.datalink.message.SMSPeerTest;
import com.gruppo3.smsconnection.replicatednet.message.ReplicatedNetPeer;
import com.gruppo3.smsconnection.replicatednet.message.ReplicatedNetPeerTest;
import com.gruppo3.smsconnection.smsdatalink.message.SMSPeer;

import org.junit.Assert;
import org.junit.Test;


public class ReplicatedNetDictionaryTest {

    ReplicatedNetPeer netPeer;
    ReplicatedNetPeer nullNetPeer=null;
    SMSPeer smsPeer;
    SMSPeer nullSmsPeer=null;


    public ReplicatedNetDictionaryTest(){
        try {
            netPeer = new ReplicatedNetPeer(ReplicatedNetPeerTest.validAddress);
            smsPeer=new SMSPeer(SMSPeerTest.validAddress);
        }
        catch (Exception e){}
    }

    @Test
    public void setUp(){
        ReplicatedNetDictionary<String,String> dict=null;
        try{
            dict=new ReplicatedNetDictionary<>(netPeer,smsPeer);
            if(dict.numberOfPeers()!=1)
                Assert.fail("should start with 1 peer");
        }
        catch (NullPointerException e){ Assert.fail("shouldn't throw NullPointerException");}
    }

    @Test
    public void setUpNullNetPeer(){
        ReplicatedNetDictionary<String,String> dict=null;
        try{
            dict=new ReplicatedNetDictionary<>(nullNetPeer,smsPeer);
            Assert.fail("should throw NullPointerException");
        }
        catch (NullPointerException e){}//correct
    }

    @Test
    public void setUpNullSMSPeer(){
        ReplicatedNetDictionary<String,String> dict=null;
        try{
            dict=new ReplicatedNetDictionary<>(netPeer,nullSmsPeer);
            Assert.fail("should throw NullPointerException");
        }
        catch (NullPointerException e){}//correct
    }

}
