package com.company;

/**
 * Created by І on 10.06.2016.
 */
public class Wire {
    private final String id;
    private Gate source;
    private Character signal;

    public Wire(String id) {
        this.id = id;
    }

    public Gate getSource() {
        return source;
    }

    public void setSource(Gate source)
    {
        this.source = source;
    }

    public Character getSignal() {
        return signal;
    }

    public void setSignal(Character signal) {
        if (hasSignal()) throw new IllegalStateException("Attempt to overwrite value for gate id<"+id+">");
        this.signal = signal;
        this.source = null; //mark source for GC
    }

    public void process()
    {
        if (source != null) source.produceOutput();
    }

    public boolean hasSignal() {
        return (signal != null);
    }
}
