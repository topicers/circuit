package com.company;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Tests for Parser
 */
public final class ParserTest {
    @Before
    public void initialize() {
        //different tests can have wires with the same ids. So avoid conflicts between them.
        WireHolder.clearWiresToProcess();
    }

    @Test
    public void parseLineValue()
    {
        Parser.parseLine("0 -> a");
        Parser.getWire("a").process();
        assertThat((int)Parser.getWire("a").getSignal().get(), is(0));
    }

    @Test
    public void parseLineWireToWire()
    {
        Parser.parseLine("a -> b");
        assertThat(WireHolder.getWireToProcessTest("a"), notNullValue());
        assertThat(WireHolder.getWireToProcessTest("b"), notNullValue());
        assertThat(Parser.getWire("b").getSource().get().getResultProducer(), is(ResultProducerFactory.getSameResultProducer()));
    }

    @Test
    public void parseLineNotId()
    {
        Parser.parseLine("NOT a -> b");
        assertThat(WireHolder.getWireToProcessTest("a"), notNullValue());
        assertThat(WireHolder.getWireToProcessTest("b"), notNullValue());
        assertThat(Parser.getWire("b").getSource().get().getResultProducer(), is(ResultProducerFactory.getNotResultProducer()));
    }

    @Test
    public void parseLineNotConst()
    {
        Parser.parseLine("NOT 123 -> a");
        assertThat(WireHolder.getWireToProcessTest("a"), notNullValue());
        Parser.getWire("a").process();
        assertThat((int)Parser.getWire("a").getSignal().get(), is(65412));
    }

    @Test
    public void parseLineOr()
    {
        Parser.parseLine("a OR b -> c");
        assertThat(WireHolder.getWireToProcessTest("a"), notNullValue());
        assertThat(WireHolder.getWireToProcessTest("b"), notNullValue());
        assertThat(WireHolder.getWireToProcessTest("c"), notNullValue());
        assertThat(
                Parser.getWire("c").getSource().get().getResultProducer(),
                is(ResultProducerFactory.get2OperandsProducer("OR", new IllegalArgumentException("")))
        );
    }

    @Test
    public void parseLineOrConst()
    {
        Parser.parseLine("123 OR 456 -> c");
        assertThat(WireHolder.getWireToProcessTest("c"), notNullValue());
        Parser.getWire("c").process();
        assertThat((int)Parser.getWire("c").getSignal().get(), is(507));
    }

    @Test
    public void parseLineAnd()
    {
        Parser.parseLine("a AND b -> c");
        assertThat(WireHolder.getWireToProcessTest("a"), notNullValue());
        assertThat(WireHolder.getWireToProcessTest("b"), notNullValue());
        assertThat(WireHolder.getWireToProcessTest("c"), notNullValue());
        assertThat(
                Parser.getWire("c").getSource().get().getResultProducer(),
                is(ResultProducerFactory.get2OperandsProducer("AND", new IllegalArgumentException("")))
        );
    }

    @Test
    public void parseLineAndConst()
    {
        Parser.parseLine("123 AND 456 -> c");
        assertThat(WireHolder.getWireToProcessTest("c"), notNullValue());
        Parser.getWire("c").process();
        assertThat((int)Parser.getWire("c").getSignal().get(), is(72));
    }

    @Test
    public void parseLineRshift()
    {
        Parser.parseLine("a RSHIFT b -> c");
        assertThat(WireHolder.getWireToProcessTest("a"), notNullValue());
        assertThat(WireHolder.getWireToProcessTest("b"), notNullValue());
        assertThat(WireHolder.getWireToProcessTest("c"), notNullValue());
        assertThat(
                Parser.getWire("c").getSource().get().getResultProducer(),
                is(ResultProducerFactory.get2OperandsProducer("RSHIFT", new IllegalArgumentException("")))
        );
    }

    @Test
    public void parseLineRshiftConst()
    {
        Parser.parseLine("456 RSHIFT 2 -> c");
        assertThat(WireHolder.getWireToProcessTest("c"), notNullValue());
        Parser.getWire("c").process();
        assertThat((int)Parser.getWire("c").getSignal().get(), is(114));
    }

    @Test
    public void parseLineLshift()
    {
        Parser.parseLine("a LSHIFT b -> c");
        assertThat(WireHolder.getWireToProcessTest("a"), notNullValue());
        assertThat(WireHolder.getWireToProcessTest("b"), notNullValue());
        assertThat(WireHolder.getWireToProcessTest("c"), notNullValue());
        assertThat(
                Parser.getWire("c").getSource().get().getResultProducer(),
                is(ResultProducerFactory.get2OperandsProducer("LSHIFT", new IllegalArgumentException("")))
        );
    }

    @Test
    public void parseLineLshiftConst()
    {
        Parser.parseLine("123 LSHIFT 2 -> c");
        assertThat(WireHolder.getWireToProcessTest("c"), notNullValue());
        Parser.getWire("c").process();
        assertThat((int)Parser.getWire("c").getSignal().get(), is(492));
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

    @Test(expected = IllegalArgumentException.class)
    public void getWireInvalid1()
    {
        Parser.getWire("2a");
    }

    @Test(expected = IllegalArgumentException.class)
    public void getWireInvalid2()
    {
        Parser.getWire("aA");
    }
}