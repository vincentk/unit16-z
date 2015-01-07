package com.unit16.z.time;

import com.unit16.z.indexed.Indexed;


public enum HoursOfDay{
	_00,
	_01,
	_02,
	_03,
	_04,
	_05,
	_06,
	_07,
	_08,
	_09,
	_10,
	_11,
	_12,
	_13,
	_14,
	_15,
	_16,
	_17,
	_18,
	_19,
	_20,
	_21,
	_22,
	_23;
	
	public static Indexed<HoursOfDay> ALL = new Indexed<HoursOfDay>() {

		@Override
		public HoursOfDay get(int i) { return values()[i]; }

		@Override
		public int size() { return 24; }
	};
}
