package com.unit16.z;

public interface Continuation<A> {

	public void handle(A val);
}
