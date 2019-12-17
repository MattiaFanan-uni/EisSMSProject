package com.gruppo3.smsconnection.replicatednet.manager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ReplicatedNetManagerPoolTest {

    @Test
    public void get() {
        ReplicatedNetManagerPool pool = new ReplicatedNetManagerPool();
        ReplicatedNetManager<String, String> netManager1 = pool.get();
        pool.release(netManager1);
        assertEquals(netManager1, pool.get());

        ReplicatedNetManager<String, Integer> netManager2 = pool.get();
        ReplicatedNetManager<String, Integer> netManager2cpy = new ReplicatedNetManager<>();
        assertEquals(netManager2.getDictionary(),netManager2cpy.getDictionary());
    }

    @Test
    public void release() {
        ReplicatedNetManagerPool pool = new ReplicatedNetManagerPool();
        ReplicatedNetManager<String, String> netManager = new ReplicatedNetManager<>();
        pool.release(netManager);
        assertEquals(netManager, pool.<String, String>get());
    }

    @Test
    public void getInstance(){
        assertEquals(ReplicatedNetManagerPool.getInstance(),
        ReplicatedNetManagerPool.getInstance());
        ReplicatedNetManagerPool pool = ReplicatedNetManagerPool.getInstance();
        assertEquals(pool, ReplicatedNetManagerPool.getInstance());

    }
}