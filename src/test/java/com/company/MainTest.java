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
    public void processSampleFile() throws IOException
    {
        Main.main(null);
        assertEquals(40149, (int)Main.wires.get("a").getSignal());
    }

    @Test
    public void isInputFileExists() throws IOException
    {
        assertTrue(Files.exists(Paths.get(Main.IN_FILENAME)));
    }

    @Test
    public void getWireId()
    {
        assertEquals(Main.getWire("aaa"), Main.getWire("aaa"));
    }

    @Test
    public void getWireConst()
    {
        final Wire wire1 = Main.getWire("2");
        final Wire wire2 = Main.getWire("2");

        assertNotEquals(wire1, wire2);
        assertEquals(2, (int)wire1.getSignal());
        assertEquals(2, (int)wire2.getSignal());
    }

    @Test
    public void parseLineValue()
    {
        Main.parseLine("0 -> c32");
        Main.wires.get("c32").process();
        assertEquals(0, (int)Main.wires.get("c32").getSignal());
    }

    @Test
    public void parseLineWireToWire()
    {
        Main.parseLine("ld -> ls");
        assertNotNull(Main.wires.get("ld"));
        assertNotNull(Main.wires.get("ls"));
        assertEquals(Main.sameResultProducer, Main.wires.get("ls").getSource().getResultProducer());
    }

    @Test
    public void parseLineNotId()
    {
        Main.parseLine("NOT lk -> ll");
        assertNotNull(Main.wires.get("lk"));
        assertNotNull(Main.wires.get("ll"));
        assertEquals(Main.notResultProducer, Main.wires.get("ll").getSource().getResultProducer());
    }

    @Test
    public void parseLineNotConst()
    {
        Main.parseLine("NOT 123 -> a50");
        assertNotNull(Main.wires.get("a50"));
        Main.wires.get("a50").process();
        assertEquals(65412, (int)Main.wires.get("a50").getSignal());
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
    public void parseLineOrConst()
    {
        Main.parseLine("123 OR 456 -> c15");
        assertNotNull(Main.wires.get("c15"));
        Main.wires.get("c15").process();
        assertEquals(507, (int)Main.wires.get("c15").getSignal());
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
    public void parseLineAndConst()
    {
        Main.parseLine("123 AND 456 -> c16");
        assertNotNull(Main.wires.get("c16"));
        Main.wires.get("c16").process();
        assertEquals(72, (int)Main.wires.get("c16").getSignal());
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
    public void parseLineRshiftConst()
    {
        Main.parseLine("456 RSHIFT 2 -> c17");
        assertNotNull(Main.wires.get("c17"));
        Main.wires.get("c17").process();
        assertEquals(114, (int)Main.wires.get("c17").getSignal());
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

    @Test
    public void parseLineLshiftConst()
    {
        Main.parseLine("123 LSHIFT 2 -> c18");
        assertNotNull(Main.wires.get("c18"));
        Main.wires.get("c18").process();
        assertEquals(492, (int)Main.wires.get("c18").getSignal());
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
}