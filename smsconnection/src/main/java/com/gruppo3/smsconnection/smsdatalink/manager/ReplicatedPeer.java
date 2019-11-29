package com.gruppo3.smsconnection.smsdatalink.manager;

import com.gruppo3.smsconnection.smsdatalink.SMSPeer;

/**
 * @author Riccardo Crociani
 *
 * ReplicatedPeer contains the peer and the name(ip) linked to it
 */

public class ReplicatedPeer {

    private SMSPeer netPeer;
    private String netIp;
    private boolean hasPermission;
    private ReplicatedDictionary netDictionary;
    private  static final int IP_LENGTH = 4;

    public ReplicatedPeer(ReplicatedDictionary netDictionary, SMSPeer netPeer, String netIp){
        this.netPeer = netPeer;
        this.netIp = netIp;
        this.netDictionary = netDictionary;
    }

    /**
     * Uses netPeer and a netIp to build the ReplicatedPeer and if the ReplicatedPeer calling
     * the method has the writing permission, the ReplicatedPeer built is added to the netDictionary
     *
     * @param netDictionary
     * @param netPeer
     * @param netIp
     */
    public void addToReplicatedNetDictionary(ReplicatedDictionary netDictionary, SMSPeer netPeer, String netIp){
        if(hasPermission()) {
            ReplicatedPeer replicatedPeerToAdd = new ReplicatedPeer(netDictionary, netPeer, netIp);
            netDictionary.addToNetDictionary(replicatedPeerToAdd, netPeer);
        }
    }

    /**
     * Return true if the ReplicatedPeer has writing permission
     * @return boolean
     */
    public boolean hasPermission(){
        return hasPermission;
    }

    /**
     *
     * @return the Ip
     */
    public String getIp(){
        return netIp;
    }

    /**
     * Check if the ip is valid
     * @param ip
     * @return true if ip is 4 characters long
     */
    public boolean isValidIp(String ip){
        return ip.length() == IP_LENGTH;
    }

    public void enablePermission(){
        hasPermission = true;
    }

    public void disablePermission(){
        hasPermission = false;
    }
}
