package com.gruppo3.smsconnection.replicatednet.dictionary.command;

import com.gruppo3.smsconnection.connection.Peer;
import com.gruppo3.smsconnection.connection.PeerNetDictionary;

public class AddPeerNetCommand<K extends Peer,V extends Peer> implements PeerNetCommand<K,V> {

    private K keyPeer;
    private V valuePeer;

    public AddPeerNetCommand(K keyPeer, V valuePeer)
    {
        if(keyPeer==null || valuePeer==null)
            throw new NullPointerException();

        this.keyPeer=keyPeer;
        this.valuePeer=valuePeer;
    }


    /**
     * execute an action over a PeerNetDictionary
     *
     * @param dictionary dictionary in witch action is executed
     */
    @Override
    public void execute(PeerNetDictionary<K,V> dictionary) {
        dictionary.putPeerIfAbsent(keyPeer, valuePeer);
    }
}
