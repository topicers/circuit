package com.company;

import java.util.function.BiFunction;

/**
 * Created by І on 10.06.2016.
 */
public class Gate {
    private final Wire input1;
    private final Wire input2;
    private final Wire output;

    public BiFunction<Wire, Wire, Character> getResultProducer() {
        return resultProducer;
    }

    private final BiFunction<Wire, Wire, Character> resultProducer;

    public Gate(Wire input1, Wire input2, Wire output, BiFunction<Wire, Wire, Character> resultProducer) {
        this.input1 = input1;
        this.input2 = input2;
        this.output = output;
        this.resultProducer = resultProducer;
    }

    public void produceOutput() {
        if (input1.hasSignal() && input2.hasSignal())
        {
            output.setSignal(resultProducer.apply(input1, input2));
        }
    }
}
