package com.gruppo3.smsconnection.utils;

import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;
import com.gruppo3.smsconnection.replicatednet.dictionary.command.StringParser;
import com.gruppo3.smsconnection.replicatednet.message.ReplicatedNetPeer;

public class ReplicatedNetPeerParser implements StringParser<ReplicatedNetPeer> {

    @Override
    public String parseString(ReplicatedNetPeer data) {
        return bytesToHex(data.getAddress());
    }

    @Override
    public ReplicatedNetPeer parseData(String string) {
        try {
            return new ReplicatedNetPeer(hexToBytes(string));
        } catch (InvalidPeerException e) {
            return null;
        }
    }

    private static String bytesToHex(byte[] hashInBytes) {

        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();

    }

    private byte[] hexToBytes (String hex){
        byte[] val = new byte[hex.length() / 2];

        for (int i = 0; i < val.length; i++) {
            int index = i * 2;
            int j = Integer.parseInt(hex.substring(index, index + 2), 16);
            val[i] = (byte) j;
        }
        return val;
    }
}
