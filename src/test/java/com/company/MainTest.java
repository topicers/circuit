package com.company;

import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for Main class.
 */
public final class MainTest {
    @Test
    public void processSampleFile() throws IOException
    {
        Main.main(new String[]{});
        assertThat((int)Parser.getWire("a").getSignal(), is(40149));
    }
}