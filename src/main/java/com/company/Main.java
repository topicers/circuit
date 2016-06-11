package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Stream;

/**
 * Created by І on 09.06.2016.
 */
public class Main {
    static final String IN_FILENAME = "input.txt";
    static final BiFunction<Wire, Wire, Character> andResultProducer = (input1, input2) -> (char)(input1.getSignal() & input2.getSignal());
    static final BiFunction<Wire, Wire, Character> orResultProducer = (input1, input2) -> (char)(input1.getSignal() | input2.getSignal());
    static final BiFunction<Wire, Wire, Character> lShiftResultProducer = (input1, input2) -> (char)(input1.getSignal() << input2.getSignal());
    static final BiFunction<Wire, Wire, Character> rShiftResultProducer = (input1, input2) -> (char)(input1.getSignal() >> input2.getSignal());
    static final BiFunction<Wire, Wire, Character> notResultProducer = (input1, input2) -> (char)(~input1.getSignal());
    static final BiFunction<Wire, Wire, Character> sameResultProducer = (input1, input2) -> input1.getSignal();
    static final Map<String, Wire> wires = new HashMap<>();
    private static final Wire nullWire;

    static {
        nullWire = new Wire("");
        nullWire.setSignal((char)0);
    }

    public static void main(String[] args) throws IOException {
        try (Stream<String> lines = Files.lines(Paths.get(IN_FILENAME)))
        {
            lines.forEachOrdered(Main::parseLine);
        }

        int counter = 0;
        while(!wires.get("a").hasSignal())
        {
            System.out.println("-- Iteration #: "+(++counter));
            wires.forEach((id, wire) -> wire.process());
        }

        System.out.println(((int)wires.get("a").getSignal()));
    }

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
                outputWire.setSource(new Gate(getWire(operands[0]), nullWire, outputWire, sameResultProducer));
                break;
            //parse line like: NOT 2 -> ll, NOT lk -> ll
            case 3:
                if (!operands[0].equals("NOT")) throw exception;
                outputWire = getWire(operands[2]);
                outputWire.setSource(new Gate(getWire(operands[1]), nullWire, outputWire, notResultProducer));
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

    static Wire getWire(String id)
    {
        try
        {
            Character constantSignal = (char)Integer.parseInt(id);
            Wire wire = new Wire(id);
            wire.setSignal(constantSignal);
            return wire;
        }
        catch(NumberFormatException e)
        {
            return wires.computeIfAbsent(id, Wire::new);
        }
    }
}
