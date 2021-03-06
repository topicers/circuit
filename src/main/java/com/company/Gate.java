package com.company;

import java.util.function.BiFunction;

/**
 * This class represents gate, which has 2 input wires and 1 output wire. resultProducer defines how to calculate
 * output signal.
 */
final class Gate {
    private final Wire input1;
    private final Wire input2;
    private final Wire output;
    private final BiFunction<Character, Character, Character> resultProducer;

    Gate(Wire input1, Wire input2, Wire output, BiFunction<Character, Character, Character> resultProducer) {
        this.input1 = input1;
        this.input2 = input2;
        this.output = output;
        this.resultProducer = resultProducer;
    }

    BiFunction<Character, Character, Character> getResultProducer() {
        return resultProducer;
    }

    //we can calculate output signal only when we have all input signals
    void produceOutput() {
        if (input1.hasSignal() && input2.hasSignal())
        {
            output.setSignal(resultProducer.apply(input1.getSignal().get(), input2.getSignal().get()));
        }
    }
}
