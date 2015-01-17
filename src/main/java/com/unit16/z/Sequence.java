package com.unit16.z;

import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Predicate;

import com.google.common.collect.Iterators;
import com.google.common.collect.UnmodifiableIterator;

/**
 * Like {@link Iterator}, but immutable (and hence, simpler).
 */
public abstract class Sequence<A> implements Iterable<A> {
	
	public abstract A next();
	public abstract boolean hasNext();
	
	public static <B> Sequence<B> fromIterator(final Iterator<B> source)
	{
		return new Sequence<B>(){

			@Override
			public B next() { return source.next(); }

			@Override
			public boolean hasNext() { return source.hasNext(); }
		};
	}
	
	public static <B> Sequence<B> fromIterable(final Iterable<B> source)
	{
		return fromIterator(source.iterator());
	}
	
	public final <C> Sequence<Pair<A, C>> zip(final Sequence<C> snd)
	{
		final Sequence<A> fst = this;
		return new Sequence<Pair<A, C>>(){

			@Override
			public Pair<A, C> next() {
				return new Pair.Const<>(fst.next(), snd.next());
			}

			@Override
			public boolean hasNext() {
				return fst.hasNext() && snd.hasNext();
			}};
	}
	
	public final <D> Sequence<D> onResultOf(Function<A, D> f)
	{
		return fromIterator(Iterators.transform(iterator(), x -> f.apply(x)));
	}
	
	public final Sequence<A> filter(Predicate<A> p)
	{
		return fromIterator(Iterators.filter(iterator(), x -> p.test(x)));
	}
	
	@Override
	public final Iterator<A> iterator()
	{
		return new UnmodifiableIterator<A>() {

			@Override
			public boolean hasNext() { return Sequence.this.hasNext(); }

			@Override
			public A next() { return Sequence.this.next(); }
		};
	}
}
