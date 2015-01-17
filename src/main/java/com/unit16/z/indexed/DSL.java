package com.unit16.z.indexed;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.UnmodifiableIterator;

public abstract class DSL<B>
implements Indexed<B>, Iterable<B>
{
	private static final class ListBacked<C>
	extends DSL<C>
	{
		private final List<C> _list;

		ListBacked(List<C> i)
		{
			_list = i;
		}
		
		ListBacked(DSL<C> i)
		{
			_list = new ArrayList<>(i.size());
			for (C c : i) { _list.add(c); }
		}
		
		@Override public C get(int i) { return _list.get(i); }
		
		@Override public int size() { return _list.size(); }	
		
		@Override public Iterator<C> iterator() { return _list.iterator(); }
	}

	private static final class IndexedBacked<C>
	extends DSL<C>
	{
		private final Indexed<C> _i;
		
		IndexedBacked(Indexed<C> i) {
			_i = i;
		}

		@Override
		public C get(int i) { return _i.get(i); }

		@Override
		public int size() { return _i.size(); }

		@Override
		public Iterator<C> iterator() {
			return new UnmodifiableIterator<C>() {

				private int _j = 0;
				
				@Override
				public boolean hasNext() { return _j < size(); }

				@Override
				public C next() { _j++; return get(_j - 1);}
			};
		}
	}
	
	public static <C> DSL<C> from(List<C> c)
	{
		return new ListBacked<>(c);
	}
	
	public static <C> DSL<C> from(Indexed<C> c)
	{
		return (c instanceof DSL) ? (DSL<C>) c : new IndexedBacked<>(c); 
	}
	
	public final <C> DSL<C> map(Function<? super B, ? extends C> f)
	{
		return new OnResultOf<>(this, f);
	}
	
	public final DSL<B> head(final int max)
	{
		final Indexed<B> w = this;
		return new IndexedBacked<>(new Indexed<B>(){

			@Override
			public B get(int i) { return w.get(i); }

			@Override
			public int size() { return max; }});
	}
	
	public final DSL<B> tail(final int min)
	{
		final Indexed<B> w = this;
		return new IndexedBacked<>(new Indexed<B>(){

			@Override
			public B get(int i) { return w.get(i + min); }

			@Override
			public int size() { return w.size() - min; }});
	}
	
	public final DSL<B> filter(Predicate<? super B> p)
	{
		if (size() > 0)
		{
			final Iterator<B> i = Iterators.filter(this.iterator(), x -> p.test(x));
			if (i.hasNext())
			{
				return new ListBacked<>(Lists.newArrayList(i));
			}
		}
		
		return new Empty<>();
	}
	
	public final DSL<B> strict()
	{
		return new ListBacked<>(this);
	}
	
	public final DSL<B> append(Indexed<B> snd)
	{
		return new Concat<>(this, snd);
	}
	
	private static final class Concat<C>
	extends DSL<C>
	{
		private final DSL<C> fst_;
		private final DSL<C> snd_;
		private final int sf;
		private final int ss;
		
		Concat(Indexed<C> fst, Indexed<C> snd) { 
			fst_ = from(fst); 
			snd_ = from(snd);
			sf = fst_.size(); 
			ss = snd_.size();
		}
		@Override
		public C get(int i) { 
			return i < sf ? fst_.get(i) : snd_.get(i - sf);
		}

		@Override
		public int size() { return sf + ss; }

		@Override
		public Iterator<C> iterator() { 
			return Iterators.concat(fst_.iterator(), snd_.iterator()); }
	}
}
