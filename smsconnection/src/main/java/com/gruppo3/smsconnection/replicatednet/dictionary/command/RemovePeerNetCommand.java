package com.gruppo3.smsconnection.replicatednet.dictionary.command;

import com.gruppo3.smsconnection.connection.Peer;
import com.gruppo3.smsconnection.connection.PeerNetDictionary;

public class RemovePeerNetCommand<K extends Peer,V extends Peer> implements PeerNetCommand<K,V> {

    private K keyPeer;

    public RemovePeerNetCommand(K keyPeer)
    {
        if(keyPeer==null)
            throw new NullPointerException();

        this.keyPeer=keyPeer;
    }


    /**
     * execute an action over a PeerNetDictionary
     *
     * @param dictionary dictionary in witch action is executed
     */
    @Override
    public void execute(PeerNetDictionary<K, V> dictionary) {
        dictionary.removePeer(keyPeer);
    }
}
