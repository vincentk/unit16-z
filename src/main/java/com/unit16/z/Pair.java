package com.unit16.z;

import com.google.common.base.Function;
import com.google.common.collect.UnmodifiableIterator;


public interface Pair<A, B> {
	
	A fst();
	
	B snd();
	
	public static final class S
	{
		public static <X> Function<Pair<X, ?>, X> fstM()
		{
			
			return new Function<Pair<X, ?>, X>()
			{
				@Override
				public X apply(Pair<X, ?> input) { return input.fst(); }	
			};
		}
		
		public static <X> Function<Pair<?, X>, X> sndM()
		{
			
			return new Function<Pair<?, X>, X>()
			{
				@Override
				public X apply(Pair<?, X> input) { return input.snd(); }	
			};
		}
	}
	
	public static interface IUniform<C> extends Pair<C, C> {
		C get(boolean flag);
	}
	
	public static class Const<C, D> implements Pair<C, D>
	{
		private final C _c;
		private final D _d;
		
		public Const(C c, D d)
		{
			_c = c;
			_d = d;
		}

		@Override public final C fst() { return _c; }

		@Override public final D snd() { return _d; }
		
		public static class Uniform<E> extends Const<E, E> 
		implements Pair.IUniform<E>, Iterable<E>
		{
			public static <E> Uniform<E> pair(E c, E d)
			{
				return new Uniform<>(c, d);
			}
			
			protected Uniform(E c, E d) {
				super(c, d);
			}
			
			@Override
			public final E get(boolean isFirst)
			{
				return isFirst ? fst() : snd();
			}

			@Override
			public final UnmodifiableIterator<E> iterator() {
				return new UnmodifiableIterator<E>() {

					int count = 0;
					
					@Override public boolean hasNext() { return count < 2; }

					@Override
					public E next() {
						count++;
						return count == 1 ? fst() : snd();
					}
				};
			}
			
			public final <F> Const.Uniform<F> functor(Function<? super E, F> f)
			{
				return pair(f.apply(fst()), f.apply(snd()));
			}
			
			@Override
			public String toString()
			{
				return "(" + fst() + "," + snd() + "}";
			}
		}
	}
}
