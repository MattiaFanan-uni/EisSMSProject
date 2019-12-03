package com.gruppo3.smsconnection.connection;

import com.gruppo3.smsconnection.replicatednet.dictionary.command.AddResourceNetCommand;
import com.gruppo3.smsconnection.replicatednet.dictionary.command.RemoveResourceNetCommand;

import java.io.Serializable;

public interface ResourceNetDictionary<K extends Serializable, V extends Serializable> extends ResourceDictionary<K,V> {

    /**
     * Return a command for inserting a resource in a ResourceNetDictionary
     *
     * @param resourceKey       Key of the resource to insert
     * @param resourceValue     Value of the resource to insert
     * @return  AddResourceCommand ready for execute the requested insertion
     */
    AddResourceNetCommand<K,V> getAddResourceCommand(K resourceKey, V resourceValue);

    /**
     * Return a command for removing a resource in a ResourceNetDictionary
     *
     * @param resourceKey       Key of the resource to remove
     * @return  RemoveResourceCommand ready for execute the requested deletion
     */
    RemoveResourceNetCommand<K,V> getRemoveResourceCommand(K resourceKey);
}
