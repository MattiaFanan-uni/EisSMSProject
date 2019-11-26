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
    public abstract D getStamp();

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

        if (sourcePeer == null){
            //peers can't be both null
            if(destinationPeer==null)return false;

            if(!canHaveNullPeer)return false;

            this.sourcePeer=sourcePeer;
            return true;
        }

        this.sourcePeer = sourcePeer;
        return true;
    }

    /**
     * Set destination peer in the Header
     * @param destinationPeer to set in the Header
     * @return true if destinationPeer is valid, false if destinationPeer is not valid
     */
    public boolean setDestinationPeer(P destinationPeer){
        if (destinationPeer == null){
            //peers can't be both null
            if(sourcePeer==null)return false;

            if(!canHaveNullPeer)return false;

            this.destinationPeer=destinationPeer;
            return true;
        }

        this.destinationPeer = destinationPeer;
        return true;
    }

}
