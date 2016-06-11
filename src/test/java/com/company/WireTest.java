package com.company;

import org.junit.Test;

/**
 * Test for Wire class.
 */
public final class WireTest {
    @Test(expected = IllegalStateException.class)
    public void setSignal()
    {
        Wire wire = new Wire("");
        wire.setSignal((char)0);
        wire.setSignal((char)0);
    }

    @Test(expected = IllegalStateException.class)
    public void setSource()
    {
        Wire wire = new Wire("");
        wire.setSource(new Gate(null, null, null, null));
        wire.setSource(new Gate(null, null, null, null));
    }
}
