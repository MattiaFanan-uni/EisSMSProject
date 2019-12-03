package com.gruppo3.smsconnection.replicatednet.dictionary.command;

import com.gruppo3.smsconnection.connection.ResourceNetDictionary;

import java.io.Serializable;

public class AddResourceNetCommand<K extends Serializable,V extends Serializable> implements ResourceNetCommand<K,V> {

    private K key;
    private V value;

    public AddResourceNetCommand(K key, V value) {
        if (key == null || value == null)
            throw new NullPointerException();

        this.key = key;
        this.value = value;
    }

    /**
     * execute an action over a ResourceNetDictionary
     *
     * @param dictionary dictionary in witch action is executed
     */
    @Override
    public void execute(ResourceNetDictionary<K, V> dictionary) {
        dictionary.putResourceIfAbsent(key, value);
    }
}