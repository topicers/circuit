package com.company;

import java.util.function.BiFunction;

/**
 * Factory for ResultProducer
 */
final class ResultProducerFactory {
    private static final BiFunction<Wire, Wire, Character> andResultProducer = (input1, input2) -> (char)(input1.getSignal() & input2.getSignal());
    private static final BiFunction<Wire, Wire, Character> orResultProducer = (input1, input2) -> (char)(input1.getSignal() | input2.getSignal());
    private static final BiFunction<Wire, Wire, Character> lShiftResultProducer = (input1, input2) -> (char)(input1.getSignal() << input2.getSignal());
    private static final BiFunction<Wire, Wire, Character> rShiftResultProducer = (input1, input2) -> (char)(input1.getSignal() >> input2.getSignal());
    private static final BiFunction<Wire, Wire, Character> notResultProducer = (input1, input2) -> (char)(~input1.getSignal());
    private static final BiFunction<Wire, Wire, Character> sameResultProducer = (input1, input2) -> input1.getSignal();

    static BiFunction<Wire, Wire, Character> get2OperandsProducer(final String type, IllegalArgumentException exception)
    {
        switch(type)
        {
            case "OR":
                return orResultProducer;
            case "AND":
                return andResultProducer;
            case "LSHIFT":
                return lShiftResultProducer;
            case "RSHIFT":
                return rShiftResultProducer;
            default:
                throw exception;
        }
    }

    static BiFunction<Wire, Wire, Character> getNotResultProducer()
    {
        return notResultProducer;
    }

    static BiFunction<Wire, Wire, Character> getSameResultProducer()
    {
        return sameResultProducer;
    }
}
