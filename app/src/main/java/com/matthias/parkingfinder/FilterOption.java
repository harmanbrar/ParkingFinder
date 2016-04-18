package com.matthias.parkingfinder;

import java.io.Serializable;

/**
 * Created by Matthias on 16-03-02.
 */
public class FilterOption implements Serializable
{
	// this class is used to be passed around to get filter option and get a list from GoogleMapAPI each time needed ...
	// ... instead of passing around the whole list ...
	// ... MapsActivity -> Create Filter (+ currUserLocation) -> Pass Filter to ParkingListActivity
	// ... ParkingListActivity -> Request to DB to create a list with the filter
	// ... ParkingListActivity -> Pass single parking space info to ParkingDetailActivity

	// is to be used in ParkingListActivity, other classes (adapters) do not need to know about this.
	private boolean isStub;
	private Address currentUserLocation;

	public FilterOption(boolean isStub, Address currentUserLocation)
	{
		this.isStub = isStub;
		this.currentUserLocation = currentUserLocation;
	}

	boolean isStub()
	{
		return isStub;
	}

	Address getCurrentUserLocation()
	{
		return currentUserLocation;
	}
}
