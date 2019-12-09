package com.gruppo3.smsconnection.replicatednet.manager;

import androidx.annotation.NonNull;

import com.gruppo3.smsconnection.connection.CommunicationHandler;
import com.gruppo3.smsconnection.connection.ResourceDictionary;
import com.gruppo3.smsconnection.connection.exception.InvalidMessageException;
import com.gruppo3.smsconnection.connection.exception.InvalidPayloadException;
import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;
import com.gruppo3.smsconnection.connection.listener.ReceivedMessageListener;
import com.gruppo3.smsconnection.replicatednet.dictionary.ReplicatedNetDictionary;
import com.gruppo3.smsconnection.replicatednet.dictionary.command.StringParser;
import com.gruppo3.smsconnection.replicatednet.message.ReplicatedNetMessage;
import com.gruppo3.smsconnection.replicatednet.message.ReplicatedNetPeer;
import com.gruppo3.smsconnection.replicatednet.message.ReplicatedNetUnknownDestinationMessage;
import com.gruppo3.smsconnection.smsdatalink.manager.SMSManager;
import com.gruppo3.smsconnection.smsdatalink.message.SMSMessage;
import com.gruppo3.smsconnection.smsdatalink.message.SMSPeer;
import com.gruppo3.smsconnection.utils.ObjectSerializer;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class ReplicatedNetManager<K extends Serializable, V extends Serializable>
        implements Serializable,
        CommunicationHandler<ReplicatedNetMessage>,
        ResourceDictionary<K, V>,
        ReceivedMessageListener<SMSMessage> {

    private transient SMSManager smsManager;
    private transient ReceivedMessageListener<ReplicatedNetMessage> listener;
    //da gestire tipo in un array
    private ReplicatedNetPeer replicatedNetMe;
    private SMSPeer smsMe;
    private ReplicatedNetDictionary<K, V> replicatedNetDictionary;
    private TreeMap<Long, SMSPeer> invitedTokenList;

    //////////////////////////////////////CONSTRUCTOR
    public ReplicatedNetManager(ReplicatedNetPeer replicatedNetMe, SMSPeer smsMe, @NonNull StringParser<K> resourceKeyParser, @NonNull StringParser<V> resourceValueParser) {
        smsManager = SMSManager.getDefault();
        smsManager.addReceiveListener(this);
        listener = null;
        this.replicatedNetMe = replicatedNetMe;
        this.smsMe = smsMe;
        replicatedNetDictionary = new ReplicatedNetDictionary<>(replicatedNetMe, smsMe, resourceKeyParser, resourceValueParser);
        invitedTokenList = new TreeMap<>();
    }
    ///////////////////////////////////////////////////COMMUNICATION_HANDLER

    /**
     * Sends a valid message to a peer
     *
     * @param message The message to send
     */
    @Override
    public boolean sendMessage(ReplicatedNetMessage message) {

        SMSMessage smsMessage = null;

        SMSPeer destinationPeer = replicatedNetDictionary.getPeer(message.getDestinationPeer());

        try {
            smsMessage = new SMSMessage(destinationPeer, smsMe, message.getSDU());
        } catch (InvalidPeerException | InvalidPayloadException e) {
            return false;
        }

        return smsManager.sendMessage(smsMessage);
    }

    /**
     * Adds a listener that gets called when a message is received
     *
     * @param listener The listener to wake up when a message is received
     */
    @Override
    public void addReceiveListener(ReceivedMessageListener<ReplicatedNetMessage> listener) {
        this.listener = listener;
    }

    /**
     * Removes the listener for received messages
     */
    @Override
    public void removeReceiveListener() {
        listener = null;
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

        V result = replicatedNetDictionary.putResourceIfAbsent(resourceKey, resourceValue);
        if (result == null) {
            broadcast(replicatedNetDictionary.getAddResourceCommand(resourceKey, resourceValue));
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
        if (result != null) {
            broadcast(replicatedNetDictionary.getRemoveResourceCommand(resourceKey));
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
    private void broadcast(String resourceNetCommand) {

        Iterator<Map.Entry<ReplicatedNetPeer, SMSPeer>> peersIterator = replicatedNetDictionary.getPeersIteratorAscending();
        byte[] byteCommand = ObjectSerializer.getSerializedBytes(resourceNetCommand);

        if (byteCommand != null)
            while (peersIterator.hasNext()) {
                ReplicatedNetPeer currentPeer = peersIterator.next().getKey();
                //i don't send to myself
                if (!currentPeer.equals(replicatedNetMe))
                    try {
                        ReplicatedNetMessage message = new ReplicatedNetMessage(currentPeer, replicatedNetMe, byteCommand);
                        sendMessage(message);
                    } catch (Exception e) {
                    }
            }
    }

    /////////////////////////////////////////ADD PEER
    //TODO
    public void invite(SMSPeer toInvite) {
        try {
            Invitation invitation = new Invitation();
            invitedTokenList.put(invitation.getCode(), toInvite);
            ReplicatedNetUnknownDestinationMessage message = new ReplicatedNetUnknownDestinationMessage(replicatedNetMe, ObjectSerializer.getSerializedBytes(invitation));
            sendMessage(message, toInvite);
        } catch (Exception e) {
        }
    }

    private void handShake(Invitation invitation, ReplicatedNetPeer sourcePeer, SMSPeer toHandshake) {
        if (invitedTokenList.containsKey(invitation.getCode())) {
            SMSPeer toAddSmsPeer = invitedTokenList.remove(invitation.getCode());
            replicatedNetDictionary.putPeerIfAbsent(sourcePeer, toAddSmsPeer);
            updateNewPeer(sourcePeer);
        } else {
            try {
                ReplicatedNetUnknownDestinationMessage message = new ReplicatedNetUnknownDestinationMessage(replicatedNetMe, ObjectSerializer.getSerializedBytes(invitation));
                sendMessage(message, toHandshake);
            } catch (Exception e) {
            }
        }

    }

    private void updateNewPeer(ReplicatedNetPeer netPeer) {
        Iterator<Map.Entry<ReplicatedNetPeer, SMSPeer>> peersIterator = replicatedNetDictionary.getPeersIteratorAscending();
        Iterator<Map.Entry<K, V>> resourcesIterator = replicatedNetDictionary.getResourcesIterator();

        //send all peers to new peer
        while (peersIterator.hasNext()) {

            Map.Entry<ReplicatedNetPeer, SMSPeer> entry = peersIterator.next();
            ReplicatedNetPeer currentNetPeer = entry.getKey();
            SMSPeer currentSmsPeer = entry.getValue();

            try {
                ReplicatedNetMessage message = new ReplicatedNetMessage(netPeer, replicatedNetMe,
                        ObjectSerializer.getSerializedBytes(replicatedNetDictionary.getAddPeerNetCommand(currentNetPeer, currentSmsPeer)));
                sendMessage(message);

            } catch (Exception e) {
            }

        }

        //send all resources to new peer
        while (resourcesIterator.hasNext()) {

            Map.Entry<K, V> entry = resourcesIterator.next();
            K currentKey = entry.getKey();
            V currentValue = entry.getValue();

            try {
                ReplicatedNetMessage message = new ReplicatedNetMessage(netPeer, replicatedNetMe,
                        ObjectSerializer.getSerializedBytes(replicatedNetDictionary.getAddResourceCommand(currentKey, currentValue)));
                sendMessage(message);

            } catch (Exception e) {
            }

        }

    }
    //////////////////////////////////////PING

    /**
     * Sends a ping to a sms peer
     *
     * @param message The message to send
     */
    public boolean sendMessage(ReplicatedNetUnknownDestinationMessage message, SMSPeer destinationSmsPeer) {

        SMSMessage smsMessage = null;

        try {
            smsMessage = new SMSMessage(destinationSmsPeer, smsMe, message.getSDU());
        } catch (InvalidPeerException | InvalidPayloadException e) {
            return false;
        }

        return smsManager.sendMessage(smsMessage);
    }

    ///////////////////////////////////////SMS_LISTENER

    /**
     * Called by NotificatonEraser whenever a message is received.
     *
     * @param message The message received
     */
    @Override
    public void onMessageReceived(SMSMessage message) {
        ReplicatedNetUnknownDestinationMessage uKNetMessage = null;
        ReplicatedNetMessage netMessage = null;

        try {
            netMessage = ReplicatedNetMessage.buildFromSDU(message.getData());
        } catch (InvalidPeerException | InvalidPayloadException | InvalidMessageException e) {
        }

        if (netMessage != null) {
            //retrieve command
            String command = netMessage.getData().toString();
            //only one executes correctly
            replicatedNetDictionary.getPeerCommandExecutor().execute(replicatedNetDictionary, command);
            replicatedNetDictionary.getResourceCommandExecutor().execute(replicatedNetDictionary, command);

        } else {
            try {
                uKNetMessage = ReplicatedNetUnknownDestinationMessage.buildFromSDU(message.getData());

                Invitation invitation = ObjectSerializer.getDeserializedObject(uKNetMessage.getData());

                handShake(invitation, uKNetMessage.getSourcePeer(), message.getSourcePeer());
            } catch (InvalidPeerException | InvalidPayloadException | InvalidMessageException e) {
            }
        }
    }

}
