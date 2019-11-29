package com.gruppo3.smsconnection.smsdatalink.manager;

import com.gruppo3.smsconnection.smsdatalink.SMSPeer;

/**
 * @author Riccardo Crociani
 *
 * ReplicatedDictionary contains 2 NetworkDictionary:
 * - genericDictionary<K, V> Risorse-utenti
 * - netDictionary<ReplicatedPeer, SMSPeer> contains the peer allowed to write and read the dictionray
 */

public class ReplicatedDictionary<K,V> extends NetworkDictionary {
    private NetworkDictionary<K,V> genericDictionary;
    private NetworkDictionary<ReplicatedPeer, SMSPeer> netDictionary;
    private ReplicatedPeer namePeer;
    private SMSPeer peer;


    public ReplicatedDictionary(NetworkDictionary genericDictionary, NetworkDictionary netDictionary){
        this.genericDictionary = genericDictionary;
        this.netDictionary = netDictionary;
    }

    /**
     * If the Ip is valid the ReplicatedPeer and the SMSPeer are added as new element to the netDictionary
     * The ReplicatedPeer added obtains the reading and writing permission
     * @param namePeer
     * @param peer
     */
    public void addToNetDictionary(ReplicatedPeer namePeer, SMSPeer peer){
        if (namePeer.isValidIp(namePeer.getIp())) {
            netDictionary.add(namePeer, peer);
            namePeer.enablePermission();
        }
    }

    /**
     * The element that has as key the ReplicatedPeer specified is removed from the NetDictionary and
     * it loses the writing and reading permission
     * @param namePeer
     * @return
     */
    public boolean removeFromNetDictionary(ReplicatedPeer namePeer){
        namePeer.disablePermission();
        return netDictionary.remove(namePeer);
    }

    /**
     * Returns the SMSPeer associated
     * @param namePeer
     * @return
     */
    public SMSPeer getPeerFromNetDictionary(ReplicatedPeer namePeer){
        return netDictionary.getValue(namePeer);
    }

}
