package com.unit16.z.indexed;

import java.util.Iterator;

import com.google.common.collect.Iterators;

final class Const<M>
extends DSL<M>
{
	private final M _m;
	Const(M m)
	{
		_m = m;
	}
	
	@Override
	public M get(int i) {
		return _m;
	}

	@Override
	public int size() {
		return Integer.MAX_VALUE;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterator<M> iterator() { return Iterators.cycle(_m); }
}