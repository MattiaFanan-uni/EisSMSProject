package com.gruppo3.smsconnection.replicatednet.manager;

import java.io.Serializable;

/**
 * @author Mattia Fanan
 * @param <A> action to be done
 * @param <D> action's data
 */
public interface NetCommand<A,D> extends Serializable {
    /**
     * Returns the action to be taken
     * @return the action to be taken
     */
    A getAction();

    /**
     * returns the data on which the action will be carried out
     * @return action's data
     */
    D getData();
}
