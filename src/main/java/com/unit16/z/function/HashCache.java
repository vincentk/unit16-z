package com.unit16.z.function;

import java.util.HashMap;

import com.google.common.base.Function;

public final class HashCache<X, Y> 
implements Function<X, Y>
{
	private final HashMap<X, Y> cache_ = new HashMap<>();
	
	/**
	 * Must be referentially transparent:
	 */
	private final Function<X, Y> f_;
	
	public HashCache(Function<X, Y> f) { f_ = f; }

	@Override
	public Y apply(X from) {
		if (!cache_.containsKey(from))
		{
			cache_.put(from, f_.apply(from));
		}
		return cache_.get(from);
	}
}
