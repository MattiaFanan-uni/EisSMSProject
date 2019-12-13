package com.gruppo3.smsconnection.replicatednet.dictionary.command;

import com.gruppo3.smsconnection.connection.ResourceNetDictionary;

import java.io.Serializable;

public interface ResourceNetCommand<K extends Serializable, V extends Serializable> extends Serializable {

    /**
     * execute an action over a PeerNetDictionary
     *
     * @param dictionary dictionary in witch action is executed
     * @param command    the <code>String</code> encoding the command to be executed
     * @return <code>true</code> if the <code>command</code> has been correctly executed, <code>false</code> otherwise
     */
    boolean execute(ResourceNetDictionary<K, V> dictionary, String command);

    /**
     * Gets the <code>String</code> encoding for the remove command to be execute
     *
     * @param resourceKey the key identifying the pair to be removed
     * @return the <code>String</code> encoding for the remove command to be execute
     */
    String getRemoveCommand(K resourceKey);

    /**
     * Gets the <code>String</code> encoding for the insert command to be execute
     *
     * @param resourceKey   the key identifying the pair to be inserted
     * @param resourceValue the value of the pair to be inserted
     * @return the <code>String</code> encoding for the insert command to be execute
     */
    String getInsertCommand(K resourceKey, V resourceValue);
}
