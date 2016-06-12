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
        Wire resultWire = Parser.getWire("a");
        Main.main(new String[]{});
        assertThat((int)resultWire.getSignal().get(), is(40149));
    }
}