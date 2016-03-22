package com.unit16.z.time;

/**
 * An event which will trigger exactly once at a specified point in time.
 */
public final class OneTimeEvent implements MaybeEmpty {

    private boolean ran = false;
    private final long when_;
    private final Runnable what_;
    
    public OneTimeEvent(UTCMicros when, Runnable what)
    {
        when_ = when.utcMicros();
        what_ = what;
    }

    @Override
    public long utcMicros() { return when_; }

    @Override
    public boolean hasNext() { return !ran; }

    @Override
    public void advance() { ran = true; what_.run(); }
}
