package com.matthias.parkingfinder;

import java.io.Serializable;

/**
 * Created by Matthias on 16-02-27.
 *
 * Example of Locations:
 * - Current Location of the user
 * - Destination (may not have both streetName / zipCode)
 * - Address of ParkingSpace
 *
 * Future Addition depending on Google Map API?
 * - Address of ... city, country? if required then such class members should be added to this class ...
 *
 */
public class Address implements Serializable
{
	private String streetName;
	private int streetNumber;       // may not have it
	private String zipCode;
	private Distance distance;

	public Address(String streetName, int streetNumber, String zipCode, Distance fakeDistance)
	{
		this.streetName = streetName;
		this.streetNumber = streetNumber;
		this.zipCode = zipCode;             // R3T 2N2 => R3T2N2, not implemented as all are hard coded
		this.distance = fakeDistance;
	}

	// some Address may not have street number
	boolean hasStreetNumber()
	{ return streetNumber > 0; }

	@Override
	public String toString()
	{
		if (hasStreetNumber())
			return streetNumber + " " + streetName + " " + zipCode;
		else
			return streetName + " " + zipCode;
	}

	// ================================================================
	// Methods for Comparing with Other Address
	// ================================================================

	/**
	 *
	 * @param another
	 * @return distance from another Address in terms of km. Maybe use
	 */
	double getDistanceInKmFrom(Address another)
	{
		return GoogleMapAPI.getDistanceInKmBetween(this, another);
	}

	String getDistanceInKmStringFrom(Address another)
	{ return String.format("%.1fkm", getDistanceInKmFrom(another)); }

	Time getDrivingTime(Address to)
	{ return GoogleMapAPI.getDrivingTimeBetween(this, to); }

	// now it's predefined
	// in real version, this should be done by google api
	Distance getDistance()
	{ return distance; }
}
