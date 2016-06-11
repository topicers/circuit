package com.company;

/**
 * Created by І on 10.06.2016.
 */
class Wire {
    private final String id;
    private Gate source;
    private Character signal;

    Wire(String id) {
        this.id = id;
    }

    Gate getSource() {
        return source;
    }

    void setSource(Gate source)
    {
        this.source = source;
    }

    Character getSignal() {
        return signal;
    }

    void setSignal(Character signal) {
        if (hasSignal()) throw new IllegalStateException("Attempt to overwrite value for gate id<"+id+">");
        this.signal = signal;
        System.out.println("Set signal <"+((int)signal)+"> on wire <"+id+">");
        this.source = null; //mark source for GC
    }

    void process()
    {
        if (source != null) source.produceOutput();
    }

    boolean hasSignal() {
        return (signal != null);
    }
}
