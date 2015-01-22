package com.unit16.z.time;

/**
 * In the future, we will know how to handle the events which we {@link #register(MaybeEmpty)}.
 */
public interface Future {
	
	public void register(MaybeEmpty eventSource);
}
