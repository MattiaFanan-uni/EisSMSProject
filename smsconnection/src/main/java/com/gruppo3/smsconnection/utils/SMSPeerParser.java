package com.gruppo3.smsconnection.utils;

import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;
import com.gruppo3.smsconnection.replicatednet.dictionary.command.StringParser;
import com.gruppo3.smsconnection.smsdatalink.message.SMSPeer;

public class SMSPeerParser implements StringParser<SMSPeer> {
    @Override
    public String parseString(SMSPeer data) {
        return data.getAddress();
    }

    @Override
    public SMSPeer parseData(String string) {
        try {
            return new SMSPeer(string);
        } catch (InvalidPeerException e) {
            return null;
        }
    }
}
