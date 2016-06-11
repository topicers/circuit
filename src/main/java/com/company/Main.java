package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Main class
 */
public final class Main {
    static final String IN_FILENAME = "input.txt";
    static final boolean IS_DEBUG = true;

    public static void main(String[] args) throws IOException {
        final String inputFileName;
        final String resultWireId;
        //get predefined filename and resulting wire from command line args or use default values
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
        final Wire resultWire = WireHolder.getWire(resultWireId);
        while(!resultWire.hasSignal())
        {
            if (Main.IS_DEBUG) System.out.println("-- Iteration #: "+(++counter));
            WireHolder.processAllWires();
        }

        System.out.println(("Result is: " + (int)resultWire.getSignal()));
    }

    /*
        1) Parse one line from input file
        2) Fill WireHolder.wiresToProcess and WireHolder.constWires maps
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
                outputWire = WireHolder.getWire(operands[1]);
                outputWire.setSource(new Gate(WireHolder.getWire(operands[0]), WireHolder.getZeroWire(), outputWire, ResultProducerFactory.getSameResultProducer()));
                break;
            //parse line like: NOT 2 -> ll, NOT lk -> ll
            case 3:
                if (!operands[0].equals("NOT")) throw exception;
                outputWire = WireHolder.getWire(operands[2]);
                outputWire.setSource(new Gate(WireHolder.getWire(operands[1]), WireHolder.getZeroWire(), outputWire, ResultProducerFactory.getNotResultProducer()));
                break;
            //parse line like: af OP ah -> ai, 2 OP ah -> ai, af OP 2 -> ai, 2 OP 2 -> ai
            case 4:
                outputWire = WireHolder.getWire(operands[3]);
                outputWire.setSource(new Gate(
                        WireHolder.getWire(operands[0]),
                        WireHolder.getWire(operands[2]),
                        outputWire,
                        ResultProducerFactory.get2OperandsProducer(operands[1], exception)
                ));
                break;
            default:
                throw exception;
        }
    }
}
