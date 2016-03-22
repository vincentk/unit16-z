package com.unit16.z.time;

/**
 * An event which will trigger repeatedly with a specified interval.
 */
public final class IntervalEvent implements MaybeEmpty {

    private final long interval_;
    private final long end_;
    private final Runnable what_;
    
    private long next_;
    
    public IntervalEvent(UTCMicros start, long interval, UTCMicros end, Runnable what)
    {
        assert interval > 0;
        interval_ = interval;
        next_ = start.utcMicros() + interval_;
        end_ = end.utcMicros();
        what_ = what;
    }

    @Override
    public long utcMicros() { return next_; }

    @Override
    public boolean hasNext() { return next_ <= end_; }

    @Override
    public void advance() { next_ += interval_; what_.run(); }
}
