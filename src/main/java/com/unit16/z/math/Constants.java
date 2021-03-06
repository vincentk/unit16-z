package com.unit16.z.math;

public class Constants {

	/**
	 * Using max / 2 to avoid overflow.
	 * In particular, -Integer.MIN_VALUE is smaller than 0 !
	 */
	public static final int POSITIVE_INFINITY = Integer.MAX_VALUE / 2;
	public static final int NEGATIVE_INFINITY = Integer.MIN_VALUE / 2;
}
