package com.unit16.z.indexed;

import com.google.common.collect.UnmodifiableIterator;

final class IteratorIndexed<B> extends UnmodifiableIterator<B> {

	private int index_ = 0;
	private final Indexed<B> indexed_;
	
	public IteratorIndexed(Indexed<B> i) {
		indexed_ = i;
	}
	
	@Override
	public boolean hasNext() { return index_ < indexed_.size(); }

	@Override
	public B next() {
		final B b = indexed_.get(index_);
		index_++;
		return b;
	}
}
