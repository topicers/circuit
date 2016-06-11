package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Stream;

/**
 * Main class
 */
public final class Main {
    static final String IN_FILENAME = "input.txt";
    static final BiFunction<Wire, Wire, Character> andResultProducer = (input1, input2) -> (char)(input1.getSignal() & input2.getSignal());
    static final BiFunction<Wire, Wire, Character> orResultProducer = (input1, input2) -> (char)(input1.getSignal() | input2.getSignal());
    static final BiFunction<Wire, Wire, Character> lShiftResultProducer = (input1, input2) -> (char)(input1.getSignal() << input2.getSignal());
    static final BiFunction<Wire, Wire, Character> rShiftResultProducer = (input1, input2) -> (char)(input1.getSignal() >> input2.getSignal());
    static final BiFunction<Wire, Wire, Character> notResultProducer = (input1, input2) -> (char)(~input1.getSignal());
    static final BiFunction<Wire, Wire, Character> sameResultProducer = (input1, input2) -> input1.getSignal();
    static final Map<String, Wire> wiresToProcess = new HashMap<>();
    private static final Map<String, Wire> constWires = new HashMap<>();
    //used when we have no second operand
    private static final Wire zeroWire;
    static final boolean IS_DEBUG = true;

    static {
        zeroWire = new Wire("");
        zeroWire.setSignal((char)0);
    }

    public static void main(String[] args) throws IOException {
        final String inputFileName;
        final String resultWireId;
        if (args.length == 2)
        {
            inputFileName = args[0];
            resultWireId = args[1];
        }
        else
        {
            inputFileName = IN_FILENAME;
            resultWireId = "a";
        }

        //parse all lines
        try (Stream<String> lines = Files.lines(Paths.get(inputFileName)))
        {
            lines.forEach(Main::parseLine);
        }

        //calculate signals from wiresToProcess, until we get signal for Wire "a"
        int counter = 0;
        final Wire resultWire = wiresToProcess.get(resultWireId);
        while(!resultWire.hasSignal())
        {
            if (Main.IS_DEBUG) System.out.println("-- Iteration #: "+(++counter));
            wiresToProcess.forEach((id, wire) -> wire.process());
        }

        System.out.println(((int)resultWire.getSignal()));
    }

    /*
        1) Parse one line from input file
        2) Fill wiresToProcess and constWires maps
        3) Create and set sources for wires

        Throw IllegalArgumentException in case of parsing error
     */
    static void parseLine(String line)
    {
        final String[] operands = line.split(" -> |\\s");
        final IllegalArgumentException exception = new IllegalArgumentException("Invalid line <"+line+"> is detected.");
        Wire outputWire;
        switch(operands.length)
        {
            case 2:
                //parse line like: 2 -> c, k -> c
                outputWire = getWire(operands[1]);
                outputWire.setSource(new Gate(getWire(operands[0]), zeroWire, outputWire, sameResultProducer));
                break;
            //parse line like: NOT 2 -> ll, NOT lk -> ll
            case 3:
                if (!operands[0].equals("NOT")) throw exception;
                outputWire = getWire(operands[2]);
                outputWire.setSource(new Gate(getWire(operands[1]), zeroWire, outputWire, notResultProducer));
                break;
            //parse line like: af OP ah -> ai, 2 OP ah -> ai, af OP 2 -> ai, 2 OP 2 -> ai
            case 4:
                BiFunction<Wire, Wire, Character> resultProducer;
                switch(operands[1])
                {
                    case "OR":
                        resultProducer = orResultProducer;
                        break;
                    case "AND":
                        resultProducer = andResultProducer;
                        break;
                    case "LSHIFT":
                        resultProducer = lShiftResultProducer;
                        break;
                    case "RSHIFT":
                        resultProducer = rShiftResultProducer;
                        break;
                    default:
                        throw exception;
                }
                outputWire = getWire(operands[3]);
                outputWire.setSource(new Gate(getWire(operands[0]), getWire(operands[2]), outputWire, resultProducer));
                break;
            default:
                throw exception;
        }
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
}
