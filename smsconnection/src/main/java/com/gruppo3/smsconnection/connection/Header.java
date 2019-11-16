package com.gruppo3.smsconnection.connection;

/**
 * Header abstract class
 * @author Mattia Fanan
 *
 * @param <P> Peer data-type
 * @param <D> Payload data data-type
 */
public abstract class Header<P extends Peer, D> {
    protected P sourcePeer;
    protected P destinationPeer;
    protected boolean canHaveNullPeer;

    /**
     * @return Header's data
     */
    public abstract D getHeader();

    /**
     * @return source peer in the Header
     */
    public P getSourcePeer() {
        return sourcePeer;
    }

    /**
     * @return destination peer in the Header
     */
    public P getDestinationPeer() {
        return destinationPeer;
    }

    /**
     * Set source peer in the Header
     * @param sourcePeer to set in the Header
     * @return true if sourcePeer is valid, false if sourcePeer is not valid
     */
    public boolean setSourcePeer(P sourcePeer) {
        // Can set null peer only if Header.canHaveNullPeer = true
        if (sourcePeer == null && !canHaveNullPeer)
            return false;

        if (sourcePeer != null) {
            if (sourcePeer.isValid()) {
                this.sourcePeer = sourcePeer;
                return true;
            }
        }

        return false;
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
