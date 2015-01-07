package com.unit16.z;

import java.util.Iterator;

public final class TrivialIterable<B>
implements Iterable<B>
{
	private final Iterator<B> t;
	private TrivialIterable(Iterator<B> t_) { t = t_; }

	@Override public Iterator<B> iterator() { return t; }

	public static <C> TrivialIterable<C> build(Iterator<C> i)
	{
		return new TrivialIterable<>(i);
	}
}
