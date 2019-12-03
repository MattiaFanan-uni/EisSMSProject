package com.gruppo3.smsconnection.connection;

import com.gruppo3.smsconnection.connection.PeerDictionary;
import com.gruppo3.smsconnection.replicatednet.dictionary.command.AddPeerNetCommand;
import com.gruppo3.smsconnection.replicatednet.dictionary.command.RemovePeerNetCommand;

public interface PeerNetDictionary<K extends Peer,V extends Peer> extends PeerDictionary<K,V> {
    /**
     * Return a command for inserting a resource in a PeerNetDictionary
     *
     * @param peerKey       Key of the peer to insert
     * @param peerValue     Value of the peer to insert
     * @return  AddPeerNetCommand ready for execute the requested insertion
     */
    AddPeerNetCommand<K,V> getAddPeerNetCommand(K peerKey, V peerValue);

    /**
     * Return a command for removing a peer in a PeerNetDictionary
     *
     * @param peerKey       Key of the peer to remove
     * @return  RemovePeerNetCommand ready for execute the requested deletion
     */
    RemovePeerNetCommand<K,V> getRemovePeerNetCommand(K peerKey);
}
