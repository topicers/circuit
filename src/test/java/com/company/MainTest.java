package com.company;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 * Created by І on 09.06.2016.
 */
public class MainTest {
    @Test
    public void isInputFileExists() throws IOException
    {
        assertTrue(Files.exists(Paths.get(Main.IN_FILENAME)));
    }

    @Test
    public void getWire()
    {
        assertEquals(Main.getWire("aaa"), Main.getWire("aaa"));
    }

    @Test
    public void parseLineValue()
    {
        Main.parseLine("0 -> c");
        assertEquals(0, (int)Main.wires.get("c").getSignal());
    }

    @Test
    public void parseLineNot()
    {
        Main.parseLine("NOT lk -> ll");
        assertNotNull(Main.wires.get("lk"));
        assertNotNull(Main.wires.get("ll"));
        assertEquals(Main.notResultProducer, Main.wires.get("ll").getSource().getResultProducer());
    }

    @Test
    public void parseLineOr()
    {
        Main.parseLine("a1 OR b1 -> c1");
        assertNotNull(Main.wires.get("a1"));
        assertNotNull(Main.wires.get("b1"));
        assertNotNull(Main.wires.get("c1"));
        assertEquals(Main.orResultProducer, Main.wires.get("c1").getSource().getResultProducer());
    }

    @Test
    public void parseLineAnd()
    {
        Main.parseLine("a2 AND b2 -> c2");
        assertNotNull(Main.wires.get("a2"));
        assertNotNull(Main.wires.get("b2"));
        assertNotNull(Main.wires.get("c2"));
        assertEquals(Main.andResultProducer, Main.wires.get("c2").getSource().getResultProducer());
    }

    @Test
    public void parseLineRshift()
    {
        Main.parseLine("a3 RSHIFT b3 -> c3");
        assertNotNull(Main.wires.get("a3"));
        assertNotNull(Main.wires.get("b3"));
        assertNotNull(Main.wires.get("c3"));
        assertEquals(Main.rShiftResultProducer, Main.wires.get("c3").getSource().getResultProducer());
    }

    @Test
    public void parseLineLshift()
    {
        Main.parseLine("a4 LSHIFT b4 -> c4");
        assertNotNull(Main.wires.get("a4"));
        assertNotNull(Main.wires.get("b4"));
        assertNotNull(Main.wires.get("c4"));
        assertEquals(Main.lShiftResultProducer, Main.wires.get("c4").getSource().getResultProducer());
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseLineInvalidUnaryOperator()
    {
        Main.parseLine("NOO a5 -> ai");
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseLineInvalidBinaryOperator()
    {
        Main.parseLine("a6 ANDD b6 -> c6");
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseLineTooManyArguments()
    {
        Main.parseLine("af AND ah gz -> ai");
    }

    @Test
    public void andResultProducer()
    {
        Wire input1 = new Wire("");
        input1.setSignal((char)123);

        Wire input2 = new Wire("");
        input2.setSignal((char)456);

        assertEquals((long)Main.andResultProducer.apply(input1, input2), 72);
    }

    @Test
    public void orResultProducer()
    {
        Wire input1 = new Wire("");
        input1.setSignal((char)123);

        Wire input2 = new Wire("");
        input2.setSignal((char)456);

        assertEquals((long)Main.orResultProducer.apply(input1, input2), 507);
    }

    @Test
    public void lShiftResultProducer()
    {
        Wire input1 = new Wire("");
        input1.setSignal((char)123);

        Wire input2 = new Wire("");
        input2.setSignal((char)2);

        assertEquals((long)Main.lShiftResultProducer.apply(input1, input2), 492);
    }

    @Test
    public void rShiftResultProducer()
    {
        Wire input1 = new Wire("");
        input1.setSignal((char)456);

        Wire input2 = new Wire("");
        input2.setSignal((char)2);

        assertEquals((long)Main.rShiftResultProducer.apply(input1, input2), 114);
    }

    @Test
    public void notResultProducer()
    {
        Wire input1 = new Wire("");
        input1.setSignal((char)123);

        assertEquals((long)Main.notResultProducer.apply(input1, null), 65412);
    }

    @Test(expected = IllegalStateException.class)
    public void wireTest()
    {
        Wire wire = new Wire("");
        wire.setSignal((char)0);
        wire.setSignal((char)0);
    }
}