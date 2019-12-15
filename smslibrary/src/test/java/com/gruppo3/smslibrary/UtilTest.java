package com.gruppo3.smslibrary;

import com.gruppo3.smslibrary.util.Util;

import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.BitSet;

public class UtilTest {

    // ## sha1Hash tests ##
    private static final String validDecryptedSha1String = "+3912345678";
    private static final String validEncryptedSha1String = "2F1B2F8782FB700181712442936F946117EC88DB";

    @Test
    public void setUpSha1Hash() {
        try {
            Util.sha1Hash(validDecryptedSha1String);
        }
        catch (NoSuchAlgorithmException e) {
            Assert.fail("Should not throw NoSuchAlgorithmException exception");
        }
        catch (UnsupportedEncodingException e) {
            Assert.fail("Should not throw UnsupportedEncodingException exception");
        }
    }

    @Test
    public void validSha1Hash() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        Assert.assertEquals(validEncryptedSha1String, Util.sha1Hash(validDecryptedSha1String));
    }

    // ## convertBytesToHex tests ##
    private static final byte[] validByteArray = new byte[] {1, 2, 10, 11};
    private static final String validHex = "01020A0B";

    @Test
    public void validBytesToHex() {
        Assert.assertEquals(validHex, Util.convertBytesToHex(validByteArray));
    }

    // ## convertHexToBitSet tests ##
    private final BitSet validBitSet = new BitSet(32);
    // CORRECT
    // 0000 0001 0000 0010 0000 1010 0000 1011
    // 0    1    0    2    0    A    0    B

    // WRONG (actual convertHexToBitSet())
    // Should invert
    // 1101 0000 0101 0000 0100 0000 1000 0000
    //    B    0    A    0    2    0    1    0

    @Test
    public void validHexToByte() {
        validBitSet.set(7);
        validBitSet.set(14);
        validBitSet.set(20);
        validBitSet.set(21);
        validBitSet.set(28);
        validBitSet.set(29);
        validBitSet.set(31);

        BitSet abc = Util.convertHexToBitSet(validHex);
        Assert.assertEquals(validBitSet, abc);
    }
}
