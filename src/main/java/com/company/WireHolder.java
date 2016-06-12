package com.company;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Holds wires
 */
final class WireHolder {
    /*
        wiresToProcess contains Wires with no signal, and they need to be processed and searched, as Wire
        can provide input signal to many Gates.

        constWires contains Wires with signal, so they don't need to be processed, and they can be cached.
    */
    private static final Map<String, Wire> wiresToProcess = new HashMap<>();
    private static final Map<String, Wire> constWires = new HashMap<>();
    //used when we have no second operand
    private static final Wire ZERO_WIRE;

    static {
        ZERO_WIRE = new Wire("");
        ZERO_WIRE.setSignal((char)0);
    }

    static Wire getZeroWire()
    {
        return ZERO_WIRE;
    }

    static void obtainResults(Wire resultWire)
    {
        //calculate signals from wiresToProcess, until we get signal for resultWire
        int counter = 0;
        while(!resultWire.hasSignal())
        {
            if (Main.IS_DEBUG) System.out.println("-- Iteration #: "+(++counter));

            final Iterator<Wire> iter = wiresToProcess.values().iterator();
            while (iter.hasNext()) {
                Wire wire = iter.next();
                wire.process();
                //if wire has received signal -> exclude it from further processing
                if (wire.hasSignal()) {
                    iter.remove();
                }
            }
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
