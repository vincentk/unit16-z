package com.unit16.z.function;

import static org.junit.Assert.*;

import java.util.function.Consumer;

import org.junit.Test;

import com.google.common.base.Functions;

public class TestFluent {


    @Test
    public void testNothingPasses()
    {
        // doesn't let anything pass
        final Consumer<Boolean> nothing = FluentConsumer.<Boolean>on(b -> assertFalse(b)).filter(t -> false);
        nothing.accept(true);
        nothing.accept(false);
    }

    @Test
    public void testAllPasses()
    {
        // doesn't let anything pass
        final Consumer<Boolean> nothing = FluentConsumer.<Boolean>on(b -> assertTrue(b)).filter(t -> true);
        // happy
        nothing.accept(true);
        try
        {
            nothing.accept(false);
            throw new RuntimeException("Should have asserted.");
        }
        catch (AssertionError e)
        {
            // cool
        }
    }
    
    @Test
    public void testMap()
    {
        final Consumer<Integer> int2String = FluentConsumer.on(s -> assertEquals("1", s)).map(num -> num.toString());
        int2String.accept(1);
    }
}
