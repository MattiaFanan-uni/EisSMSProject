package com.gruppo3.smsconnection.replicatednet.dictionary.command;

import com.gruppo3.smsconnection.connection.ResourceNetDictionary;

import java.io.Serializable;

public class RemoveResourceNetCommand<K extends Serializable,V extends Serializable> implements ResourceNetCommand<K,V> {

    private K key;

    public RemoveResourceNetCommand(K key)
    {
        if(key==null)
            throw new NullPointerException();

        this.key=key;
    }


    /**
     * execute an action over a ResourceNetDictionary
     *
     * @param dictionary dictionary in witch action is executed
     */
    @Override
    public void execute(ResourceNetDictionary<K, V> dictionary) {
        dictionary.removeResource(key);
    }
}
