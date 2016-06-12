package com.company;

import java.util.HashMap;
import java.util.Map;

/**
 * Holds wires
 */
final class WireHolder {
    private static final Map<String, Wire> wiresToProcess = new HashMap<>();
    private static final Map<String, Wire> constWires = new HashMap<>();
    //used when we have no second operand
    private static final Wire zeroWire;

    static {
        zeroWire = new Wire("");
        zeroWire.setSignal((char)0);
    }

    static Wire getZeroWire()
    {
        return zeroWire;
    }

    static void obtainResults(Wire resultWire)
    {
        //calculate signals from wiresToProcess, until we get signal for Wire "a"
        int counter = 0;
        while(!resultWire.hasSignal())
        {
            if (Main.IS_DEBUG) System.out.println("-- Iteration #: "+(++counter));
            wiresToProcess.forEach((id, wire) -> wire.process());
        }
    }

    static void clearWiresToProcess()
    {
        wiresToProcess.clear();
    }

    /*
        wiresToProcess contains Wires with no signal, and they need to be processed and searched, as Wire
        can provide input signal to many Gates.

        constWires contains Wires with signal, so they don't need to be processed, and they can be cached.

        If id is numeric compute it from constWires, otherwise compute it from wiresToProcess
    */
    static Wire getWire(String id)
    {
        try
        {
            Character constantSignal = (char)Integer.parseInt(id);
            return constWires.computeIfAbsent(id, wid -> {
                Wire wire = new Wire(wid);
                wire.setSignal(constantSignal);
                return wire;
            });
        }
        catch(NumberFormatException e)
        {
            return wiresToProcess.computeIfAbsent(id, Wire::new);
        }
    }

    //Used only for tests
    static Wire getWireToProcess(String id)
    {
        return wiresToProcess.get(id);
    }
}
