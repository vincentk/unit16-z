package com.unit16.z.time;

import java.util.Comparator;

public interface UTCMicros {

    public long utcMicros();

    public static final class Const extends NicelyFormatted
    {
        private final long ts_;
        public Const(long t) { ts_ = t; }

        @Override
        public long utcMicros() { return ts_; }
    }

    static abstract class NicelyFormatted implements UTCMicros
    {
        @Override
        public String toString()
        {
            final long micros = utcMicros() % 1000;
            return DateUtils.fromMicros(this).toString() + "." + micros;
        }
    }

    public static final Comparator<UTCMicros> ORDERING = (UTCMicros o1, UTCMicros o2) -> {
        final long dl = o1.utcMicros() - o2.utcMicros();
        return Long.signum(dl);
    };
}
