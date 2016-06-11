package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * For parsing input file
 */
final class Parser {
    static void parseFile(String fileName) throws IOException
    {
        //parse all lines
        try (Stream<String> lines = Files.lines(Paths.get(fileName)))
        {
            lines.forEach(Parser::parseLine);
        }
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
