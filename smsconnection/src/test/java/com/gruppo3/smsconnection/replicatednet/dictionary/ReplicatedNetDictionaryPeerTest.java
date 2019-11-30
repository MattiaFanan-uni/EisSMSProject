package com.gruppo3.smsconnection.replicatednet.dictionary;

import com.gruppo3.smsconnection.datalink.message.SMSPeerTest;
import com.gruppo3.smsconnection.replicatednet.message.ReplicatedNetPeer;
import com.gruppo3.smsconnection.replicatednet.message.ReplicatedNetPeerTest;
import com.gruppo3.smsconnection.smsdatalink.message.SMSPeer;

import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;
import java.util.Map;

public class ReplicatedNetDictionaryPeerTest {
    ReplicatedNetPeer netPeer;
    ReplicatedNetPeer nullNetPeer=null;
    ReplicatedNetPeer greaterNetPeer;
    ReplicatedNetPeer lowerNetPeer;
    SMSPeer smsPeer;
    SMSPeer nullSmsPeer=null;
    SMSPeer otherSmsPeer;


    public ReplicatedNetDictionaryPeerTest(){
        try {
            netPeer = new ReplicatedNetPeer(ReplicatedNetPeerTest.validAddress);
            greaterNetPeer = new ReplicatedNetPeer(ReplicatedNetPeerTest.greaterThanValidAddress);
            lowerNetPeer = new ReplicatedNetPeer(ReplicatedNetPeerTest.lowerThanValidAddress);
            smsPeer=new SMSPeer(SMSPeerTest.validAddress);
            otherSmsPeer=new SMSPeer(SMSPeerTest.validCountryCodeAddress);
        }
        catch (Exception e){}
    }
//////////////////////////////////////////////////ADD
    @Test
    public void addPeersAbsent(){
        ReplicatedNetDictionary<String,String> dict=null;
        try{
            dict=new ReplicatedNetDictionary<>(netPeer,smsPeer);
        }
        catch (NullPointerException e){ Assert.fail("Error in ReplicatedNetDictionaryTest");}
        try{
            SMSPeer returnedSMSPeer=dict.putPeerIfAbsent(greaterNetPeer,otherSmsPeer);
            if(returnedSMSPeer!=null)
                Assert.fail("should return null");
            if(dict.numberOfPeers()!=2)
                Assert.fail("number of peers should be 2");
        }
        catch (NullPointerException e){Assert.fail("shouldn't throw NullPointerException");}
    }

    @Test
    public void addPeersAlreadyIn(){
        ReplicatedNetDictionary<String,String> dict=null;
        try{
            dict=new ReplicatedNetDictionary<>(netPeer,smsPeer);
        }
        catch (NullPointerException e){ Assert.fail("Error in ReplicatedNetDictionaryTest");}
        try{
            SMSPeer returnedSMSPeer=dict.putPeerIfAbsent(netPeer,otherSmsPeer);

            if(returnedSMSPeer.getAddress().compareTo(smsPeer.getAddress())!=0)
                Assert.fail("should return smsPeer already in");
        }
        catch (NullPointerException e){Assert.fail("shouldn't throw NullPointerException");}
    }

    @Test
    public void addPeersNullKey(){
        ReplicatedNetDictionary<String,String> dict=null;
        try{
            dict=new ReplicatedNetDictionary<>(netPeer,smsPeer);
        }
        catch (NullPointerException e){ Assert.fail("Error in ReplicatedNetDictionaryTest");}
        try{
            SMSPeer returnedSMSPeer=dict.putPeerIfAbsent(nullNetPeer,otherSmsPeer);
            Assert.fail("should throw NullPointerException");
        }
        catch (NullPointerException e){}//correct
    }

    @Test
    public void addPeersNullValue(){
        ReplicatedNetDictionary<String,String> dict=null;
        try{
            dict=new ReplicatedNetDictionary<>(netPeer,smsPeer);
        }
        catch (NullPointerException e){ Assert.fail("Error in ReplicatedNetDictionaryTest");}
        try{
            SMSPeer returnedSMSPeer=dict.putPeerIfAbsent(netPeer,nullSmsPeer);
            Assert.fail("should throw NullPointerException");
        }
        catch (NullPointerException e){}//correct
    }

//////////////////////////////////////////////////REMOVE
    @Test
    public void removePeersAbsent(){
        ReplicatedNetDictionary<String,String> dict=null;
        try{
            //i need 2 cause removing last peer return always null
            dict=new ReplicatedNetDictionary<>(netPeer,smsPeer);
            dict.putPeerIfAbsent(greaterNetPeer,smsPeer);
        }
        catch (NullPointerException e){ Assert.fail("Error in ReplicatedNetDictionaryTest");}
        try{
            SMSPeer returnedSMSPeer=dict.removePeer(lowerNetPeer);
            if(returnedSMSPeer!=null)
                Assert.fail("should return null");
        }
        catch (NullPointerException e){Assert.fail("shouldn't throw NullPointerException");}
    }

    @Test
    public void removePeersAlreadyIn(){
        ReplicatedNetDictionary<String,String> dict=null;
        try{
            //i need 2 cause removing last peer return always null
            dict=new ReplicatedNetDictionary<>(netPeer,smsPeer);
            dict.putPeerIfAbsent(greaterNetPeer,otherSmsPeer);
        }
        catch (NullPointerException e){ Assert.fail("Error in ReplicatedNetDictionaryTest");}
        try{
            SMSPeer returnedSMSPeer=dict.removePeer(greaterNetPeer);
            if(returnedSMSPeer.getAddress().compareTo(otherSmsPeer.getAddress())!=0)
                Assert.fail("should return SMSPeer removed");
        }
        catch (NullPointerException e){Assert.fail("shouldn't throw NullPointerException");}
    }

    @Test
    public void removePeersNullKey(){
        ReplicatedNetDictionary<String,String> dict=null;
        try{
            //i need 2 cause removing last peer return always null
            dict=new ReplicatedNetDictionary<>(netPeer,smsPeer);
            dict.putPeerIfAbsent(greaterNetPeer,smsPeer);
        }
        catch (NullPointerException e){ Assert.fail("Error in ReplicatedNetDictionaryTest");}
        try{
            SMSPeer returnedSMSPeer=dict.removePeer(nullNetPeer);
            Assert.fail("should throw NullPointerException");
        }
        catch (NullPointerException e){}//correct
    }

    @Test
    public void removePeersLastPeerCount(){
        ReplicatedNetDictionary<String,String> dict=null;
        try{
            dict=new ReplicatedNetDictionary<>(netPeer,smsPeer);
        }
        catch (NullPointerException e){ Assert.fail("Error in ReplicatedNetDictionaryTest");}
        try{
            SMSPeer returnedSMSPeer=dict.removePeer(netPeer);
            //when i try to remove last peer it always return null
            if(dict.numberOfPeers()!=1)
                Assert.fail("shouldn't remove last peer");
        }
        catch (NullPointerException e){Assert.fail("shouldn't throw NullPointerException");}
    }

    @Test
    public void removePeersLastPeerReturn(){
        ReplicatedNetDictionary<String,String> dict=null;
        try{
            dict=new ReplicatedNetDictionary<>(netPeer,smsPeer);
        }
        catch (NullPointerException e){ Assert.fail("Error in ReplicatedNetDictionaryTest");}
        try{
            SMSPeer returnedSMSPeer=dict.removePeer(netPeer);
            //when i try to remove last peer it always return null
            if(returnedSMSPeer!=null)
                Assert.fail("should return null");
        }
        catch (NullPointerException e){Assert.fail("shouldn't throw NullPointerException");}
    }

    //////////////////////////////////////////////////////////NUMBER_OF_PEERS
    @Test
    public void numberOfPeers(){
        ReplicatedNetDictionary<String,String> dict=null;
        try{
            dict=new ReplicatedNetDictionary<>(netPeer,smsPeer);
            dict.putPeerIfAbsent(greaterNetPeer,smsPeer);
            dict.putPeerIfAbsent(lowerNetPeer,otherSmsPeer);

            if(dict.numberOfPeers()!=3)
                Assert.fail("should have 3 peers");
        }
        catch (NullPointerException e){ Assert.fail("Error in ReplicatedNetDictionaryTest");}
    }

    //////////////////////////////////////////////////ITERATOR

    @Test
    public void getIterator(){
        ReplicatedNetDictionary<String,String> dict=null;
        try{
            dict=new ReplicatedNetDictionary<>(netPeer,smsPeer);
            dict.putPeerIfAbsent(greaterNetPeer,smsPeer);
            dict.putPeerIfAbsent(lowerNetPeer,otherSmsPeer);

            Iterator<Map.Entry<ReplicatedNetPeer,SMSPeer>> iterator=dict.getPeersIteratorAscending();

            if(iterator==null)
                Assert.fail("iterator shuldn't be null");
        }
        catch (NullPointerException e){ Assert.fail("Error in ReplicatedNetDictionaryTest");}
    }

    @Test
    public void getIteratorRightNumberOfItems(){
        ReplicatedNetDictionary<String,String> dict=null;
        try{
            dict=new ReplicatedNetDictionary<>(netPeer,smsPeer);
            dict.putPeerIfAbsent(greaterNetPeer,smsPeer);
            dict.putPeerIfAbsent(lowerNetPeer,otherSmsPeer);

            Iterator<Map.Entry<ReplicatedNetPeer,SMSPeer>> iterator=dict.getPeersIteratorAscending();

            int numberOfItems=0;

            while(iterator.hasNext())
            {
                iterator.next();
                numberOfItems++;
            }

            if(numberOfItems!=3)
                Assert.fail("should have 3 peers");
        }
        catch (NullPointerException e){ Assert.fail("Error in ReplicatedNetDictionaryTest");}
    }

    @Test
    public void getIteratorRightItemsOrderAscending(){
        ReplicatedNetDictionary<String,String> dict=null;
        try{
            dict=new ReplicatedNetDictionary<>(netPeer,smsPeer);
            dict.putPeerIfAbsent(greaterNetPeer,smsPeer);
            dict.putPeerIfAbsent(lowerNetPeer,otherSmsPeer);

            Iterator<Map.Entry<ReplicatedNetPeer,SMSPeer>> iterator=dict.getPeersIteratorAscending();

            //first item lower
            Map.Entry<ReplicatedNetPeer,SMSPeer> firstElement =iterator.next();
            if(!firstElement.getKey().equals(lowerNetPeer))
                Assert.fail("should be lower netPeer");

            //second item
            Map.Entry<ReplicatedNetPeer,SMSPeer> secondElement =iterator.next();
            if(!secondElement.getKey().equals(netPeer))
                Assert.fail("should be mid netPeer");

            //last item greater
            Map.Entry<ReplicatedNetPeer,SMSPeer> lastElement =iterator.next();
            if(!lastElement.getKey().equals(greaterNetPeer))
                Assert.fail("should be greater netPeer");
        }
        catch (NullPointerException e){ Assert.fail("Error in ReplicatedNetDictionaryTest");}
    }
}