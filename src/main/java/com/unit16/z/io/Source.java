package com.unit16.z.io;

import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;

public interface Source<A> extends Closeable {

	public A next() throws IOException;
	
	
	/**
	 * @return true exactly if the source is open (has not been closed -- {@link #close()})
	 * and we expect to be able to read another item from it.
	 * @throws IOException
	 */
	public boolean hasNext() throws IOException;

	public static final class Empty<C>
	implements Source<C>
	{
		@Override
		public void close() {}

		@Override
		public C next() throws IOException { throw new IOException("Empty."); }

		@Override
		public boolean hasNext() { return false; }
		
	}
	
	public static final class Sequential<B>
	implements Source<B>
	{
		private final Iterator<? extends Source<? extends B>> _iter;
		private Source<? extends B> _curr;
		private boolean hasNext = false;
		
		
		public Sequential(Iterator<? extends Source<? extends B>> i) throws IOException
		{
			_iter = i;
			_curr = _iter.hasNext() ? _iter.next() : new Empty<B>();
			
			advance();
		}
		
		@Override
		public void close() throws IOException {
			_curr.close();
			while (_iter.hasNext())
			{
				_iter.next().close();
			}
		}

		@Override
		public B next() throws IOException { 
			
			final B n = _curr.next();
			
			advance();
			
			return n;
		}

		private void advance() throws IOException {
			while (!_curr.hasNext() && _iter.hasNext())
			{
				_curr.close();
				_curr = _iter.next();
			}
			
			hasNext = _curr.hasNext();
		}

		@Override
		public boolean hasNext() { return hasNext; }
		
	}
}
