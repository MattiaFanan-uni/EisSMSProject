package com.gruppo3.kademlia;

import com.gruppo3.kademlia.types.BitSet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * BitSet class junit testing
 *
 * @author Giovanni Barca
 */
public class BitSetTest {
    private static final BitSet validBitSet = new BitSet();
    private static final String validBitString = "1010";
    private static final String validHexString = "A";
    private static final String invalidBitString = "1234";
    private static final String invalidHexString = "H";

    /**
     * BitSet initialization
     */
    @Before
    public void init() {
        // Creating 1010 BitSet
        validBitSet.set(0, true);
        validBitSet.set(2, true);
    }

    /**
     * BitSet.set(String) method testing
     * String to BitSet conversion
     */
    @Test
    public void testBitStringToBitSet() {
        Assert.assertEquals(validBitSet, BitSet.set(validBitString));
    }

    /**
     * BitSet.set(String) method IllegalArgumentException testing
     * String to BitSet conversion
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidBitStringToBitSet() {
        Assert.assertEquals(validBitSet, BitSet.set(invalidBitString));
    }

    /**
     * BitSet.hexToBitSet(String) testing
     * Hex String to BitSet conversion
     */
    @Test
    public void testHexToBitSet() {
        Assert.assertEquals(validBitSet, BitSet.hexStringToBitSet(validHexString));
    }

    /**
     * BitSet.hexToBitSet(String) method IllegalArgumentException testing
     * Hex String to BitSet conversion
     */
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidHexToBitSet() {
        Assert.assertEquals(validBitSet, BitSet.set(invalidHexString));
    }
}
