package com.gruppo3.kademlia.types;

import com.gruppo3.smslibrary.util.Util;

/**
 * Class extending the {@link java.util.BitSet BitSet} class.
 *
 * @author Giovanni Barca
 * @version 0.1
 */
public class BitSet extends java.util.BitSet {
    public BitSet() {
        super();
    }

    public BitSet(int nbits) {
        super(nbits);
    }

    /**
     * This method reinitialize this BitSet and fills it with the bitSetString argument chars.
     * @param bitSetString The string to be parsed to this BitSet
     * @throws IllegalArgumentException If the bitSetString argument contains other chars besides 0 and 1
     */
    public void set(String bitSetString) throws IllegalArgumentException {
        if (!bitSetString.matches("[01]+"))
            throw new IllegalArgumentException("Argument is not a valid binary String.");

        for (int i = 0; i < bitSetString.length(); i++) {
            if (bitSetString.charAt(i) == '0')
                this.set(i, false);
            else if (bitSetString.charAt(i) == '1')
                this.set(i, true);
        }
    }

    /**
     * This method reinitialize this BitSet and fills it with the binary representation of hexValue argument String<br>
     * The hexadecimal value doesn't need the leading '<code>0x</code>' and it works with both lower and upper case letters.
     * @param hexValue String representing the hexadecimal value to be parsed
     * @throws IllegalArgumentException if the hexValue argument is not in the range of hexadecimal values (0-F)
     */
    public void setFromHexValue(String hexValue) throws IllegalArgumentException {
        if (!hexValue.matches("[0-9a-fA-F]+"))
            throw new IllegalArgumentException("Argument is not a valid hexadecimal String.");

        byte[] temp = new byte[hexValue.length() / 2];
        for (int i = 0; i < temp.length; i++) {
            int index = i * 2;
            int j = Integer.parseInt(hexValue.substring(index, index + 2), 16);
            temp[i] = (byte) j;
        }

        set(Util.parseBytesToBinaryString(temp));
    }

    /**
     * This method reinitialize this BitSet and fills it with the binary representation of boolean array argument, where false equals to 0 and true equals to 1.
     * @param booleanArray Boolean array to be converted
     */
    public void setFromBooleanArray(boolean[] booleanArray) {
        for (int i = 0; i < booleanArray.length; i++)
            set(i, booleanArray[i]);
    }
}
