package com.gruppo3.smsconnection;

import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;
import com.gruppo3.smsconnection.smsdatalink.SMSPeer;

import org.junit.Assert;
import org.junit.Test;

public class SMSPeerTest {
    @Test
    public void setUp(){
        SMSPeer peer;
        try {
            peer=new SMSPeer("12345678");
        }
        catch (InvalidPeerException e){Assert.fail("Should not throw InvalidPeerException exception");}
        catch (Exception e){Assert.fail("Should not throw this exception");}
    }

    @Test
    public void setUPInternationalAddressPrefix() {
        SMSPeer peer;
        try {
            peer=new SMSPeer("+39123456");
        }
        catch (InvalidPeerException e){Assert.fail("shouldn't throw InvalidPeerException");}
        catch (Exception e){Assert.fail("Should not throw this exception");}
    }

    @Test
    public void setUPWithChars() {
        SMSPeer peer;
        try {
            peer=new SMSPeer("++39123456");
            Assert.fail("should throw InvalidPeerException");
        }
        catch (InvalidPeerException e){}//correct
        catch (Exception e){Assert.fail("Should not throw this exception");}
    }

    @Test
    public void numberTooLong() {
        SMSPeer peer;
        try {
            peer=new SMSPeer("123456789016876543245678");
            Assert.fail("should throw InvalidPeerException");
        }
        catch (InvalidPeerException e){}//correct
        catch (Exception e){Assert.fail("Should not throw this exception");}
    }

    @Test
    public void numberTooShort() {
        SMSPeer peer;
        try {
            peer=new SMSPeer("123");
            Assert.fail("should throw InvalidPeerException");
        }
        catch (InvalidPeerException e){}//correct
        catch (Exception e){Assert.fail("Should not throw this exception");}
    }



    @Test
    public void nullNumber() {
        SMSPeer peer;
        try {
            peer=new SMSPeer(null);
            Assert.fail("should throw InvalidPeerException");
        }
        catch (InvalidPeerException e){}//correct
        catch (Exception e){Assert.fail("Should not throw this exception");}
    }

    @Test
    public void getAddressTest() {
        SMSPeer peer;
        String inputAddress="12345678";
        try {
            peer= new SMSPeer(inputAddress);
            if(peer.getAddress().compareTo(inputAddress)!=0)
                Assert.fail("should be the same number");
        }
        catch (InvalidPeerException e) {Assert.fail("Shouldn't throw InvalidPeerException");}
        catch (Exception e) {Assert.fail("Shouldn't throw this Exception");}
    }

    @Test
    public void setAddressTest() {
        SMSPeer peer;
        String startAddress="12345678";
        String changedAddress="345678";
        try {
            peer =  new SMSPeer(startAddress);
            boolean changeResult=peer.setAddress(changedAddress);
            if(!changeResult)
                Assert.fail("result should be true");
            if(peer.getAddress().compareTo(changedAddress)!=0)
                Assert.fail("data should be changed");
        }
        catch (InvalidPeerException e) {Assert.fail("Shouldn't throw InvalidPeerException");}
        catch (Exception e) {Assert.fail("Shouldn't throw this Exception");}
    }

    @Test
    public void setAddressNullTest() {
        SMSPeer peer;
        String startAddress="12345678";
        String changedAddress=null;
        try {
            peer =  new SMSPeer(startAddress);
            boolean changeResult=peer.setAddress(changedAddress);
            if(changeResult)
                Assert.fail("result should be false");
            if(peer.getAddress().compareTo(startAddress)!=0)
                Assert.fail("data shouldn't be changed");
        }
        catch (InvalidPeerException e) {Assert.fail("Shouldn't throw InvalidPeerException");}
        catch (Exception e) {Assert.fail("Shouldn't throw this Exception");}
    }
}
