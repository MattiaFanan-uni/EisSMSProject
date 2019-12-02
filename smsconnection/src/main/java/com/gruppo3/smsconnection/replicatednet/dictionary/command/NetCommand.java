package com.gruppo3.smsconnection.replicatednet.dictionary.command;

import com.gruppo3.smsconnection.replicatednet.dictionary.ReplicatedNetDictionary;

import java.io.Serializable;

public interface NetCommand extends Serializable {

    /**
     * execute an action over a ReplicatedNetDictionary
     * @param dictionary dictionary in witch action is executed
     */
    void execute(ReplicatedNetDictionary dictionary);
}
