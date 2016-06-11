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
        Main.wiresToProcess.clear();
    }

    @Test
    public void processSampleFile() throws IOException
    {
        Main.main(new String[]{});
        assertEquals(40149, (int)Main.wiresToProcess.get("a").getSignal());
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
        assertEquals(Main.getWire("a"), Main.getWire("a"));
    }

    @Test
    public void getWireConst()
    {
        final Wire wire = Main.getWire("2");

        assertEquals(wire, Main.getWire("2"));
        assertEquals(2, (int)wire.getSignal());
    }

    @Test
    public void parseLineValue()
    {
        Main.parseLine("0 -> a");
        Main.wiresToProcess.get("a").process();
        assertEquals(0, (int)Main.wiresToProcess.get("a").getSignal());
    }

    @Test
    public void parseLineWireToWire()
    {
        Main.parseLine("a -> b");
        assertNotNull(Main.wiresToProcess.get("a"));
        assertNotNull(Main.wiresToProcess.get("b"));
        assertEquals(ResultProducerFactory.getSameResultProducer(), Main.wiresToProcess.get("b").getSource().getResultProducer());
    }

    @Test
    public void parseLineNotId()
    {
        Main.parseLine("NOT a -> b");
        assertNotNull(Main.wiresToProcess.get("a"));
        assertNotNull(Main.wiresToProcess.get("b"));
        assertEquals(ResultProducerFactory.getNotResultProducer(), Main.wiresToProcess.get("b").getSource().getResultProducer());
    }

    @Test
    public void parseLineNotConst()
    {
        Main.parseLine("NOT 123 -> a");
        assertNotNull(Main.wiresToProcess.get("a"));
        Main.wiresToProcess.get("a").process();
        assertEquals(65412, (int)Main.wiresToProcess.get("a").getSignal());
    }

    @Test
    public void parseLineOr()
    {
        Main.parseLine("a OR b -> c");
        assertNotNull(Main.wiresToProcess.get("a"));
        assertNotNull(Main.wiresToProcess.get("b"));
        assertNotNull(Main.wiresToProcess.get("c"));
        assertEquals(ResultProducerFactory.get2OperandsProducer("OR", new IllegalArgumentException("")), Main.wiresToProcess.get("c").getSource().getResultProducer());
    }

    @Test
    public void parseLineOrConst()
    {
        Main.parseLine("123 OR 456 -> c");
        assertNotNull(Main.wiresToProcess.get("c"));
        Main.wiresToProcess.get("c").process();
        assertEquals(507, (int)Main.wiresToProcess.get("c").getSignal());
    }

    @Test
    public void parseLineAnd()
    {
        Main.parseLine("a AND b -> c");
        assertNotNull(Main.wiresToProcess.get("a"));
        assertNotNull(Main.wiresToProcess.get("b"));
        assertNotNull(Main.wiresToProcess.get("c"));
        assertEquals(ResultProducerFactory.get2OperandsProducer("AND", new IllegalArgumentException("")), Main.wiresToProcess.get("c").getSource().getResultProducer());
    }

    @Test
    public void parseLineAndConst()
    {
        Main.parseLine("123 AND 456 -> c");
        assertNotNull(Main.wiresToProcess.get("c"));
        Main.wiresToProcess.get("c").process();
        assertEquals(72, (int)Main.wiresToProcess.get("c").getSignal());
    }

    @Test
    public void parseLineRshift()
    {
        Main.parseLine("a RSHIFT b -> c");
        assertNotNull(Main.wiresToProcess.get("a"));
        assertNotNull(Main.wiresToProcess.get("b"));
        assertNotNull(Main.wiresToProcess.get("c"));
        assertEquals(ResultProducerFactory.get2OperandsProducer("RSHIFT", new IllegalArgumentException("")), Main.wiresToProcess.get("c").getSource().getResultProducer());
    }

    @Test
    public void parseLineRshiftConst()
    {
        Main.parseLine("456 RSHIFT 2 -> c");
        assertNotNull(Main.wiresToProcess.get("c"));
        Main.wiresToProcess.get("c").process();
        assertEquals(114, (int)Main.wiresToProcess.get("c").getSignal());
    }

    @Test
    public void parseLineLshift()
    {
        Main.parseLine("a LSHIFT b -> c");
        assertNotNull(Main.wiresToProcess.get("a"));
        assertNotNull(Main.wiresToProcess.get("b"));
        assertNotNull(Main.wiresToProcess.get("c"));
        assertEquals(ResultProducerFactory.get2OperandsProducer("LSHIFT", new IllegalArgumentException("")), Main.wiresToProcess.get("c").getSource().getResultProducer());
    }

    @Test
    public void parseLineLshiftConst()
    {
        Main.parseLine("123 LSHIFT 2 -> c");
        assertNotNull(Main.wiresToProcess.get("c"));
        Main.wiresToProcess.get("c").process();
        assertEquals(492, (int)Main.wiresToProcess.get("c").getSignal());
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