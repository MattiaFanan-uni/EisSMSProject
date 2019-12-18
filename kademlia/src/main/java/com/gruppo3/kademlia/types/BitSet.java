package com.gruppo3.kademlia.types;

/**
 * Class extending the <code>java.util.BitSet</code>.
 *
 * @author Giovanni Barca
 */
public class BitSet extends java.util.BitSet {
    public BitSet() {
        super();
    }

    public BitSet(int nbits) {
        super();
    }

    /**
     * This method reinitialize this BitSet and fills it with the bitSetString argument chars.
     * @param bitSetString The string to be parsed to this BitSet
     * @throws IllegalArgumentException If the bitSetString argument contains other chars besides 0 and 1
     */
    public static java.util.BitSet set(String bitSetString) throws IllegalArgumentException {
        BitSet bitSet = new BitSet();

        for (int i = 0; i < bitSetString.length(); i++) {
            if (bitSetString.charAt(i) == '0')
                bitSet.set(i, false);
            else if (bitSetString.charAt(i) == '1')
                bitSet.set(i, true);
            else
                throw new IllegalArgumentException();
        }

        return bitSet;
    }

    /**
     * Converts an hexadecimal value passed via a String to its corresponding BitSet values.<br>
     * The hexadecimal value doesn't need the leading <code>0x</code> and it works with both lower and upper case letters.
     * @param hexValue String representing the hexadecimal value to be converted
     * @return A BitSet of the converted hexadecimal String argument
     * @throws IllegalArgumentException if the hexValue argument is not in the range of hexadecimal values (0-F)
     */
    public static java.util.BitSet hexToBitSet(String hexValue) throws IllegalArgumentException {
        // Upper casing hex string to avoid parsing errors
        hexValue = hexValue.toUpperCase();

        // Values are false by default. Setting instead only true values (where the bit is equal to 1)
        boolean[] tempBooleanArray = new boolean[hexValue.length() * 4];

        for (int i = 0; i < hexValue.length(); i++) {
            switch (hexValue.charAt(i)) {
                case '1':
                    tempBooleanArray[(i * 4) + 3] = true;
                    break;

                case '2':
                    tempBooleanArray[(i * 4) + 2] = true;
                    break;

                case '3':
                    tempBooleanArray[(i * 4) + 2] = true;
                    tempBooleanArray[(i * 4) + 3] = true;
                    break;

                case '4':
                    tempBooleanArray[(i * 4) + 1] = true;
                    break;

                case '5':
                    tempBooleanArray[(i * 4) + 1] = true;
                    tempBooleanArray[(i * 4) + 3] = true;
                    break;

                case '6':
                    tempBooleanArray[(i * 4) + 1] = true;
                    tempBooleanArray[(i * 4) + 2] = true;
                    break;

                case '7':
                    tempBooleanArray[(i * 4) + 1] = true;
                    tempBooleanArray[(i * 4) + 2] = true;
                    tempBooleanArray[(i * 4) + 3] = true;
                    break;

                case '8':
                    tempBooleanArray[(i * 4)] = true;
                    break;

                case '9':
                    tempBooleanArray[(i * 4)] = true;
                    tempBooleanArray[(i * 4) + 3] = true;
                    break;

                case 'A':
                    tempBooleanArray[(i * 4)] = true;
                    tempBooleanArray[(i * 4) + 2] = true;
                    break;

                case 'B':
                    tempBooleanArray[(i * 4)] = true;
                    tempBooleanArray[(i * 4) + 2] = true;
                    tempBooleanArray[(i * 4) + 3] = true;
                    break;

                case 'C':
                    tempBooleanArray[(i * 4)] = true;
                    tempBooleanArray[(i * 4) + 1] = true;
                    break;

                case 'D':
                    tempBooleanArray[(i * 4)] = true;
                    tempBooleanArray[(i * 4) + 1] = true;
                    tempBooleanArray[(i * 4) + 3] = true;
                    break;

                case 'E':
                    tempBooleanArray[(i * 4)] = true;
                    tempBooleanArray[(i * 4) + 1] = true;
                    tempBooleanArray[(i * 4) + 2] = true;
                    break;

                case 'F':
                    tempBooleanArray[(i * 4)] = true;
                    tempBooleanArray[(i * 4) + 1] = true;
                    tempBooleanArray[(i * 4) + 2] = true;
                    tempBooleanArray[(i * 4) + 3] = true;
                    break;

                default:
                    throw new IllegalArgumentException();
            }
        }

        return booleanArrayToBitSet(tempBooleanArray);
    }

    /**
     * Converts an array of boolean values to a BitSet.
     * @param booleanArray Boolean array to be converted
     * @return The converted boolean array
     */
    protected static java.util.BitSet booleanArrayToBitSet(boolean[] booleanArray) {
        java.util.BitSet tempBitSet = new java.util.BitSet(booleanArray.length);

        for (int i = 0; i < booleanArray.length; i++)
            tempBitSet.set(i, booleanArray[i]);

        return tempBitSet;
    }
}
