package com.gruppo3.smsconnection.replicatednet.dictionary.command;

import com.gruppo3.smsconnection.replicatednet.dictionary.ReplicatedNetDictionary;

import java.io.Serializable;

public class addResourceNetCommand<K extends Serializable,V extends Serializable> implements NetCommand {

    private K key;
    private V value;

    public addResourceNetCommand(K key, V value) {
        if (key == null || value == null)
            throw new NullPointerException();

        this.key = key;
        this.value = value;
    }

    @Override
    public void execute(ReplicatedNetDictionary dictionary) {
        dictionary.putResourceIfAbsent(key, value);
    }
}