package com.gruppo3.smsconnection.replicatednet.manager;

import org.junit.Assert;
import org.junit.Test;

public class InvitationTest {

    @Test
    public void setUp() {
        Invitation inv = new Invitation();

        //invitation starts unaccepted
        Assert.assertFalse(inv.isAccepted());
    }

    @Test
    public void setAccept() {
        Invitation inv = new Invitation();
        inv.accept();
        Assert.assertTrue(inv.isAccepted());
    }

    @Test
    public void toStringAndBackAccepted() {
        Invitation inv = new Invitation();
        inv.accept();

        Invitation retrievedInv = Invitation.getFromString(inv.getStringInvitation());

        Assert.assertNotNull(retrievedInv);

        Assert.assertEquals(retrievedInv.getCode(),inv.getCode());
        Assert.assertEquals(retrievedInv.isAccepted(),inv.isAccepted());
    }

    @Test
    public void toStringAndBackNonAccepted() {
        Invitation inv = new Invitation();

        Invitation retrievedInv = Invitation.getFromString(inv.getStringInvitation());

        Assert.assertNotNull(retrievedInv);

        Assert.assertEquals(retrievedInv.getCode(),inv.getCode());
        Assert.assertEquals(retrievedInv.isAccepted(),inv.isAccepted());

    }

}
