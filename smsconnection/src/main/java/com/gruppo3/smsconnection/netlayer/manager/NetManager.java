package com.gruppo3.smsconnection.netlayer.manager;

import com.gruppo3.smsconnection.connection.CommunicationHandler;
import com.gruppo3.smsconnection.connection.exception.InvalidDataException;
import com.gruppo3.smsconnection.connection.exception.InvalidPeerException;
import com.gruppo3.smsconnection.connection.exception.UnknownPeerException;
import com.gruppo3.smsconnection.connection.listener.ReceivedMessageListener;
import com.gruppo3.smsconnection.netlayer.NetAdapter;
import com.gruppo3.smsconnection.netlayer.NetDataUnit;
import com.gruppo3.smsconnection.netlayer.NetPeer;
import com.gruppo3.smsconnection.smsdatalink.SMSDataUnit;
import com.gruppo3.smsconnection.smsdatalink.SMSPeer;
import com.gruppo3.smsconnection.smsdatalink.manager.SMSManager;

import java.util.HashMap;

/**
 * @author Mattia Fanan
 */
public class NetManager extends CommunicationHandler<NetDataUnit> implements ReceivedMessageListener<SMSDataUnit> {

    HashMap<NetPeer, SMSPeer> routingTable;
    private static NetManager defInstance;
    ReceivedMessageListener<NetDataUnit> listener;

    /**
     * singletone
     */
    private NetManager()
    {
        SMSManager.getDefault().addReceiveListener(this);
        routingTable=retriveRoutingTable();
        listener = null;
    }

    /**
     *
     * @return the defoult istance of NetManager
     */
    public NetManager getDefault()
    {
        if(defInstance==null)
            defInstance=new NetManager();
        return defInstance;
    }

    /**
     * forward data unit to data-link layer
     * @param netDataUnit
     * @return true if forwarded
     */
    @Override
    public boolean sendDataUnit (NetDataUnit netDataUnit) {
        if(netDataUnit==null || !netDataUnit.isValid())
            return false;

        NetAdapter adpt;

        try{
            adpt = new NetAdapter(netDataUnit,routingTable);
        }
        catch (UnknownPeerException e){return false;}
        catch (InvalidPeerException e){return false;}
        catch (InvalidDataException e){return false;}
        if(adpt==null)return false;

        SMSManager.getDefault().sendDataUnit(adpt.getSMSDataUnit());
        return true;
    }

    /**
     * set a listener
     * @param listener The listener to wake up when a message is received
     */
    @Override
    public void addReceiveListener(ReceivedMessageListener<NetDataUnit> listener) {
        if(listener!=null)
            this.listener=listener;
    }

    /**
     * unset the listener
     */
    @Override
    public void removeReceiveListener() {
        listener=null;
    }

    /**
     * future
     * @return
     */
    //TODO
    private HashMap<NetPeer, SMSPeer> retriveRoutingTable() {
        return new HashMap<>();
    }

    /**
     * future
     */
    //TODO
    private void saveRoutingTable(){

    }

    /**
     * futur forward message to upper layer
     * manage and forward data units
     * @param message The message received
     */
    //TODO
    @Override
    public void onMessageReceived(SMSDataUnit dataUnit) {
       // listener.onMessageReceived(new NetDataUnit(new NetPeer()));
    }
}
