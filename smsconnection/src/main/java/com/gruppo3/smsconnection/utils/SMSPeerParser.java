package com.gruppo3.smsconnection.utils;

import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;
import com.gruppo3.smsconnection.replicatednet.dictionary.command.StringParser;
import com.gruppo3.smsconnection.smsdatalink.message.SMSPeer;
/**
 * Utility object used to map a {@link SMSPeer} to a String and reverse
 *
 * @author Mattia Fanan
 */
public class SMSPeerParser implements StringParser<SMSPeer> {
    /**
     * Parses a String from a {@link SMSPeer}
     *
     * @param data the {@link SMSPeer} to parse from
     * @return the parsed String
     */
    @Override
    public String parseString(SMSPeer data) {
        return data.getAddress();
    }

    /**
     * Parses a {@link SMSPeer} from the passed String
     *
     * @param string the string to parse from
     * @return the parsed {@link SMSPeer}
     */
    @Override
    public SMSPeer parseData(String string) {
        try {
            return new SMSPeer(string);
        } catch (InvalidPeerException e) {
            return null;
        }
    }
}
