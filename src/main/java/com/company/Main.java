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
    static final Map<String, Wire> wires = new HashMap<>();

    public static void main(String[] args) throws IOException {
        try (Stream<String> lines = Files.lines(Paths.get(IN_FILENAME)))
        {
            lines.forEachOrdered(Main::parseLine);
        }
    }

    static void parseLine(String line)
    {
        final String[] operands = line.split(" -> |\\s");
        final IllegalArgumentException exception = new IllegalArgumentException("Invalid line <"+line+"> is detected.");
        Wire outputWire;
        switch(operands.length)
        {
            //parse line like: 0 -> c
            case 2:
                outputWire = getWire(operands[1]);
                outputWire.setSignal((char)Integer.parseInt(operands[0]));
                break;
            //parse line like: NOT lk -> ll
            case 3:
                if (!operands[0].equals("NOT")) throw exception;
                outputWire = getWire(operands[2]);
                outputWire.setSource(new Gate(getWire(operands[1]), null, outputWire, notResultProducer));
                break;
            //parse line like: af OP ah -> ai
            case 4:
                outputWire = getWire(operands[3]);
                switch(operands[1])
                {
                    case "OR":
                        outputWire.setSource(new Gate(getWire(operands[0]), getWire(operands[2]), outputWire, orResultProducer));
                        break;
                    case "AND":
                        outputWire.setSource(new Gate(getWire(operands[0]), getWire(operands[2]), getWire(operands[3]), andResultProducer));
                        break;
                    case "LSHIFT":
                        outputWire.setSource(new Gate(getWire(operands[0]), getWire(operands[2]), getWire(operands[3]), lShiftResultProducer));
                        break;
                    case "RSHIFT":
                        outputWire.setSource(new Gate(getWire(operands[0]), getWire(operands[2]), getWire(operands[3]), rShiftResultProducer));
                        break;
                    default:
                        throw exception;
                }
                break;
            default:
                throw exception;
        }
    }

    static Wire getWire(String id)
    {
        return wires.computeIfAbsent(id, Wire::new);
    }
}
