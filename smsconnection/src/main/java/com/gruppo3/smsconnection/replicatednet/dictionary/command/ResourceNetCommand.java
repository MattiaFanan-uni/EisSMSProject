package com.gruppo3.smsconnection.replicatednet.dictionary.command;

import com.gruppo3.smsconnection.connection.ResourceNetDictionary;

import java.io.Serializable;

public interface ResourceNetCommand<K extends Serializable,V extends Serializable> extends Serializable {
    /**
     * execute an action over a ResourceNetDictionary
     *
     * @param dictionary dictionary in witch action is executed
     */
    void execute(ResourceNetDictionary<K,V> dictionary);
}
