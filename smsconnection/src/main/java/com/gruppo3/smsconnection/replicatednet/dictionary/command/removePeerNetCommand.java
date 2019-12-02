package com.gruppo3.smsconnection.replicatednet.dictionary.command;

import com.gruppo3.smsconnection.replicatednet.dictionary.ReplicatedNetDictionary;
import com.gruppo3.smsconnection.replicatednet.message.ReplicatedNetPeer;

import java.io.Serializable;

public class removePeerNetCommand<K extends Serializable> implements NetCommand {
    private ReplicatedNetPeer netPeer;

    public removePeerNetCommand(ReplicatedNetPeer netPeer)
    {
        if(netPeer==null)
            throw new NullPointerException();

        this.netPeer=netPeer;
    }

    /**
     * execute an action over a ReplicatedNetDictionary
     *
     * @param dictionary dictionary in witch action is executed
     */
    @Override
    public void execute(ReplicatedNetDictionary dictionary) {
        dictionary.removePeer(netPeer);
    }
}
