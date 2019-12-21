package com.gruppo3.smslibrary.util;

import android.util.Log;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
     */
    public static String sha1Hash(String toEncrypt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] bytes = toEncrypt.getBytes(StandardCharsets.UTF_8);
            digest.update(bytes, 0, bytes.length);
            bytes = digest.digest();

            return convertBytesToBinaryString(bytes);
        }
        catch (NoSuchAlgorithmException e) {
            Log.e("smslibrary exception", Log.getStackTraceString(e));
            return null;
        }
    }

    private static String convertBytesToBinaryString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte bytesElement : bytes) {
            String byteToBitString = String.format("%8s", Integer.toBinaryString(bytesElement & 0xFF)).replace(' ', '0');
            sb.append(byteToBitString);
        }

        return sb.toString();
    }

    /**
     * Converts the given byte array to its hexadecimal value (values between A and F are upper case).
     * @param bytes Byte array to be converted
     * @return A String containing the hexadecimal value of the passed parameter
     */
    private static String convertBytesToHex(byte[] bytes)
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
}