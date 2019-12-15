package com.gruppo3.smsconnection.utils;

import com.gruppo3.smsconnection.replicatednet.dictionary.command.StringParser;

/**
 * Utility object used to map an Integer to a String and reverse<br>
 * <i>convert to char the hex representation of the 32-bit long number</i>
 *
 * @author Mattia Fanan
 */
public class IntegerParser implements StringParser<Integer> {

    /**
     * Parses a String from an Integer
     *
     * @param data the Integer to parse from
     * @return the parsed String
     */
    @Override
    public String parseString(Integer data) {
        return ReplicatedNetPeerParser.bytesToHex(intToByte(data));
    }

    /**
     * Parses an Integer from the passed String
     *
     * @param string the string to parse from
     * @return the parsed Integer
     */
    @Override
    public Integer parseData(String string) {
        return byteToInt(ReplicatedNetPeerParser.hexToBytes(string));
    }


    /**
     * Maps an Integer in its 4 byte representation
     *
     * @param toMap the Integer to map
     * @return the mapped <code>byte[]</code>
     */
    protected static byte[] intToByte(int toMap) {
        return new byte[]{
                (byte) (toMap >> 24),
                (byte) (toMap >> 16),
                (byte) (toMap >> 8),
                (byte) toMap
        };
    }

    /**
     * Maps the first 4 byte of <code>byte[]</code> in its Integer 32-bit representation
     *
     * @param bytes the <code>byte[]</code> to map
     * @return the mapped Integer
     */
    protected static int byteToInt(byte[] bytes) {

        return ((bytes[0] & 0xFF) << 24) |
                ((bytes[1] & 0xFF) << 16) |
                ((bytes[2] & 0xFF) << 8) |
                ((bytes[3] & 0xFF));

    }


}
