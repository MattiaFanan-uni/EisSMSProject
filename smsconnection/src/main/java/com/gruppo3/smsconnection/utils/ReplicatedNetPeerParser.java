package com.gruppo3.smsconnection.utils;

import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;
import com.gruppo3.smsconnection.replicatednet.dictionary.command.StringParser;
import com.gruppo3.smsconnection.replicatednet.message.ReplicatedNetPeer;

import java.io.UnsupportedEncodingException;

public class ReplicatedNetPeerParser implements StringParser<ReplicatedNetPeer> {

    @Override
    public String parseString(ReplicatedNetPeer data) {
        try {
            //iso-8859-1 has 1to1 mapping from bytes to char
            return new String(data.getAddress(), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    @Override
    public ReplicatedNetPeer parseData(String string) {
        try {
            return new ReplicatedNetPeer(string.getBytes("ISO-8859-1"));
        } catch (InvalidPeerException | UnsupportedEncodingException e) {
            return null;
        }
    }
}
