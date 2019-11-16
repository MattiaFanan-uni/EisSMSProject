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
     * Set destination peer in the Header
     * @param destinationPeer to set in the Header
     * @return true if destinationPeer is valid, false if destinationPeer is not valid
     */
    public boolean setDestinationPeer(P destinationPeer){
        // Can set null peer only if Header.canHaveNullPeer = true
        if (destinationPeer == null && !canHaveNullPeer)
            return false;

        if (destinationPeer != null) {
            if (destinationPeer.isValid()) {
                this.destinationPeer = destinationPeer;
                return true;
            }
        }

        return false;
    }

    /**
     * Check if Header is valid
     * @return true if Header is valid, false if Header is not valid
     */
    public abstract boolean isValid();
}
