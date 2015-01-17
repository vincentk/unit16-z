package com.unit16.z.indexed;

import java.util.Iterator;

import com.google.common.collect.ImmutableSet;

final class Empty<M>
extends DSL<M>
{
	@Override
	public M get(int i) { throw new ArrayIndexOutOfBoundsException(i); }

	@Override
	public int size() { return 0; }

	@Override
	public Iterator<M> iterator() { return ImmutableSet.<M>of().iterator(); }
}