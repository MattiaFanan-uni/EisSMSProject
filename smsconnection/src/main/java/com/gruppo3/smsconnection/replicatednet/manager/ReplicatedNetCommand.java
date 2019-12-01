package com.gruppo3.smsconnection.replicatednet.manager;

import androidx.annotation.NonNull;

import java.util.Map;

public class ReplicatedNetCommand<D> implements NetCommand<ReplicatedNetManager.NET_ACTION, D> {
    private ReplicatedNetManager.NET_ACTION action;
    private D data;

    public ReplicatedNetCommand(ReplicatedNetManager.NET_ACTION action,@NonNull D data){
        if(action==null || data==null)
            throw new NullPointerException();
        this.action=action;
        this.data=data;
    }
    /**
     * Returns the action to be taken
     *
     * @return the action to be taken
     */
    @Override
    public ReplicatedNetManager.NET_ACTION getAction() {
        return action;
    }

    /**
     * returns the data on which the action will be carried out
     *
     * @return action's data
     */
    @Override
    public D getData() {
        return data;
    }
}
