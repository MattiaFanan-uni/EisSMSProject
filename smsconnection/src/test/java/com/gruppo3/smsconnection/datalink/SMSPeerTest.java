package com.gruppo3.smsconnection.datalink;

import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;
import com.gruppo3.smsconnection.smsdatalink.SMSPeer;

import org.junit.Assert;
import org.junit.Test;

public class SMSPeerTest {
    public static final String validAddress="12345678";
    public static final String validCountryCodeAddress="+3912345678";
    public static final String tooLongAddress="123412345678912345678905678";
    public static final String tooShortAddress="12";
    public static final String nullAddress=null;
    public static final String charAddress="++12345678";

    @Test
    public void setUp(){
        SMSPeer peer;
        try {
            peer=new SMSPeer(validAddress);
        }
        catch (InvalidPeerException e){Assert.fail("Should not throw InvalidPeerException exception");}
        catch (Exception e){Assert.fail("Should not throw this exception");}
    }

    @Test
    public void setUPCountryCode() {
        SMSPeer peer;
        try {
            peer=new SMSPeer(validCountryCodeAddress);
        }
        catch (InvalidPeerException e){Assert.fail("shouldn't throw InvalidPeerException");}
        catch (Exception e){Assert.fail("Should not throw this exception");}
    }

    @Test
    public void setUPWithChars() {
        SMSPeer peer;
        try {
            peer=new SMSPeer(charAddress);
            Assert.fail("should throw InvalidPeerException");
        }
        catch (InvalidPeerException e){}//correct
        catch (Exception e){Assert.fail("Should not throw this exception");}
    }

    @Test
    public void numberTooLong() {
        SMSPeer peer;
        try {
            peer=new SMSPeer(tooLongAddress);
            Assert.fail("should throw InvalidPeerException");
        }
        catch (InvalidPeerException e){}//correct
        catch (Exception e){Assert.fail("Should not throw this exception");}
    }

    @Test
    public void numberTooShort() {
        SMSPeer peer;
        try {
            peer=new SMSPeer(tooShortAddress);
            Assert.fail("should throw InvalidPeerException");
        }
        catch (InvalidPeerException e){}//correct
        catch (Exception e){Assert.fail("Should not throw this exception");}
    }



    @Test
    public void nullNumber() {
        SMSPeer peer;
        try {
            peer=new SMSPeer(nullAddress);
            Assert.fail("should throw NullPointerException");
        }
        catch (NullPointerException e){}//correct
        catch (Exception e){Assert.fail("Should not throw this exception");}
    }

    @Test
    public void getAddressTest() {
        SMSPeer peer;
        try {
            peer= new SMSPeer(validAddress);
            if(peer.getAddress().compareTo(validAddress)!=0)
                Assert.fail("should be the same number");
        }
        catch (InvalidPeerException e) {Assert.fail("Shouldn't throw InvalidPeerException");}
        catch (Exception e) {Assert.fail("Shouldn't throw this Exception");}
    }

}
