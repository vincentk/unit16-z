package com.unit16.z.indexed;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;

public final class PredicatesI {
	private PredicatesI(){}
	
	public static <T> Predicate<Indexed<? extends T>> any(final Predicate<T> p)
	{
		return new Predicate<Indexed<? extends T>>(){

			@Override public boolean apply(final Indexed<? extends T> input) {
				return Iterables.any(DSL.from(input), p);
			}};
	}
	
	public static <T> Predicate<Indexed<? extends T>> all(final Predicate<T> p)
	{
		return Predicates.not(any(Predicates.not(p)));
	}
}
