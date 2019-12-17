package com.gruppo3.smsconnection.replicatednet.manager;

/**
 * @author Riccardo Crociani
 *
 * ReplicatedNetManagerPool
 */

import java.io.Serializable;
import java.util.ArrayList;

public class ReplicatedNetManagerPool {

    private ArrayList<ReplicatedNetManager> pool;
    private static ReplicatedNetManagerPool replicatedNetManagerPoolInstance = null;

    public ReplicatedNetManagerPool() {
        pool = new ArrayList<>();
    }

    /**
     * Singleton
     * @return
     */
    public static ReplicatedNetManagerPool getInstance() {
        if (replicatedNetManagerPoolInstance == null)
            replicatedNetManagerPoolInstance = new ReplicatedNetManagerPool();

        return replicatedNetManagerPoolInstance;
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @return a ReplicatedNetManager from the pool to set up
     */
    public <K extends Serializable, V extends Serializable> ReplicatedNetManager<K,V> get() {
        ReplicatedNetManager ret = null;
        if (pool.size() > 0)
            ret = pool.remove(0);
        else
            ret = new ReplicatedNetManager<K, V>();
        return ret;
    }

    /**
     * Add to the pool a ReplicatedNetManager object
     * @param object
     */
    public void release(ReplicatedNetManager object) {
        pool.add(object);
    }
}
