package com.gruppo3.smslibrary;

import com.gruppo3.smslibrary.exceptions.InvalidAddressException;
import com.gruppo3.smslibrary.types.Peer;

import org.junit.Assert;
import org.junit.Test;

public class SMSPeerTest {
    public static final String validAddress = "12345678";
    public static final String validCountryCodeAddress = "+3912345678";
    public static final String tooLongAddress = "123412345678912345678905678";
    public static final String tooShortAddress = "12";
    public static final String charAddress = "++12345678";

    @Test
    public void setUp() {
        try {
            new Peer(validAddress);
        } catch (InvalidAddressException e) {
            Assert.fail("Should not throw InvalidAddressException exception");
        }
    }

    @Test
    public void setUPCountryCode() {
        try {
            new Peer(validCountryCodeAddress);
        } catch (InvalidAddressException e) {
            Assert.fail("Should not throw InvalidAddressException exception");
        }
    }

    @Test(expected = InvalidAddressException.class)
    public void setUPWithChars() {
        new Peer(charAddress);
    }

    @Test(expected = InvalidAddressException.class)
    public void numberTooLong() {
        new Peer(tooLongAddress);
    }

    @Test(expected = InvalidAddressException.class)
    public void numberTooShort() {
        new Peer(tooShortAddress);
    }


    @Test(expected = NullPointerException.class)
    public void nullNumber() throws InvalidAddressException {
        new Peer(null);
    }

    @Test
    public void getAddressTest() throws Exception {
        Peer peer = new Peer(validAddress);
        Assert.assertEquals(peer.getAddress(), validAddress);
    }

}
