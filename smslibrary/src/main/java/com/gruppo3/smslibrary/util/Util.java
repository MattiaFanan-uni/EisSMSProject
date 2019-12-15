package com.gruppo3.smslibrary.util;

import com.gruppo3.smslibrary.exceptions.InvalidBitSetLengthException;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.BitSet;

/**
 * Class containing all methods needed to accomplish general operations.
 *
 * @author Giovanni Barca
 */
public class Util {
    final private static char[] hexArray = "0123456789ABCDEF".toCharArray();

    /**
     * Converts the given String to a 160bit length hash using SHA-1 algorithm.
     * @param toEncrypt String to get the hash from
     * @return The hash of the passed parameter
     * @throws NoSuchAlgorithmException If no Provider supports a MessageDigestSpi implementation for the specified algorithm
     * @throws UnsupportedEncodingException If the named charset is not supported
     */
    public static String sha1Hash(String toEncrypt) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        byte[] bytes = toEncrypt.getBytes(StandardCharsets.UTF_8);
        digest.update(bytes, 0, bytes.length);
        bytes = digest.digest();

        return convertBytesToHex(bytes);
    }

    /**
     * Converts the given byte array to its hexadecimal value (values between A and F are upper case).
     * @param bytes Byte array to be converted
     * @return A String containing the hexadecimal value of the passed parameter
     */
    public static String convertBytesToHex(byte[] bytes)
    {
        char[] hexChars = new char[bytes.length * 2];
        for(int j = 0; j < bytes.length; j++)
        {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }

        return new String(hexChars);
    }

    /**
     * Converts a String containing an hexadecimal value (without the leading '0x') to a BitSet.
     * @param hexString String hexadecimal value to be converted
     * @return A BitSet representing the hexadecimal value in base-2
     */
    public static BitSet convertHexToBitSet(String hexString) {
        return BitSet.valueOf(new long[]{Long.valueOf(hexString, 16)});
    }
}