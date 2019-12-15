package com.gruppo3.smslibrary;

import com.gruppo3.smslibrary.types.Peer;

import java.util.HashMap;
import java.util.Map;

public class NetworkManager {

    private static Map<String, Peer> routingTable = new HashMap<String, Peer>(); // Subtree ID, Exit node

    public enum Commands {
        PING,
        FIND_VALUE,
        FIND_NODE,
        STORE
    }


}
