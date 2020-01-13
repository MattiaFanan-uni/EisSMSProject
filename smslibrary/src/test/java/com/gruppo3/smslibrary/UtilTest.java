package com.gruppo3.smslibrary;

import com.gruppo3.smslibrary.util.Util;

import org.junit.Assert;
import org.junit.Test;

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
        Assert.assertEquals(validEncryptedSha1String, Util.sha1Hash(validDecryptedSha1String));
    }
}
