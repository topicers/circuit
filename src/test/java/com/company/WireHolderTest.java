package com.company;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Test for WireHolder
 */
public class WireHolderTest {
    @Test
    public void getWireId()
    {
        assertThat(WireHolder.getWire("a"), is(WireHolder.getWire("a")));
    }

    @Test
    public void getWireConst()
    {
        final Wire wire = WireHolder.getWire("2");

        assertThat(wire, is(WireHolder.getWire("2")));
        assertThat((int)wire.getSignal(), is(2));
    }
}