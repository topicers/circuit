package com.company;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for Parser
 */
public class ParserTest {
    @Before
    public void initialize() {
        //different tests can have wires with the same ids. So avoid conflicts between them.
        WireHolder.clearWiresToProcess();
    }

    @Test
    public void parseLineValue()
    {
        Parser.parseLine("0 -> a");
        WireHolder.getWire("a").process();
        assertEquals(0, (int)WireHolder.getWire("a").getSignal());
    }

    @Test
    public void parseLineWireToWire()
    {
        Parser.parseLine("a -> b");
        assertNotNull(WireHolder.getWire("a"));
        assertNotNull(WireHolder.getWire("b"));
        assertEquals(ResultProducerFactory.getSameResultProducer(), WireHolder.getWire("b").getSource().getResultProducer());
    }

    @Test
    public void parseLineNotId()
    {
        Parser.parseLine("NOT a -> b");
        assertNotNull(WireHolder.getWire("a"));
        assertNotNull(WireHolder.getWire("b"));
        assertEquals(ResultProducerFactory.getNotResultProducer(), WireHolder.getWire("b").getSource().getResultProducer());
    }

    @Test
    public void parseLineNotConst()
    {
        Parser.parseLine("NOT 123 -> a");
        assertNotNull(WireHolder.getWire("a"));
        WireHolder.getWire("a").process();
        assertEquals(65412, (int)WireHolder.getWire("a").getSignal());
    }

    @Test
    public void parseLineOr()
    {
        Parser.parseLine("a OR b -> c");
        assertNotNull(WireHolder.getWire("a"));
        assertNotNull(WireHolder.getWire("b"));
        assertNotNull(WireHolder.getWire("c"));
        assertEquals(ResultProducerFactory.get2OperandsProducer("OR", new IllegalArgumentException("")), WireHolder.getWire("c").getSource().getResultProducer());
    }

    @Test
    public void parseLineOrConst()
    {
        Parser.parseLine("123 OR 456 -> c");
        assertNotNull(WireHolder.getWire("c"));
        WireHolder.getWire("c").process();
        assertEquals(507, (int)WireHolder.getWire("c").getSignal());
    }

    @Test
    public void parseLineAnd()
    {
        Parser.parseLine("a AND b -> c");
        assertNotNull(WireHolder.getWire("a"));
        assertNotNull(WireHolder.getWire("b"));
        assertNotNull(WireHolder.getWire("c"));
        assertEquals(ResultProducerFactory.get2OperandsProducer("AND", new IllegalArgumentException("")), WireHolder.getWire("c").getSource().getResultProducer());
    }

    @Test
    public void parseLineAndConst()
    {
        Parser.parseLine("123 AND 456 -> c");
        assertNotNull(WireHolder.getWire("c"));
        WireHolder.getWire("c").process();
        assertEquals(72, (int)WireHolder.getWire("c").getSignal());
    }

    @Test
    public void parseLineRshift()
    {
        Parser.parseLine("a RSHIFT b -> c");
        assertNotNull(WireHolder.getWire("a"));
        assertNotNull(WireHolder.getWire("b"));
        assertNotNull(WireHolder.getWire("c"));
        assertEquals(ResultProducerFactory.get2OperandsProducer("RSHIFT", new IllegalArgumentException("")), WireHolder.getWire("c").getSource().getResultProducer());
    }

    @Test
    public void parseLineRshiftConst()
    {
        Parser.parseLine("456 RSHIFT 2 -> c");
        assertNotNull(WireHolder.getWire("c"));
        WireHolder.getWire("c").process();
        assertEquals(114, (int)WireHolder.getWire("c").getSignal());
    }

    @Test
    public void parseLineLshift()
    {
        Parser.parseLine("a LSHIFT b -> c");
        assertNotNull(WireHolder.getWire("a"));
        assertNotNull(WireHolder.getWire("b"));
        assertNotNull(WireHolder.getWire("c"));
        assertEquals(ResultProducerFactory.get2OperandsProducer("LSHIFT", new IllegalArgumentException("")), WireHolder.getWire("c").getSource().getResultProducer());
    }

    @Test
    public void parseLineLshiftConst()
    {
        Parser.parseLine("123 LSHIFT 2 -> c");
        assertNotNull(WireHolder.getWire("c"));
        WireHolder.getWire("c").process();
        assertEquals(492, (int)WireHolder.getWire("c").getSignal());
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseLineInvalidUnaryOperator()
    {
        Parser.parseLine("NOO a -> b");
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseLineInvalidBinaryOperator()
    {
        Parser.parseLine("a ANDD b -> c");
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseLineTooManyArguments()
    {
        Parser.parseLine("a AND b c -> d");
    }
}