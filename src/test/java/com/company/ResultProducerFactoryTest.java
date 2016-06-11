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
}