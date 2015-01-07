package com.unit16.z.time;

import com.google.common.base.Function;
import com.google.common.base.Supplier;

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

	static final class Functor<P, Q>
	implements Function<TimedI<? extends P>, TimedI.I<Q>>
	{
		private final Function<P, Q> f;
		private Functor(Function<P, Q> f_) { f = f_; }

		@Override
		public I<Q> apply(TimedI<? extends P> input) {

			final long m = input.gmtMicros();
			final Q q = f.apply(input.get());

			return new TimedI.I<>(q, m);
		}

		public static <P, Q> Function<TimedI<? extends P>, TimedI.I<Q>> from(Function<P, Q> f)
		{
			return new Functor<>(f);
		}
	}
}
