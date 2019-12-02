package com.gruppo3.smsconnection.replicatednet.dictionary.command;

import com.gruppo3.smsconnection.replicatednet.dictionary.ReplicatedNetDictionary;

import java.io.Serializable;

public class removeResourceNetCommand<K extends Serializable> implements NetCommand {
    private K key;

    public removeResourceNetCommand(K key)
    {
        if(key==null)
            throw new NullPointerException();

        this.key=key;
    }

    /**
     * execute an action over a ReplicatedNetDictionary
     *
     * @param dictionary dictionary in witch action is executed
     */
    @Override
    public void execute(ReplicatedNetDictionary dictionary) {
        dictionary.removeResource(key);
    }
}
