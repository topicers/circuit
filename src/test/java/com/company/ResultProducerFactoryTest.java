package com.company;

import org.junit.Test;

import java.util.function.BiFunction;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

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

        assertThat(orResultProducer, not(anyOf(is(andResultProducer), is(rShiftResultProducer), is(lShiftResultProducer))));
        assertThat(andResultProducer, not(anyOf(is(orResultProducer), is(rShiftResultProducer), is(lShiftResultProducer))));
        assertThat(rShiftResultProducer, not(anyOf(is(orResultProducer), is(andResultProducer), is(lShiftResultProducer))));
        assertThat(lShiftResultProducer, not(anyOf(is(orResultProducer), is(andResultProducer), is(rShiftResultProducer))));
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

        assertThat(
                (int)ResultProducerFactory.get2OperandsProducer("AND", new IllegalArgumentException("")).apply(input1, input2),
                is(72)
        );
    }

    @Test
    public void orResultProducer()
    {
        Wire input1 = new Wire("");
        input1.setSignal((char)123);

        Wire input2 = new Wire("");
        input2.setSignal((char)456);

        assertThat(
                (int)ResultProducerFactory.get2OperandsProducer("OR", new IllegalArgumentException("")).apply(input1, input2),
                is(507)
        );
    }

    @Test
    public void lShiftResultProducer()
    {
        Wire input1 = new Wire("");
        input1.setSignal((char)123);

        Wire input2 = new Wire("");
        input2.setSignal((char)2);

        assertThat(
                (int)ResultProducerFactory.get2OperandsProducer("LSHIFT", new IllegalArgumentException("")).apply(input1, input2),
                is(492)
        );
    }

    @Test
    public void rShiftResultProducer()
    {
        Wire input1 = new Wire("");
        input1.setSignal((char)456);

        Wire input2 = new Wire("");
        input2.setSignal((char)2);

        assertThat(
                (int)ResultProducerFactory.get2OperandsProducer("RSHIFT", new IllegalArgumentException("")).apply(input1, input2),
                is(114)
        );
    }

    @Test
    public void notResultProducer()
    {
        Wire input1 = new Wire("");
        input1.setSignal((char)123);

        assertThat(
                (int)ResultProducerFactory.getNotResultProducer().apply(input1, null),
                is(65412)
        );
    }
}