package com.gruppo3.smsconnection.replicatednet.message;

import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;
import com.gruppo3.smsconnection.replicatednet.message.ReplicatedNetPeer;

import org.junit.Assert;
import org.junit.Test;
import java.util.Arrays;

public class ReplicatedNetPeerTest {
    public static final byte[] validAddress={-1,-3,5,7,8,9,0,3,2,1,23,45,67,-8,32,11};
    public static final byte[] greaterThanValidAddress={-1,-3,5,7,8,9,0,3,2,1,23,45,67,-8,32,-11};
    public static final byte[] lowerThanValidAddress={-1,-3,5,7,8,9,0,3,2,1,23,45,67,-8,32,0};
    public static final byte[] tooLongAddress={-1,-3,5,7,8,9,0,3,2,1,23,45,67,-8,32,-11,77,-34,-56};
    public static final byte[] tooShortAddress={-1,-3,5,7,8,9,0,3};
    public static final byte[] nullAddress=null;



    @Test
    public void setUp(){
        ReplicatedNetPeer peer;
        try {
            peer=new ReplicatedNetPeer(validAddress);
        }
        catch (InvalidPeerException e){Assert.fail("Should not throw InvalidPeerException exception");}
        catch (Exception e){Assert.fail("Should not throw this exception");}
    }

    @Test
    public void numberTooLong() {
        ReplicatedNetPeer peer;
        try {
            peer=new ReplicatedNetPeer(tooLongAddress);
            Assert.fail("should throw InvalidPeerException");
        }
        catch (InvalidPeerException e){}//correct
        catch (Exception e){Assert.fail("Should not throw this exception");}
    }

    @Test
    public void numberTooShort() {
        ReplicatedNetPeer peer;
        try {
            peer=new ReplicatedNetPeer(tooShortAddress);
            Assert.fail("should throw InvalidPeerException");
        }
        catch (InvalidPeerException e){}//correct
        catch (Exception e){Assert.fail("Should not throw this exception");}
    }

    @Test
    public void nullNumber() {
            ReplicatedNetPeer peer;
        try {
            peer=new ReplicatedNetPeer(nullAddress);
            Assert.fail("should throw NullPointerException");
        }
        catch (NullPointerException e){}//correct
        catch (Exception e){Assert.fail("Should not throw this exception");}
    }

    @Test
    public void getAddressTest() {
        ReplicatedNetPeer peer;
        try {
            peer= new ReplicatedNetPeer(validAddress);
            if(!Arrays.equals(peer.getAddress(),validAddress))
                Assert.fail("should be the same number");
        }
        catch (InvalidPeerException e) {Assert.fail("Shouldn't throw InvalidPeerException");}
        catch (Exception e) {Assert.fail("Shouldn't throw this Exception");}
    }

    @Test
    public void CompareToGreater(){
        ReplicatedNetPeer peer=null;
        ReplicatedNetPeer greater=null;
        try {
            peer=new ReplicatedNetPeer(validAddress);
            greater=new ReplicatedNetPeer(greaterThanValidAddress);
        }
        catch (InvalidPeerException e){Assert.fail("Should not throw InvalidPeerException exception");}
        catch (Exception e){Assert.fail("Should not throw this exception");}

        if(peer.compareTo(greater)>=0)
            //if peer is greater or equal
            Assert.fail("should be lower");

    }

    @Test
    public void CompareToLower(){
        ReplicatedNetPeer peer=null;
        ReplicatedNetPeer lower=null;
        try {
            peer=new ReplicatedNetPeer(validAddress);
            lower=new ReplicatedNetPeer(lowerThanValidAddress);
        }
        catch (InvalidPeerException e){Assert.fail("Should not throw InvalidPeerException exception");}
        catch (Exception e){Assert.fail("Should not throw this exception");}

        if(peer.compareTo(lower)<=0)
            //if peer is lower or equal
            Assert.fail("should be greater");

    }

    @Test
    public void CompareToEqual(){
        ReplicatedNetPeer peer=null;
        ReplicatedNetPeer equal=null;
        try {
            peer=new ReplicatedNetPeer(validAddress);
            equal=new ReplicatedNetPeer(validAddress);
        }
        catch (InvalidPeerException e){Assert.fail("Should not throw InvalidPeerException exception");}
        catch (Exception e){Assert.fail("Should not throw this exception");}

        if(peer.compareTo(equal)!=0)
            //if peer is lower or greater
            Assert.fail("should be equal");

    }

    @Test
    public void Equals(){
        ReplicatedNetPeer peer=null;
        ReplicatedNetPeer equal=null;
        try {
            peer=new ReplicatedNetPeer(validAddress);
            equal=new ReplicatedNetPeer(validAddress);
        }
        catch (InvalidPeerException e){Assert.fail("Should not throw InvalidPeerException exception");}
        catch (Exception e){Assert.fail("Should not throw this exception");}

        if(!peer.equals(equal))
            Assert.fail("should be equal");

    }

    @Test
    public void EqualsNotEquals(){
        ReplicatedNetPeer peer=null;
        ReplicatedNetPeer lower=null;
        try {
            peer=new ReplicatedNetPeer(validAddress);
            lower=new ReplicatedNetPeer(lowerThanValidAddress);
        }
        catch (InvalidPeerException e){Assert.fail("Should not throw InvalidPeerException exception");}
        catch (Exception e){Assert.fail("Should not throw this exception");}

        if(peer.equals(lower))
            Assert.fail("shouldn't be equal");

    }
}
