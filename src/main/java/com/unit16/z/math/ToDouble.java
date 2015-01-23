package com.unit16.z.math;

import com.unit16.z.indexed.Indexed;

public interface ToDouble<A> {

	public double apply(A val);
	
	public static final class DSL<B>
	{
		private final ToDouble<B> td;
		public DSL(ToDouble<B> td_) { td = td_; }
		
		public final double[] from(Indexed<B> idx)
		{
			final double[] r = new double[idx.size()];
			into(idx, r);
			return r;
		}
		
		public void into(Indexed<? extends B> idx, double[] dst)
		{
			final int n = idx.size();
			for (int i = 0; i < n; i++) { dst[i] = td.apply(idx.get(i)); }
		}
		
		public double min(Iterable<? extends B> src)
		{
			double m = Double.MAX_VALUE;
			for (B b : src) { m = Math.min(m, td.apply(b)); }
			return m;
		}
		
		public double max(Iterable<? extends B> src)
		{
			double m = Double.MIN_VALUE;
			for (B b : src) { m = Math.max(m, td.apply(b)); }
			return m;
		}
	}
}
