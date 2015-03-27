package com.unit16.z.time;

import static org.junit.Assert.*;

import org.junit.Test;

import com.unit16.z.time.GMTMicros.Const;

@SuppressWarnings("static-method")
public final class TestIntervalEvent {

    @Test
    public void testSimple()
    {
        final IntervalEvent ie = new IntervalEvent(new Const(0), 1, new Const(10), () -> {});
        
        for (int i = 0; i < 10; i++)
        {
            assertTrue(ie.hasNext());
            assertEquals(i + 1, ie.gmtMicros());
            ie.advance();
        }
    }
}
