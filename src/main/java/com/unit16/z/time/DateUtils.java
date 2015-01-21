package com.unit16.z.time;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.TimeZone;
import java.util.function.Predicate;

import com.unit16.z.Sequence;

public class DateUtils {

    public static final Predicate<LocalDate> WEEKDAY = day -> day.getDayOfWeek().getValue() < 6;

    /**
     * @param start of sequence (inclusive)
     * @param end of sequence (inclusive)
     * @return all days between start and end -- empty sequence if start is after end.
     */
    public static Sequence<LocalDate> period(final LocalDate start, final LocalDate end)
    {
        return new Sequence<LocalDate>(){

            private LocalDate today = start;

            @Override
            public LocalDate next() { 

                final LocalDate r = today;
                today = r.plusDays(1);
                return r;
            }

            @Override
            public boolean hasNext() {
                return !today.isAfter(end);
            }};
    }

    public static LocalDateTime fromMicros(GMTMicros micros)
    {
        final Instant ofEpochMilli = Instant.ofEpochMilli(micros.gmtMicros() / 1000);
        return LocalDateTime.ofInstant(ofEpochMilli, UTC_ID);
    }

    public static GMTMicros.Const UTCMicros(LocalDateTime date)
    {
        return new GMTMicros.Const(date.atZone(UTC_ID).toEpochSecond() * 1000 * 1000);
    }

    public static GMTMicros.Const midnightUTCMicros(LocalDate date)
    {
        return new GMTMicros.Const(date.atStartOfDay(UTC_ID).toEpochSecond() * 1000 * 1000);
    }

    public static final TimeZone UTC = TimeZone.getTimeZone("UTC");
    public static final ZoneId UTC_ID = UTC.toZoneId();
}
