package com.gruppo3.smsconnection.connection;

import com.gruppo3.smsconnection.replicatednet.dictionary.command.ResourceNetCommand;

import java.io.Serializable;

public interface ResourceNetDictionary<K extends Serializable, V extends Serializable> extends ResourceDictionary<K, V> {

    /**
     * Returns the <code>String</code> encoding for the insert command to be execute
     *
     * @param resourceKey   Key of the resource to insert
     * @param resourceValue Value of the resource to insert
     * @return the <code>String</code> encoding for the insert command to be execute
     */
    String getAddResourceCommand(K resourceKey, V resourceValue);

    /**
     * Returns the <code>String</code> encoding for the remove command to be execute
     *
     * @param resourceKey Key of the resource to remove
     * @return the <code>String</code> encoding for the remove command to be execute
     */
    String getRemoveResourceCommand(K resourceKey);

    /**
     * Gets the command executor for execute <code>String</code> encoded commands
     *
     * @return the command executor for execute <code>String</code> encoded commands
     */
    ResourceNetCommand<K, V> getResourceCommandExecutor();

}
