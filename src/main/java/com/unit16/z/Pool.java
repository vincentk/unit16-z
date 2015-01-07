package com.unit16.z;

import java.util.ArrayList;


/**
 * @param <D> type of cached element
 */
public abstract class Pool<D> {

	private final ArrayList<D> cache_ = new ArrayList<>(1);

	/**
	 * @param val to be returned to the pool
	 */
	public final void recycle(D val)
	{
		assert !cache_.contains(val);
		cache_.add(val);
	}
	
	/**
	 * Draw an element from the pool, creating one if the pool is empty.
	 */
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
	
	/**
	 * Factory method for new objects.
	 */
	protected abstract D create();
}
