package com.company;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 * Tests for Main class.
 */
public final class MainTest {
    @Before
    public void initialize() {
        //different tests can have wires with the same ids, and processSampleFile produces many wires. So avoid conflicts between them.
        WireHolder.clearWiresToProcess();
    }

    @Test
    public void processSampleFile() throws IOException
    {
        Main.main(new String[]{});
        assertEquals(40149, (int)WireHolder.getWire("a").getSignal());
    }

    @Test
    public void isInputFileExists() throws IOException
    {
        assertTrue(Files.exists(Paths.get(Main.IN_FILENAME)));
    }

    //in case of
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

    @Test
    public void parseLineValue()
    {
        Main.parseLine("0 -> a");
        WireHolder.getWire("a").process();
        assertEquals(0, (int)WireHolder.getWire("a").getSignal());
    }

    @Test
    public void parseLineWireToWire()
    {
        Main.parseLine("a -> b");
        assertNotNull(WireHolder.getWire("a"));
        assertNotNull(WireHolder.getWire("b"));
        assertEquals(ResultProducerFactory.getSameResultProducer(), WireHolder.getWire("b").getSource().getResultProducer());
    }

    @Test
    public void parseLineNotId()
    {
        Main.parseLine("NOT a -> b");
        assertNotNull(WireHolder.getWire("a"));
        assertNotNull(WireHolder.getWire("b"));
        assertEquals(ResultProducerFactory.getNotResultProducer(), WireHolder.getWire("b").getSource().getResultProducer());
    }

    @Test
    public void parseLineNotConst()
    {
        Main.parseLine("NOT 123 -> a");
        assertNotNull(WireHolder.getWire("a"));
        WireHolder.getWire("a").process();
        assertEquals(65412, (int)WireHolder.getWire("a").getSignal());
    }

    @Test
    public void parseLineOr()
    {
        Main.parseLine("a OR b -> c");
        assertNotNull(WireHolder.getWire("a"));
        assertNotNull(WireHolder.getWire("b"));
        assertNotNull(WireHolder.getWire("c"));
        assertEquals(ResultProducerFactory.get2OperandsProducer("OR", new IllegalArgumentException("")), WireHolder.getWire("c").getSource().getResultProducer());
    }

    @Test
    public void parseLineOrConst()
    {
        Main.parseLine("123 OR 456 -> c");
        assertNotNull(WireHolder.getWire("c"));
        WireHolder.getWire("c").process();
        assertEquals(507, (int)WireHolder.getWire("c").getSignal());
    }

    @Test
    public void parseLineAnd()
    {
        Main.parseLine("a AND b -> c");
        assertNotNull(WireHolder.getWire("a"));
        assertNotNull(WireHolder.getWire("b"));
        assertNotNull(WireHolder.getWire("c"));
        assertEquals(ResultProducerFactory.get2OperandsProducer("AND", new IllegalArgumentException("")), WireHolder.getWire("c").getSource().getResultProducer());
    }

    @Test
    public void parseLineAndConst()
    {
        Main.parseLine("123 AND 456 -> c");
        assertNotNull(WireHolder.getWire("c"));
        WireHolder.getWire("c").process();
        assertEquals(72, (int)WireHolder.getWire("c").getSignal());
    }

    @Test
    public void parseLineRshift()
    {
        Main.parseLine("a RSHIFT b -> c");
        assertNotNull(WireHolder.getWire("a"));
        assertNotNull(WireHolder.getWire("b"));
        assertNotNull(WireHolder.getWire("c"));
        assertEquals(ResultProducerFactory.get2OperandsProducer("RSHIFT", new IllegalArgumentException("")), WireHolder.getWire("c").getSource().getResultProducer());
    }

    @Test
    public void parseLineRshiftConst()
    {
        Main.parseLine("456 RSHIFT 2 -> c");
        assertNotNull(WireHolder.getWire("c"));
        WireHolder.getWire("c").process();
        assertEquals(114, (int)WireHolder.getWire("c").getSignal());
    }

    @Test
    public void parseLineLshift()
    {
        Main.parseLine("a LSHIFT b -> c");
        assertNotNull(WireHolder.getWire("a"));
        assertNotNull(WireHolder.getWire("b"));
        assertNotNull(WireHolder.getWire("c"));
        assertEquals(ResultProducerFactory.get2OperandsProducer("LSHIFT", new IllegalArgumentException("")), WireHolder.getWire("c").getSource().getResultProducer());
    }

    @Test
    public void parseLineLshiftConst()
    {
        Main.parseLine("123 LSHIFT 2 -> c");
        assertNotNull(WireHolder.getWire("c"));
        WireHolder.getWire("c").process();
        assertEquals(492, (int)WireHolder.getWire("c").getSignal());
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseLineInvalidUnaryOperator()
    {
        Main.parseLine("NOO a -> b");
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseLineInvalidBinaryOperator()
    {
        Main.parseLine("a ANDD b -> c");
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseLineTooManyArguments()
    {
        Main.parseLine("a AND b c -> d");
    }

    @Test
    public void andResultProducer()
    {
        Wire input1 = new Wire("");
        input1.setSignal((char)123);

        Wire input2 = new Wire("");
        input2.setSignal((char)456);

        assertEquals((long)ResultProducerFactory.get2OperandsProducer("AND", new IllegalArgumentException("")).apply(input1, input2), 72);
    }

    @Test
    public void orResultProducer()
    {
        Wire input1 = new Wire("");
        input1.setSignal((char)123);

        Wire input2 = new Wire("");
        input2.setSignal((char)456);

        assertEquals((long)ResultProducerFactory.get2OperandsProducer("OR", new IllegalArgumentException("")).apply(input1, input2), 507);
    }

    @Test
    public void lShiftResultProducer()
    {
        Wire input1 = new Wire("");
        input1.setSignal((char)123);

        Wire input2 = new Wire("");
        input2.setSignal((char)2);

        assertEquals((long)ResultProducerFactory.get2OperandsProducer("LSHIFT", new IllegalArgumentException("")).apply(input1, input2), 492);
    }

    @Test
    public void rShiftResultProducer()
    {
        Wire input1 = new Wire("");
        input1.setSignal((char)456);

        Wire input2 = new Wire("");
        input2.setSignal((char)2);

        assertEquals((long)ResultProducerFactory.get2OperandsProducer("RSHIFT", new IllegalArgumentException("")).apply(input1, input2), 114);
    }

    @Test
    public void notResultProducer()
    {
        Wire input1 = new Wire("");
        input1.setSignal((char)123);

        assertEquals((long)ResultProducerFactory.getNotResultProducer().apply(input1, null), 65412);
    }
}