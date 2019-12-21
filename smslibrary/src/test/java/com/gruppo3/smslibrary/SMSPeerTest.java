package com.gruppo3.smslibrary;

import com.gruppo3.smslibrary.exceptions.InvalidAddressException;
import com.gruppo3.smslibrary.types.Peer;

import org.junit.Assert;
import org.junit.Test;

public class SMSPeerTest {
    private static final String validAddress = "12345678";
    private static final String validCountryCodeAddress = "+3912345678";
    private static final String tooLongAddress = "123412345678912345678905678";
    private static final String tooShortAddress = "12";
    private static final String charAddress = "++12345678";

    public static String getValidAddress() {
        return validAddress;
    }

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
        Assert.assertEquals(peer.getPhoneNumber(), validAddress);
    }

}
