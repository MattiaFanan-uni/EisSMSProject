package com.gruppo3.smsconnection.replicatednet.dictionary.command;

import com.gruppo3.smsconnection.replicatednet.dictionary.ReplicatedNetDictionary;
import com.gruppo3.smsconnection.replicatednet.message.ReplicatedNetPeer;
import com.gruppo3.smsconnection.smsdatalink.message.SMSPeer;

public class addPeerNetCommand implements NetCommand {

    private ReplicatedNetPeer netPeer;
    private SMSPeer smsPeer;

    public addPeerNetCommand(ReplicatedNetPeer netPeer, SMSPeer smsPeer)
    {
        if(netPeer==null || smsPeer==null)
            throw new NullPointerException();

        this.smsPeer=smsPeer;
        this.netPeer=netPeer;
    }

    @Override
    public void execute(ReplicatedNetDictionary dictionary) {
        dictionary.putPeerIfAbsent(netPeer,smsPeer);
    }
}
