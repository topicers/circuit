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

    static Wire getConstWire(String id, int idVal)
    {
        return constWires.computeIfAbsent(id, wid -> {
            Wire wire = new Wire(wid);
            wire.setSignal((char)idVal);
            return wire;
        });
    }

    static Wire getWireToProcess(String id)
    {
        return wiresToProcess.computeIfAbsent(id, Wire::new);
    }

    //Used only for tests
    static Wire getWireToProcessTest(String id)
    {
        return wiresToProcess.get(id);
    }
}
