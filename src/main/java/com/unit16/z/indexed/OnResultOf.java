package com.unit16.z.indexed;

import java.util.Iterator;

import com.google.common.base.Function;
import com.google.common.collect.Iterators;

final class OnResultOf<A, B>
extends DSL<B>
{
	private final DSL<A> _a;
	private final Function<? super A, ? extends B> _f;
	
	OnResultOf(DSL<A> a, Function<? super A, ? extends B> f)
	{
		_a = a;
		_f = f;
	}

	@Override
	public B get(int i) {
		return _f.apply(_a.get(i));
	}

	@Override
	public int size() { return _a.size(); }

	@Override
	public Iterator<B> iterator() { return Iterators.transform(_a.iterator(), _f); }
}
