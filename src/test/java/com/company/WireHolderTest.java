package com.company;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Test for WireHolder
 */
public class WireHolderTest {

    @Before
    public void initialize() {
        //different tests can have wires with the same ids. So avoid conflicts between them.
        WireHolder.clearWiresToProcess();
    }

    @Test
    public void getConstWire() throws Exception {
        Wire wire = WireHolder.getConstWire("2", 2);
        assertThat(WireHolder.getConstWire("2", 2), is(wire));
        assertThat((int)wire.getSignal(), is(2));
    }

    @Test
    public void getWireToProcess() throws Exception {
        Wire wire = WireHolder.getWireToProcess("a");
        assertThat(WireHolder.getWireToProcess("a"), is(wire));
        assertThat(wire.hasSignal(), is(false));
    }
}