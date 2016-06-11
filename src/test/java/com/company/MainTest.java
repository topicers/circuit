package com.company;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 * Tests for Main class.
 */
public final class MainTest {
    @Test
    public void isInputFileExists() throws IOException
    {
        assertTrue(Files.exists(Paths.get(Main.IN_FILENAME)));
    }

    @Test
    public void processSampleFile() throws IOException
    {
        Main.main(new String[]{});
        assertEquals(40149, (int)WireHolder.getWire("a").getSignal());
    }
}