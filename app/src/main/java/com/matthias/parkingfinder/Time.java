package com.matthias.parkingfinder;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by Matthias on 16-02-28.
 */
public class Time implements Serializable

{
	private int hour;
	private int min;

	// takes care of when min > 60
	public Time(int hour, int min)
	{
		this.hour = hour + min / 60;
		this.min = min % 60;
	}

	public int getHour()
	{ return hour; }

	public int getMin()
	{ return min; }

	// getTotalMinValue(1h 30m) = 90m

	/**
	 * Usage: For Comparators. Classes outside of Time should use Comparators to compare time.
	 * Example: getTotalMinValue(1h 30m) = 90m
	 * @return total time in terms of minutes
	 */
	private int getTotalMinValue()
	{ return hour * 60 + min; }

	// 23h 59m
	@Override
	public String toString()
	{ return String.format("%dh %dm", hour, min); }


	public static class Comparators
	{
		public static Comparator<Time> TOTAL_MINUTES = new Comparator<Time>()
		{
			@Override
			public int compare(Time o1, Time o2)
			{
				return o1.getTotalMinValue() - o2.getTotalMinValue();
			}
		};
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (!(o instanceof Time)) return false;

		Time another = (Time) o;

		return this.getTotalMinValue() == another.getTotalMinValue();
	}
}
