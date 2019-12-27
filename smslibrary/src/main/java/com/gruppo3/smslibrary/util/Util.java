package com.gruppo3.smslibrary.util;

import android.util.Log;

import androidx.annotation.NonNull;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * Class containing all methods needed to accomplish general operations.
 *
 * @author Giovanni Barca
 * @version 1
 */
public class Util {
    final private static char[] hexArray = "0123456789ABCDEF".toCharArray();

    /**
     * Converts the given String to a 160bit length hash using SHA-1 algorithm.
     * @param toEncrypt String to get the hash from
     * @return The hash of the passed parameter or an empty string if an error has occured (prints the Stack Trace to the Logcat)
     */
    @NonNull
    public static String sha1Hash(@NonNull String toEncrypt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] bytes = toEncrypt.getBytes(StandardCharsets.UTF_8);
            digest.update(bytes, 0, bytes.length);
            bytes = digest.digest();

            return parseBytesToBinaryString(bytes);
        }
        catch (NoSuchAlgorithmException e) {
            // Converting NoSuchAlgorithmException to RuntimeException
            // NoSuchAlgorithmException shouldn't never occur
            Log.d("Hashing error", Log.getStackTraceString(e));
            return "";
        }
    }

    /**
     * Parsed the given array of bytes to a String containing the corresponding binary value.
     * @param bytes Bytes array to be parsed
     * @return A String containing the binary value of the bytes array argument
     */
    private static String parseBytesToBinaryString(byte[] bytes) {
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

    /**
     * Generates a random node ID of the given length.
     * @param length Number of bits the random generated node ID will have
     * @return A String containing a random generated node ID
     * @throws IllegalArgumentException If length is equal or less than 0
     */
    public static String generateRandomNodeID(int length) throws IllegalArgumentException {
        if (length <= 0)
            throw new IllegalArgumentException("Length must be greater than 0.");

        String randomNodeId = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            if (random.nextBoolean())
                randomNodeId += '1';
            else
                randomNodeId += '0';
        }

        return randomNodeId;
    }
}