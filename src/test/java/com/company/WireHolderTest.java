package com.company;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test for WireHolder
 */
public class WireHolderTest {
    @Test
    public void getWireId()
    {
        assertEquals(WireHolder.getWire("a"), WireHolder.getWire("a"));
    }

    @Test
    public void getWireConst()
    {
        final Wire wire = WireHolder.getWire("2");

        assertEquals(wire, WireHolder.getWire("2"));
        assertEquals(2, (int)wire.getSignal());
    }
}