package com.company;

import org.junit.Test;

/**
 * Created by І on 10.06.2016.
 */
public class WireTest {
    @Test(expected = IllegalStateException.class)
    public void setSignal()
    {
        Wire wire = new Wire("");
        wire.setSignal((char)0);
        wire.setSignal((char)0);
    }
}
