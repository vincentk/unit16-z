package com.unit16.z.time;

/**
 * An event which will trigger repeatedly with a specified interval.
 */
public final class IntervalEvent implements MaybeEmpty {

    private final long interval_;
    private final long end_;
    private final Runnable what_;
    
    private long next_;
    
    public IntervalEvent(GMTMicros start, long interval, GMTMicros end, Runnable what)
    {
        assert interval > 0;
        interval_ = interval;
        next_ = start.gmtMicros() + interval_;
        end_ = end.gmtMicros();
        what_ = what;
    }

    @Override
    public long gmtMicros() { return next_; }

    @Override
    public boolean hasNext() { return next_ <= end_; }

    @Override
    public void advance() { next_ += interval_; what_.run(); }
}
