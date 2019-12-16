package com.gruppo3.smslibrary;

import org.junit.Assert;
import org.junit.Test;

import com.gruppo3.smslibrary.util.Util;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Util class testing
 *
 * @author Giovanni Barca
 */
public class UtilTest {

    // ## sha1Hash tests ##
    private static final String validDecryptedSha1String = "+3912345678";
    private static final String validEncryptedSha1String = "2F1B2F8782FB700181712442936F946117EC88DB";

    /**
     * Util.sha1Hash(String) method testing
     * String to Sha1 hash generator
     */
    @Test
    public void stringToSha1Hash() {
        try {
            Assert.assertEquals(validEncryptedSha1String, Util.sha1Hash(validDecryptedSha1String));
        }
        catch (NoSuchAlgorithmException e) {
            Assert.fail("Should not throw NoSuchAlgorithmException exception");
        }
    }

    // ## convertBytesToHex tests ##
    private static final byte[] validByteArray = new byte[] {1, 2, 10, 11};
    private static final String validHex = "01020A0B";

    /**
     * Util.convertBytesToHex(byte[]) method testing
     * Bytes to Hex conversion
     */
    /*@Test
    public void validBytesToHex() {
        Assert.assertEquals(validHex, Util.convertBytesToHex(validByteArray));
    }*/
}
