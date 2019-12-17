package com.gruppo3.smsconnection.replicatednet.manager;


import java.io.Serializable;
import java.util.Random;

/**
 * Data structure to send as invitation in {@link ReplicatedNetManager}
 *
 * @author Mattia Fanan
 */
public class Invitation implements Serializable {

    public static char controlCode = '@';
    private long code;
    private boolean accepted;

    public Invitation() {
        code = (new Random()).nextInt();
        accepted = false;
    }

    private Invitation(long code, boolean accepted) {
        this.code = code;
        this.accepted = accepted;
    }

    /**
     * Changes the invitation state to ACCEPTED
     */
    public void accept() {
        accepted = true;
    }

    /**
     * Checks the state of this invitation
     *
     * @return <code>true</code> if this invitation is accepted, <code>false</code>  otherwise
     */
    public boolean isAccepted() {
        return accepted;
    }

    /**
     * Gets the ID of this invitation
     *
     * @return the ID of this invitation
     */
    public long getCode() {
        return code;
    }

    /**
     * Gets the String representation of this invitation
     */
    public String getStringInvitation() {
        return controlCode + (accepted ? "S" : "N") + code;
    }

    /**
     * Retrieves an invitation from it's string representation
     *
     * @param stringRepresentation the string representation of the invitation
     * @return an Inviation if is possible to obtain a valid one from the string passed, <code>null</code> otherwise
     *
     */
    public static Invitation getFromString(String stringRepresentation) {
        try {
            if (stringRepresentation.charAt(0) != controlCode)
                return null;
            boolean accepted;
            //TODO boolean default value is false
            if (stringRepresentation.charAt(1) == 'S')
                accepted = true;
            else if (stringRepresentation.charAt(1) == 'N')
                accepted = false;
            else return null;

            return new Invitation(Long.parseLong(stringRepresentation.substring(2)), accepted);
        } catch (Exception e) {
            return null;
        }
    }
}