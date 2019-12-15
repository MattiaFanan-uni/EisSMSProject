package com.gruppo3.smsconnection.replicatednet.message;

import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;

import org.junit.Assert;
import org.junit.Test;

public class ReplicatedNetPeerTest {
    public static final byte[] validAddress = {-1, -3, 5, 7, 8, 9, 0, 3, 2, 1, 23, 45, 67, -8, 32, 11};
    public static final byte[] greaterThanValidAddress = {-1, -3, 5, 7, 8, 9, 0, 3, 2, 1, 23, 45, 67, -8, 32, -11};
    public static final byte[] lowerThanValidAddress = {-1, -3, 5, 7, 8, 9, 0, 3, 2, 1, 23, 45, 67, -8, 32, 0};
    public static final byte[] tooLongAddress = {-1, -3, 5, 7, 8, 9, 0, 3, 2, 1, 23, 45, 67, -8, 32, -11, 77, -34, -56};
    public static final byte[] tooShortAddress = {-1, -3, 5, 7, 8, 9, 0, 3};
    public static final byte[] nullAddress = null;


    @Test
    public void setUp() {
        try {
            new ReplicatedNetPeer(validAddress);
        } catch (InvalidPeerException e) {
            Assert.fail("Should not throw InvalidPeerException exception");
        }
    }

    @Test(expected = InvalidPeerException.class)
    public void numberTooLong() {
        new ReplicatedNetPeer(tooLongAddress);
    }

    @Test(expected = InvalidPeerException.class)
    public void numberTooShort() {
        new ReplicatedNetPeer(tooShortAddress);
    }

    @Test(expected = NullPointerException.class)
    public void nullNumber() {
        new ReplicatedNetPeer(nullAddress);
    }

    @Test
    public void getAddressTest() {
        ReplicatedNetPeer peer = new ReplicatedNetPeer(validAddress);
        Assert.assertArrayEquals(peer.getAddress(), validAddress);
    }

    @Test
    public void CompareToGreater() {
        ReplicatedNetPeer peer = new ReplicatedNetPeer(validAddress);
        ReplicatedNetPeer greater = new ReplicatedNetPeer(greaterThanValidAddress);

        Assert.assertTrue(peer.compareTo(greater) <= 0);
    }

    @Test
    public void CompareToLower() {
        ReplicatedNetPeer peer = new ReplicatedNetPeer(validAddress);
        ReplicatedNetPeer lower = new ReplicatedNetPeer(lowerThanValidAddress);

        Assert.assertTrue(peer.compareTo(lower) >= 0);
    }

    @Test
    public void CompareToEqual() {
        ReplicatedNetPeer peer = new ReplicatedNetPeer(validAddress);
        ReplicatedNetPeer equal = new ReplicatedNetPeer(validAddress);

        Assert.assertTrue(peer.compareTo(equal) == 0);

    }

    @Test
    public void Equals() {
        ReplicatedNetPeer peer = new ReplicatedNetPeer(validAddress);
        ReplicatedNetPeer equal = new ReplicatedNetPeer(validAddress);

        Assert.assertEquals(peer, equal);

    }

    @Test
    public void EqualsNotEquals() {
        ReplicatedNetPeer peer = new ReplicatedNetPeer(validAddress);
        ReplicatedNetPeer lower = new ReplicatedNetPeer(lowerThanValidAddress);

        Assert.assertNotEquals(peer, lower);

    }
}
