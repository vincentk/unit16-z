package com.unit16.z.time;

import java.util.function.Supplier;

public interface TimedI<P> extends Supplier<P>, GMTMicros {

	abstract static class Formatted<Q>
	extends GMTMicros.NicelyFormatted
	implements
	TimedI<Q>
	{
		@Override public String toString()
		{
			return super.toString() + ":" + get().toString();
		}
	}

	public static class I<Q> extends Formatted<Q>
	{
		private final long micros_;
		private final Q p_;

		public I(Q p, long ts)
		{
			micros_ = ts;
			p_ = p;
		}

		@Override
		public final long gmtMicros() { return micros_; }

		@Override
		public final Q get() { return p_; }
	}

	public static class M<Q> extends Formatted<Q>
	{
		public long micros_;
		public Q p_;

		@Override
		public final long gmtMicros() { return micros_; }

		@Override
		public final Q get() { return p_; }
	}
}
