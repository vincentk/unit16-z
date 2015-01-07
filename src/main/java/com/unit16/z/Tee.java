package com.unit16.z;

import com.google.common.base.Function;


public interface Tee<A, B, C> {

	C apply(A a, B b);

	/**
	 * Composition rules for {@link Tee}, as a presumably hip "fluid builder".
	 * 
	 * @param <A>
	 * @param <B>
	 * @param <C>
	 */
	static abstract class DSL<A, B, C>
	implements Tee<A, B, C>
	{
		static <X, Y, Z> Tee<X, Y, Z> from(final Tee<X, Y, Z> f)
		{
			return new DSL<X, Y, Z>() {
				@Override public Z apply(X a, Y b) { 
					return f.apply(a, b); }
			};
		}

		public final <S> DSL<S, B, C> preA(final Function<S, A> f) {
			final DSL<A, B, C> w = this;
			return new DSL<S, B, C>() {
				@Override public C apply(S a, B b) { 
					return w.apply(f.apply(a), b); }
			};
		}
		
		public final <R> DSL<A, R, C> preB(final Function<R, B> f) {
			final DSL<A, B, C> w = this;
			return new DSL<A, R, C>() {
				@Override public C apply(A a, R b) { 
					return w.apply(a, f.apply(b)); }
			};
		}

		public final <Q> DSL<A, B, Q> post(final Function<C, Q> f) {
			final DSL<A, B, C> w = this;
			return new DSL<A, B, Q>() {
				@Override public Q apply(A a, B b) { 
					return f.apply(w.apply(a, b)); }
			};
		}
	}
}
