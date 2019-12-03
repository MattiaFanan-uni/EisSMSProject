package com.gruppo3.smsconnection.replicatednet.dictionary.command;

import com.gruppo3.smsconnection.connection.Peer;
import com.gruppo3.smsconnection.connection.PeerNetDictionary;

import java.io.Serializable;

public interface PeerNetCommand<K extends Peer,V extends Peer> extends Serializable {
    /**
     * execute an action over a PeerNetDictionary
     *
     * @param dictionary dictionary in witch action is executed
     */
    void execute(PeerNetDictionary<K, V> dictionary);
}
