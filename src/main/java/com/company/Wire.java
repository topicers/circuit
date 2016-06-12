package com.company;

import java.util.Optional;

/**
 * This class represents wire. Wire has signal, otherwise it has source in order to get signal in future
 */
final class Wire {
    private final String id;
    private Optional<Gate> source = Optional.empty();
    private Optional<Character> signal = Optional.empty();

    Wire(String id) {
        this.id = id;
    }

    Optional<Gate> getSource() {
        return source;
    }

    //wire can have only one source
    void setSource(Gate source)
    {
        if (this.source.isPresent()) throw new IllegalStateException("Attempt to overwrite source for gate id<"+id+">");
        this.source = Optional.of(source);
    }

    Optional<Character> getSignal() {
        return signal;
    }

    //signal for wire can be set only one time
    void setSignal(Character signal) {
        if (hasSignal()) throw new IllegalStateException("Attempt to overwrite signal for gate id<"+id+">");
        if (Main.IS_DEBUG) System.out.println("Set signal <"+(int)signal+"> on wire <"+id+">");

        this.signal = Optional.of(signal);
        this.source = Optional.empty(); //mark Gate for GC
    }

    void process()
    {
        source.ifPresent(Gate::produceOutput);
    }

    boolean hasSignal() {
        return signal.isPresent();
    }

    @Override
    public String toString() {
        return "Wire<"+id+">";
    }
}
