package com.gruppo3.smsconnection.connection;

import com.gruppo3.smsconnection.replicatednet.dictionary.command.PeerNetCommand;

public interface PeerNetDictionary<K extends Peer, V extends Peer> extends PeerDictionary<K, V> {
    /**
     * Return the <code>String</code> encoding for the insert command to be execute
     *
     * @param peerKey   Key of the peer to insert
     * @param peerValue Value of the peer to insert
     * @return the <code>String</code> encoding for the insert command to be execute
     */
    String getAddPeerNetCommand(K peerKey, V peerValue);

    /**
     * Return the <code>String</code> encoding for the remove command to be execute
     *
     * @param peerKey Key of the peer to remove
     * @return the <code>String</code> encoding for the remove command to be execute
     */
    String getRemovePeerNetCommand(K peerKey);

    /**
     * Gets the command executor for execute <code>String</code> encoded commands
     *
     * @return the command executor for execute <code>String</code> encoded commands
     */
    PeerNetCommand<K, V> getPeerCommandExecutor();
}
