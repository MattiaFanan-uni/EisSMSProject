package com.gruppo3.smsconnection.replicatednet.manager;

import org.junit.Assert;
import org.junit.Test;

public class InvitationTest {

    @Test
    public void setUp() {
        Invitation inv = new Invitation();

        if (inv.isAccepted())
            Assert.fail("shouldn't starts as accepted");
    }

    @Test
    public void setAccept() {
        Invitation inv = new Invitation();
        inv.accept();
        if (!inv.isAccepted())
            Assert.fail("should be accepted");
    }

    @Test
    public void toStringAndBackAccepted() {
        Invitation inv = new Invitation();
        inv.accept();

        Invitation retrievedInv = Invitation.getFromString(inv.getStringInvitation());

        if (retrievedInv == null)
            Assert.fail("shouldn't return null");

        if (retrievedInv.getCode() != inv.getCode())
            Assert.fail("should be the same code");

        if (retrievedInv.isAccepted() != inv.isAccepted())
            Assert.fail("should be the same accepted value");

    }

    @Test
    public void toStringAndBackNonAccepted() {
        Invitation inv = new Invitation();

        Invitation retrievedInv = Invitation.getFromString(inv.getStringInvitation());

        if (retrievedInv == null)
            Assert.fail("shouldn't return null");

        if (retrievedInv.getCode() != inv.getCode())
            Assert.fail("should be the same code");

        if (retrievedInv.isAccepted() != inv.isAccepted())
            Assert.fail("should be the same accepted value");

    }

}
