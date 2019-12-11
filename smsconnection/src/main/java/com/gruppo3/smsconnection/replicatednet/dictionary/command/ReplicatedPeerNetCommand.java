package com.gruppo3.smsconnection.replicatednet.dictionary.command;

import com.gruppo3.smsconnection.connection.PeerNetDictionary;
import com.gruppo3.smsconnection.replicatednet.message.ReplicatedNetPeer;
import com.gruppo3.smsconnection.smsdatalink.message.SMSPeer;

public class ReplicatedPeerNetCommand implements PeerNetCommand<ReplicatedNetPeer, SMSPeer> {
    public static final char controlCode = '$';
    private StringParser<ReplicatedNetPeer> peerKeyParser;
    private StringParser<SMSPeer> peerValueParser;

    private static final int CODE_START = 0;
    private static final int CONTROL_COMPONENT = 1;
    private static final int TARGET_START = CODE_START + CONTROL_COMPONENT;
    private static final int ACTION_START = TARGET_START + CONTROL_COMPONENT;
    private static final int LENGTH_KEY_START = ACTION_START + CONTROL_COMPONENT;
    private static final int KEY_START = LENGTH_KEY_START + CONTROL_COMPONENT;

    public ReplicatedPeerNetCommand(StringParser<ReplicatedNetPeer> peerKeyParser, StringParser<SMSPeer> peerValueParser) {
        if (peerKeyParser == null || peerValueParser == null)
            throw new NullPointerException();

        this.peerKeyParser = peerKeyParser;
        this.peerValueParser = peerValueParser;

    }

    /**
     * execute an action over a PeerNetDictionary
     *
     * @param dictionary dictionary in witch action is executed
     * @param command    the <code>String</code> encoding the command to be executed
     * @return <code>true</code> if the <code>command</code> has been correctly executed, <code>false</code> otherwise
     */
    @Override
    public boolean execute(PeerNetDictionary<ReplicatedNetPeer, SMSPeer> dictionary, String command) {
        try {
            //test if is a valid command
            if (!isCommand(command))
                return false;

            //test if is directed to the right dictionary
            if (!command.substring(TARGET_START, ACTION_START).equals("P"))
                return false;

            //retrieve action
            String action = command.substring(ACTION_START, LENGTH_KEY_START);

            //retrieve key
            int keyLENGTH = command.charAt(LENGTH_KEY_START);
            String keyToParse = command.substring(KEY_START, KEY_START + keyLENGTH);
            ReplicatedNetPeer key = peerKeyParser.parseData(keyToParse);

            switch (action) {
                case "R":
                    return dictionary.removePeer(key) != null;
                case "I":
                    //retrieve value
                    int lengthValueEnd = KEY_START + keyLENGTH + CONTROL_COMPONENT;
                    int valueLENGTH = command.charAt(KEY_START + keyLENGTH);
                    String valueToParse = command.substring(lengthValueEnd, lengthValueEnd + valueLENGTH);
                    SMSPeer value = peerValueParser.parseData(valueToParse);
                    return dictionary.putPeerIfAbsent(key, value) == null;
            }

            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Gets the <code>String</code> encoding for the remove command to be execute
     *
     * @param peerKey the key identifying the pair to be removed
     * @return the <code>String</code> encoding for the remove command to be execute
     */
    @Override
    public String getRemoveCommand(ReplicatedNetPeer peerKey) {
        String key = peerKeyParser.parseString(peerKey);
        String keyLength = (char) key.length() + "";
        return controlCode + "P" + "R" + keyLength + key;
    }

    /**
     * Gets the <code>String</code> encoding for the insert command to be execute
     *
     * @param peerKey   the key identifying the pair to be inserted
     * @param peerValue the value of the pair to be inserted
     * @return the <code>String</code> encoding for the insert command to be execute
     */
    @Override
    public String getInsertCommand(ReplicatedNetPeer peerKey, SMSPeer peerValue) {
        String key = peerKeyParser.parseString(peerKey);
        String value = peerValueParser.parseString(peerValue);
        String keyLength = (char) key.length() + "";
        String valueLength = (char) value.length() + "";
        return controlCode + "P" + "I" + keyLength + key + valueLength + value;
    }

    private boolean isCommand(String command) {
        return command.charAt(0)==controlCode;
    }
}
