package com.unit16.z;

import java.util.ArrayList;

public abstract class Pool<D> {

	private final ArrayList<D> cache_ = new ArrayList<>(1);

	public final void recycle(D val)
	{
		assert !cache_.contains(val);
		cache_.add(val);
	}
	
	public final D draw()
	{
		if (cache_.isEmpty())
		{
			return create();
		}
		else
		{
			return cache_.remove(cache_.size() - 1);
		}
	}
	
	protected abstract D create();
}
