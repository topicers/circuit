package com.company;

import java.io.IOException;

/**
 * Main class
 */
final class Main {
    private static final String IN_FILENAME = "input.txt";
    static final boolean IS_DEBUG = false;

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

        Parser.parseFile(inputFileName);

        final Wire resultWire = WireHolder.getWireToProcess(resultWireId);
        WireHolder.obtainResults(resultWire);
        System.out.println(("Result is: " + (int)resultWire.getSignal().get()));
    }
}
