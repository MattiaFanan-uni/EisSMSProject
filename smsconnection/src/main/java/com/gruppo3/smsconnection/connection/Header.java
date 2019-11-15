package com.gruppo3.smsconnection.connection;

/**
 * @author Mattia Fanan
 * abstraction of DataUnit's header
 * @param <P> type of peer
 * @param <D> type of payload's data
 */
public abstract class Header<P extends Peer,D> {
    protected P destinationPeer;
    protected P sourcePeer;
    protected boolean canHaveNullPeer;

    /**
     * get header's data to add to the payload
     * @return header to add to the payload
     */
    public abstract D getToAddHeder();

    /**
     *@return P header's source peer
     */
    public P getSourcePeer(){ return sourcePeer; }

    /**
     *@return P header's destination peer
     */
    public P getDestinationPeer(){ return destinationPeer; }

    /**
     * set header's source peer if a valid one is passed
     * @param peer peer
     * @return true if the header's source peer is successfully updated
     */
    public boolean setSourcePeer(P peer){
        //if peer is null i can set it only if header can have null peers
        if(peer==null){
            if(canHaveNullPeer)
                this.sourcePeer=peer;
            return canHaveNullPeer;
        }

        if(!peer.isValid())
            return false;

        this.sourcePeer=peer;
        return true;
    }
    /**
     * set header's destination peer if a valid one is passed
     * @param peer  peer
     * @return true if the header's destination peer is successfully updated
     */
    public boolean setDestinationPeer(P peer){

        //if peer is null i can set it only if header can have null peers
        if(peer==null){
            if(canHaveNullPeer)
                this.destinationPeer=peer;
            return canHaveNullPeer;
        }

        if(peer==null)
            return canHaveNullPeer;
        if(!peer.isValid())
            return false;
        this.destinationPeer=peer;
        return true;
    }

    /**
     * methods that decides what is a valid header
     * @return true if is valid
     */
    public abstract boolean isValid();
}
