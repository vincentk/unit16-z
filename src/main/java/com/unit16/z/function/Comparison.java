package com.unit16.z.function;

import java.util.Comparator;

import com.google.common.base.Equivalence;

/**
 * An {@link Equivalence} relationship based on a {@link Comparator}.
 * 
 * @param <T>
 */
public final class Comparison<T> extends Equivalence<T>
{
	private final Comparator<? super T> c;
	public Comparison(Comparator<? super T> c1) { c = c1; }

	@Override
	protected boolean doEquivalent(T a, T b) {
		return c.compare(a, b) == 0;
	}

	@Override
	protected int doHash(T t) {
		return t.hashCode();
	}
}
