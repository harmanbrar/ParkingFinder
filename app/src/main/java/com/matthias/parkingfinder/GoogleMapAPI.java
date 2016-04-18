package com.matthias.parkingfinder;

/**
 * Created by Matthias on 16-02-28.
 */
public class GoogleMapAPI
{
	// not yet implemented
	static void openNavigationToAddress(Address address)
	{ }

	static double getDistanceInKmBetween(Address from, Address to)
	{
		// is faking, based on the predefined values
		return to.getDistance().getKilometer();
	}

	static Time getDrivingTimeBetween(Address from, Address to)
	{
		// is faking, based on the predefined values
		return to.getDistance().getDistanceInTime();
	}
}
