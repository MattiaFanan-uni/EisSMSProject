package com.gruppo3.smsconnection.utils;

import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;
import com.gruppo3.smsconnection.replicatednet.dictionary.command.StringParser;
import com.gruppo3.smsconnection.replicatednet.message.ReplicatedNetPeer;

/**
 * Utility object used to map a {@link ReplicatedNetPeer} to a String and reverse
 *
 * @author Mattia Fanan
 */
public class ReplicatedNetPeerParser implements StringParser<ReplicatedNetPeer> {
    /**
     * Parses a String from a {@link ReplicatedNetPeer}
     *
     * @param data the {@link ReplicatedNetPeer} to parse from
     * @return the parsed String
     */
    @Override
    public String parseString(ReplicatedNetPeer data) {
        return bytesToHex(data.getAddress());
    }

    /**
     * Parses a {@link ReplicatedNetPeer} from the passed String
     *
     * @param string the string to parse from
     * @return the parsed {@link ReplicatedNetPeer}
     */
    @Override
    public ReplicatedNetPeer parseData(String string) {
        try {
            return new ReplicatedNetPeer(hexToBytes(string));
        } catch (InvalidPeerException e) {
            return null;
        }
    }

    /**
     * Maps a <code>byte[]</code> into its String representation<br>
     * <i>maps byte to the char representation of it's hex</i>
     *
     * @param bytes the sequence to map
     * @return the mapped String
     */
    protected static String bytesToHex(byte[] bytes) {

        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();

    }

    /**
     * Maps a String into its <code>byte[]</code> representation<br>
     * <i>maps two chars into a hex number and then into a byte</i>
     *
     * @param hex the String to map
     * @return the mapped sequence
     */
    protected static byte[] hexToBytes(String hex) {
        byte[] val = new byte[hex.length() / 2];

        for (int i = 0; i < val.length; i++) {
            int index = i * 2;
            int j = Integer.parseInt(hex.substring(index, index + 2), 16);
            val[i] = (byte) j;
        }
        return val;
    }
}
