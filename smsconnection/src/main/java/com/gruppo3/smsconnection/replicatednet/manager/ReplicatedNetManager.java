package com.gruppo3.smsconnection.replicatednet.manager;

import android.util.Log;

import androidx.annotation.NonNull;

import com.gruppo3.smsconnection.connection.CommunicationHandler;
import com.gruppo3.smsconnection.connection.exception.InvalidMessageException;
import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;
import com.gruppo3.smsconnection.connection.listener.ReceivedMessageListener;
import com.gruppo3.smsconnection.replicatednet.dictionary.ReplicatedNetDictionary;
import com.gruppo3.smsconnection.replicatednet.dictionary.command.ReplicatedPeerNetCommand;
import com.gruppo3.smsconnection.replicatednet.dictionary.command.ReplicatedResourceNetCommand;
import com.gruppo3.smsconnection.replicatednet.message.ReplicatedNetMessage;
import com.gruppo3.smsconnection.replicatednet.message.ReplicatedNetPeer;
import com.gruppo3.smsconnection.smsdatalink.manager.SMSManager;
import com.gruppo3.smsconnection.smsdatalink.message.SMSMessage;
import com.gruppo3.smsconnection.smsdatalink.message.SMSPeer;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class ReplicatedNetManager<K extends Serializable, V extends Serializable>
        implements Serializable,
        CommunicationHandler<ReplicatedNetMessage>,
        ReceivedMessageListener<SMSMessage> {

    private ReceivedMessageListener<ReplicatedNetMessage> listener;
    private ReplicatedNetPeer replicatedNetMe;
    private SMSPeer smsMe;
    private ReplicatedNetDictionary<K, V> replicatedNetDictionary;
    private TreeMap<Long, SMSPeer> invitedTokenList;

    /**
     * Protected constructor uses object pool
     */
    protected ReplicatedNetManager() {}

    protected void setup(){
        listener = null;
        invitedTokenList = new TreeMap<>();
        SMSManager.getDefault().addReceiveListener(this);
    }

    protected void tearDown(){
        listener = null;
        replicatedNetDictionary = null;
        SMSManager.getDefault().removeReceiveListener();
    }

    //////////////////////////////////////////////////ADD
    public void addDictionary(ReplicatedNetPeer replicatedNetMe, SMSPeer smsMe, ReplicatedNetDictionary<K, V> dictionary) {
        this.replicatedNetMe = replicatedNetMe;
        this.smsMe = smsMe;
        replicatedNetDictionary = dictionary;
    }

    public ReplicatedNetDictionary<K, V> getDictionary() {
        return replicatedNetDictionary;
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
        } catch (InvalidPeerException e) {
            Log.d("COMMUNICATION", "peer exception in REP send");
            return false;

        } catch (InvalidMessageException e) {
            Log.d("COMMUNICATION", "message exception in REP send");
            return false;
        }

        return SMSManager.getDefault().sendMessage(smsMessage);
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

    ////////////////////////////////////////////BROADCAST
    public void broadcast(@NonNull String resourceNetCommand) {

        if (resourceNetCommand == null)
            throw new NullPointerException();

        Iterator<Map.Entry<ReplicatedNetPeer, SMSPeer>> peersIterator = replicatedNetDictionary.getPeersIteratorAscending();

        while (peersIterator.hasNext()) {
            ReplicatedNetPeer currentPeer = peersIterator.next().getKey();
            //i don't send to myself
            if (!currentPeer.equals(replicatedNetMe))
                try {
                    ReplicatedNetMessage message = new ReplicatedNetMessage(currentPeer, replicatedNetMe, resourceNetCommand);
                    sendMessage(message);
                } catch (Exception e) {
                    Log.d("MESSAGE", "exception broadcast");
                }

        }
    }

    /////////////////////////////////////////ADD PEER
    //TODO
    public void invite(SMSPeer toInvite) {
        if (!replicatedNetDictionary.containsPeerValue(toInvite)) {
            try {
                Invitation invitation = new Invitation();
                //save token
                invitedTokenList.put(invitation.getCode(), toInvite);
                Log.d("COMMUNICATION", "invite " + invitedTokenList.size());
                //send invitation
                ReplicatedNetMessage message = new ReplicatedNetMessage(null, replicatedNetMe, invitation.getStringInvitation());
                sendMessage(message, toInvite);
            } catch (Exception e) {
            }
        } else
            Log.d("COMMUNICATION", "peer already in");
    }

    private void handShake(Invitation invitation, ReplicatedNetPeer sourcePeer, SMSPeer
            toHandshake) {

        if (invitation.isAccepted()) {
            //end invitation process
            Log.d("COMMUNICATION", "end invitation" + invitedTokenList.size());
            if (invitedTokenList.containsKey(invitation.getCode())) {
                SMSPeer toAddSmsPeer = invitedTokenList.remove(invitation.getCode());
                replicatedNetDictionary.putPeerIfAbsent(sourcePeer, toHandshake);
                Log.d("COMMUNICATION", "" + replicatedNetDictionary.numberOfPeers());

                updateNewPeer(sourcePeer);
            } else
                Log.d("COMMUNICATION", "not contains token");
        } else {
            //reply invitation
            Log.d("COMMUNICATION", "reply invitation");
            invitation.accept();
            try {
                ReplicatedNetMessage message = new ReplicatedNetMessage(null, replicatedNetMe, invitation.getStringInvitation());
                sendMessage(message, toHandshake);
            } catch (Exception e) {

                Log.d("MESSAGE", "exception handshake");
            }
        }

    }

    private void updateNewPeer(ReplicatedNetPeer netPeer) {
        Log.d("COMMUNICATION", "in update NEW Peer");
        Iterator<Map.Entry<ReplicatedNetPeer, SMSPeer>> peersIterator = replicatedNetDictionary.getPeersIteratorAscending();
        Iterator<Map.Entry<K, V>> resourcesIterator = replicatedNetDictionary.getResourcesIterator();

        //send all peers to new peer
        while (peersIterator.hasNext()) {
            Log.d("COMMUNICATION", "sending peers");
            Map.Entry<ReplicatedNetPeer, SMSPeer> entry = peersIterator.next();
            ReplicatedNetPeer currentNetPeer = entry.getKey();
            SMSPeer currentSmsPeer = entry.getValue();

            try {
                ReplicatedNetMessage message = new ReplicatedNetMessage(
                        netPeer,
                        replicatedNetMe,
                        replicatedNetDictionary.getAddPeerNetCommand(currentNetPeer, currentSmsPeer)
                );
                Log.d("COMMUNICATION", "sending length@ " + message.getSDU().length());
                sendMessage(message);

            } catch (Exception e) {

                Log.d("COMMUNICATION", "exception update NEW peer");
            }

        }

        //send all resources to new peer
        while (resourcesIterator.hasNext()) {
            Log.d("COMMUNICATION", "sending resources");
            Map.Entry<K, V> entry = resourcesIterator.next();
            K currentKey = entry.getKey();
            V currentValue = entry.getValue();

            try {
                ReplicatedNetMessage message = new ReplicatedNetMessage(
                        netPeer,
                        replicatedNetMe,
                        replicatedNetDictionary.getAddResourceCommand(currentKey, currentValue)
                );
                sendMessage(message);

            } catch (Exception e) {

                Log.d("COMMUNICATION", "exception update NEW resource");
            }

        }

    }
    //////////////////////////////////////PING

    /**
     * Sends a ping to a sms peer
     *
     * @param message The message to send
     */
    public boolean sendMessage(ReplicatedNetMessage message, SMSPeer destinationSmsPeer) {

        SMSMessage smsMessage = null;

        try {
            smsMessage = new SMSMessage(destinationSmsPeer, smsMe, message.getSDU());
        } catch (InvalidPeerException | InvalidMessageException e) {
            Log.d("MESSAGE", "exception rep send2");
            return false;
        }

        return SMSManager.getDefault().sendMessage(smsMessage);
    }

    ///////////////////////////////////////SMS_LISTENER

    /**
     * Called by NotificatonEraser whenever a message is received.
     *
     * @param message The message received
     */
    @Override
    public void onMessageReceived(SMSMessage message) {
        ReplicatedNetMessage netMessage = null;
        try {
            netMessage = ReplicatedNetMessage.buildFromSDU(message.getData());
        } catch (InvalidPeerException | InvalidMessageException e) {
            Log.d("MESSAGE", "exception retrieving peer REP");
        }


        if (netMessage != null) {

            Log.d("COMMUNICATION", netMessage.getData().substring(0, 1));

            if (netMessage.getData().charAt(0) == ReplicatedResourceNetCommand.controlCode) {
                Log.d("COMMUNICATION", "received resource");
                //retrieve command
                String command = netMessage.getData();
                //execute command
                replicatedNetDictionary.getResourceCommandExecutor().execute(replicatedNetDictionary, command);

            } else if (netMessage.getData().charAt(0) == ReplicatedPeerNetCommand.controlCode) {
                Log.d("COMMUNICATION", "received peer");
                //retrieve command
                String command = netMessage.getData();
                //execute command
                replicatedNetDictionary.getPeerCommandExecutor().execute(replicatedNetDictionary, command);
            } else if (netMessage.getData().charAt(0) == Invitation.controlCode) {
                Log.d("COMMUNICATION", "received invitation");
                Invitation invitation = Invitation.getFromString(netMessage.getData());
                //reply or end an invitation process
                handShake(invitation, netMessage.getSourcePeer(), message.getSourcePeer());
            } else
                Log.d("MESSAGE", "not a know protocol for rep man");
        } else
            Log.d("MESSAGE", "not able to parse repMess to smsMess ");
    }

}
