package com.gruppo3.smsconnection.replicatednet.manager;

import android.telephony.SmsMessage;

import androidx.annotation.NonNull;

import com.gruppo3.smsconnection.connection.CommunicationHandler;
import com.gruppo3.smsconnection.connection.ResourceDictionary;
import com.gruppo3.smsconnection.connection.exception.InvalidPayloadException;
import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;
import com.gruppo3.smsconnection.connection.listener.ReceivedMessageListener;
import com.gruppo3.smsconnection.replicatednet.dictionary.ReplicatedNetDictionary;
import com.gruppo3.smsconnection.replicatednet.message.ReplicatedNetMessage;
import com.gruppo3.smsconnection.replicatednet.message.ReplicatedNetPeer;
import com.gruppo3.smsconnection.smsdatalink.manager.SMSManager;
import com.gruppo3.smsconnection.smsdatalink.message.SMSMessage;
import com.gruppo3.smsconnection.smsdatalink.message.SMSPeer;
import com.gruppo3.smsconnection.utils.ObjectSerializer;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

public class ReplicatedNetManager<K extends Serializable, V extends Serializable>
        implements Serializable,
        CommunicationHandler<ReplicatedNetMessage>,
        ResourceDictionary<K,V> ,
        ReceivedMessageListener<SMSMessage>{

    private transient SMSManager smsManager;
    private transient ReceivedMessageListener<ReplicatedNetMessage> listener;
    //da gestire tipo in un array
    private ReplicatedNetPeer replicatedNetMe;
    private SMSPeer smsMe;
    private ReplicatedNetDictionary<K,V> replicatedNetDictionary;
    private TreeSet<Long> invitedTokenList;

    public ReplicatedNetManager(ReplicatedNetPeer replicatedNetMe, SMSPeer smsMe){
        smsManager=SMSManager.getDefault();
        listener=null;
        this.replicatedNetMe= replicatedNetMe;
        this.smsMe=smsMe;
        replicatedNetDictionary =new ReplicatedNetDictionary<>(replicatedNetMe,smsMe);
        invitedTokenList=new TreeSet<>();
    }
    ///////////////////////////////////////////////////COMMUNICATION_HANDLER
    /**
     * Sends a valid message to a peer
     *
     * @param message The message to send
     */
    @Override
    public boolean sendMessage(ReplicatedNetMessage message) {

        SMSMessage smsMessage=null;

        SMSPeer destinationPeer= replicatedNetDictionary.getPeer(message.getDestinationPeer());

        try{
            smsMessage=new SMSMessage(destinationPeer,smsMe,message.getSDU());
        }
        catch (InvalidPeerException| InvalidPayloadException e){return false;}

        return smsManager.sendMessage(smsMessage);
    }

    /**
     * Adds a listener that gets called when a message is received
     *
     * @param listener The listener to wake up when a message is received
     */
    @Override
    public void addReceiveListener(ReceivedMessageListener<ReplicatedNetMessage> listener) {
        this.listener=listener;
    }

    /**
     * Removes the listener for received messages
     */
    @Override
    public void removeReceiveListener() {
        listener=null;
    }

    /**
     * If the specified key is not already associated with a value associates it with the given value and returns null, else returns the current value.
     *
     * @param resourceKey   key with which the specified value is to be associated
     * @param resourceValue value to be associated with the specified key
     * @return the previous value associated with the specified key, or null if there was no mapping for the key.
     */

    /////////////////////////////////////RESOURCE DICTIONARY///////////////////

    @Override
    public V putResourceIfAbsent(@NonNull K resourceKey, V resourceValue) {

        V result = replicatedNetDictionary.putResourceIfAbsent(resourceKey,resourceValue);
        if(result!=null)
        {
            broadcast(new ReplicatedNetCommand(NET_ACTION.ADD_RESOURCE, new AbstractMap.SimpleEntry(resourceKey,resourceValue)));
        }
        return result;
    }

    /**
     * Removes the resource having this key from this ReplicatedNetDictionary if present.
     *
     * @param resourceKey key of the resource to be removed
     * @return the previous value associated with key, or null if there was no mapping for key.
     */
    @Override
    public V removeResource(@NonNull K resourceKey) {

        V result = replicatedNetDictionary.removeResource(resourceKey);
        if(result!=null)
        {
            broadcast(new ReplicatedNetCommand(NET_ACTION.REMOVE_RESOURCE, resourceKey));
        }
        return result;
    }

    /**
     * Returns the resource value to which the specified resource key is mapped, or null if this map contains no mapping for the key
     *
     * @param resourceKey the key whose associated resource value is to be returned
     * @return the resource value to which the specified key is mapped, or null if this map contains no mapping for the key
     */
    @Override
    public V getResource(@NonNull K resourceKey) {
        return replicatedNetDictionary.getResource(resourceKey);
    }

    /**
     * Returns the number of resources in this dictionary.
     *
     * @return the number of resources in this dictionary.
     */
    @Override
    public int numberOfResources() {
        return replicatedNetDictionary.numberOfResources();
    }

    /**
     * Check if the Dictionary contains a resource having the specified key
     *
     * @param resourceKey key of the resource whose presence in this dictionary is to be tested
     * @return <code>true</code> if this dictionary contains a mapping for the specified key
     */
    @Override
    public boolean containsResourceKey(@NonNull K resourceKey) {
        return replicatedNetDictionary.containsResourceKey(resourceKey);
    }

    /**
     * Check if the Dictionary contains a resource having the specified value
     *
     * @param resourceValue value of the resource whose presence in this dictionary is to be tested
     * @return <code>true</code> if this dictionary contains a mapping for the specified value
     */
    @Override
    public boolean containsResourceValue(V resourceValue) {
        return replicatedNetDictionary.containsResourceValue(resourceValue);
    }

    /**
     * Return all the Resources in this dictionary
     *
     * @return an iterator on all resources in this dictionary
     */
    @Override
    public Iterator<Map.Entry<K, V>> getResourcesIterator() {
        return replicatedNetDictionary.getResourcesIterator();
    }

    ////////////////////////////////////////////BROADCAST
    private void broadcast(ReplicatedNetCommand command){

        Iterator<Map.Entry<ReplicatedNetPeer,SMSPeer>> peersIterator=replicatedNetDictionary.getPeersIteratorAscending();
        byte[] byteCommand=ObjectSerializer.getSerializedBytes(command);

        if(byteCommand!=null)
            while(peersIterator.hasNext()){
                ReplicatedNetPeer currentPeer=peersIterator.next().getKey();
                //i don't send to myself
                if(!currentPeer.equals(replicatedNetMe))
                    try {
                        ReplicatedNetMessage message = new ReplicatedNetMessage(currentPeer, replicatedNetMe, byteCommand);
                        sendMessage(message);
                    }
                    catch (Exception e){}
            }
    }
    /////////////////////////////////////////ADD PEER
    //TODO
    public void invite(SMSPeer toInvite){

    }

    private void handShake(ReplicatedNetCommand<Long> command){
        if(invitedTokenList.contains(command.getData()))
        {
            invitedTokenList.remove(command.getData());
            replicatedNetDictionary.putPeerIfAbsent( null,null);
            updateNewPeer();
        }
        //else
            //handShake();
    }

    private void updateNewPeer(){

    }
    ///////////////////////////////////////SMS_LISTENER
    /**
     * Called by NotificatonEraser whenever a message is received.
     *
     * @param message The message received
     */
    @Override
    public void onMessageReceived(SMSMessage message) {

    }

    public enum NET_ACTION{
        HANDSHAKE,
        ADD_PEER,
        REMOVE_PEER,
        ADD_RESOURCE,
        REMOVE_RESOURCE;
    }

}
