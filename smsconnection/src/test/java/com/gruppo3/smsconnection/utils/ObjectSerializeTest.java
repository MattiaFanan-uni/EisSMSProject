package com.gruppo3.smsconnection.utils;

import com.gruppo3.smsconnection.replicatednet.message.ReplicatedNetPeer;
import com.gruppo3.smsconnection.replicatednet.message.ReplicatedNetPeerTest;

import org.junit.Assert;
import org.junit.Test;

public class ObjectSerializeTest {

    @Test
    public void serializationDeserializationEqualsTest() {
        ReplicatedNetPeer peer = null;
        try {
            peer = new ReplicatedNetPeer(ReplicatedNetPeerTest.validAddress);
        } catch (Exception e) {
            Assert.fail("error in ReplicatedNetPeer tests");
        }

        byte[] serialization = ObjectSerializer.getSerializedBytes(peer);

        if (serialization == null)
            Assert.fail("byte[] serialization shouldn't be null");

        ReplicatedNetPeer deserializedPeer = ObjectSerializer.<ReplicatedNetPeer>getDeserializedObject(serialization);

        if (deserializedPeer == null)
            Assert.fail("deserialized object shouldn't be null");

        if (!peer.equals(deserializedPeer))
            Assert.fail("the two objects should be equal");

    }
}
