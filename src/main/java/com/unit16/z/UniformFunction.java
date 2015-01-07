package com.unit16.z;

import com.google.common.base.Function;

public interface UniformFunction<T> 
extends Function<T, T>
{
	// marker type
	
	public static final class UpCast<D> implements UniformFunction<D>
	{
		private final Function<D, D> f_;
		public UpCast(Function<D, D> f) { f_ = f; }
		@Override public D apply(D from) { return f_.apply(from); }
	}
}
