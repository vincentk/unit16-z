package com.unit16.z.math;

public final class Rounding {
	
	private Rounding() {}
	
	/**
	 * Symmetric rounding around zero. I.e. rounding towards/away from zero rather than rounding
	 * towards floor(v) / ceil(v).
	 * 
	 * For all v:
	 * -symmetric(v) == symmetric(-v)
	 * 
	 * @param v input value
	 * @return rounded value
	 */
	public static long symmetric(double v)
	{
		final long rva = Math.round(Math.abs(v));
		
		return v >= 0 ? rva : -rva;
	}
	
}
