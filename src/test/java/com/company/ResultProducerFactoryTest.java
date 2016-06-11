package com.company;

import org.junit.Test;

import java.util.function.BiFunction;

import static org.junit.Assert.*;

/**
 * Test for ResultProducerFactory
 */
public class ResultProducerFactoryTest {
    @Test
    public void get2OperandsProducerDistinct() {
        IllegalArgumentException exception = new IllegalArgumentException("");
        BiFunction<Wire, Wire, Character> orResultProducer = ResultProducerFactory.get2OperandsProducer("OR", exception);
        BiFunction<Wire, Wire, Character> andResultProducer = ResultProducerFactory.get2OperandsProducer("AND", exception);
        BiFunction<Wire, Wire, Character> rShiftResultProducer = ResultProducerFactory.get2OperandsProducer("RSHIFT", exception);
        BiFunction<Wire, Wire, Character> lShiftResultProducer = ResultProducerFactory.get2OperandsProducer("LSHIFT", exception);

        assertNotEquals(orResultProducer, andResultProducer);
        assertNotEquals(orResultProducer, rShiftResultProducer);
        assertNotEquals(orResultProducer, lShiftResultProducer);
        assertNotEquals(rShiftResultProducer, andResultProducer);
        assertNotEquals(lShiftResultProducer, andResultProducer);
        assertNotEquals(lShiftResultProducer, rShiftResultProducer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void get2OperandsProducerInvalidArgument()
    {
        ResultProducerFactory.get2OperandsProducer("ORR", new IllegalArgumentException(""));
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