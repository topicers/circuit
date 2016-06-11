package com.company;

/**
 * This class represents wire. Wire has signal, otherwise it has source in order to get signal in future
 */
final class Wire {
    private final String id;
    private Gate source;
    private Character signal;

    Wire(String id) {
        this.id = id;
    }

    Gate getSource() {
        return source;
    }

    //wire can have only one source
    void setSource(Gate source)
    {
        if (this.source != null) throw new IllegalStateException("Attempt to overwrite source for gate id<"+id+">");
        this.source = source;
    }

    Character getSignal() {
        return signal;
    }

    //signal for wire can be set only one time
    void setSignal(Character signal) {
        if (hasSignal()) throw new IllegalStateException("Attempt to overwrite signal for gate id<"+id+">");
        if (Main.IS_DEBUG) System.out.println("Set signal <"+((int)signal)+"> on wire <"+id+">");

        this.signal = signal;
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
