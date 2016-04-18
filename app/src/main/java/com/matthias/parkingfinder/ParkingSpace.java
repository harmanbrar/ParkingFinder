package com.matthias.parkingfinder;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.Marker;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by Matthias on 16-02-27.
 */
public class ParkingSpace implements Serializable
{
	static final double NO_FLAT_RATE = -1;          // if no flat rate for the lot, this should be used.

	// constant var for parking space parkingType
	enum ParkingType implements Serializable
	{ STREET_PARKING, PARKADE, SURFACE_LOT }

	// details of the parking space
	private transient Bitmap thumbnail;
	private String name;
	private Address address;
	private double price;
	private double flatRate;                        // if no flat rate
	private TimePeriod chargingTimePeriod;          // non-charging time = 24 hours - charging time - nonParkingTime
	private TimePeriod nonParkingTimePeriod;        // times when people are not allowed to park
	private ParkingType parkingType;
	private boolean hasCamera;
	private boolean hasAttendant;
	private transient Marker marker;				//Used to toggle on/off marks on the map for filter options.



	public ParkingSpace(){}							//Null Constructor

	public ParkingSpace(Bitmap thumbnail, String name, Address address, double price, double flatRate,
	                    TimePeriod chargingTimePeriod, TimePeriod nonParkingTimePeriod,
	                    ParkingType parkingType, boolean hasCamera, boolean hasAttendant, Marker marker)
	{
		this.thumbnail = thumbnail;
		this.name = name;
		this.address = address;
		this.price = price;
		this.flatRate = flatRate;
		this.chargingTimePeriod = chargingTimePeriod;
		this.nonParkingTimePeriod = nonParkingTimePeriod;
		this.parkingType = parkingType;
		this.hasCamera = hasCamera;
		this.hasAttendant = hasAttendant;
		this.marker = marker;
	}

	// ================================================================
	// Getter Methods
	// ================================================================

	// used for passing around the parking space and bitmap data
	void setThumbnail(Bitmap thumbnail)
	{ this.thumbnail = thumbnail; }

	Bitmap getThumbnail()
	{ return thumbnail; }

	String getName()
	{ return name; }

	Address getAddress()
	{ return address; }

	double getPrice()
	{ return price; }

	boolean	checkChargeTime(TimePeriod time) { return chargingTimePeriod.hasOverlapWith(time);}
	
	boolean checkNoParkingTime (TimePeriod time) {return nonParkingTimePeriod.hasOverlapWith(time);}

	boolean hasFlatRate()
	{ return flatRate != NO_FLAT_RATE || flatRate >= 0; }

	double getFlatRate()
	{ return flatRate; }

	String getPriceString()
	{ return String.format("$%.1f / hr", getPrice()); }

	void setMarkerFalse()
	{ this.marker.setVisible(false);}
	void setMarkerTrue()
	{ this.marker.setVisible(true);}
	// ================================================================
	// Methods to check parking ParkingType
	// ================================================================

	boolean isStreetParking()
	{ return parkingType.equals(ParkingType.STREET_PARKING); }

	boolean isParkade()
	{ return parkingType.equals(ParkingType.PARKADE); }

	boolean isSurfaceLot()
	{ return parkingType.equals(ParkingType.SURFACE_LOT); }


	public static class Comparators
	{
		public static Comparator<ParkingSpace> PRICE = new Comparator<ParkingSpace>()
		{
			@Override
			public int compare(ParkingSpace o1, ParkingSpace o2)
			{
				double diff = o1.getPrice() - o2.getPrice();

				if (diff > 0)
					return 1;
				else if (diff == 0)
					return 0;
				else
					return -1;
			}
		};

		public static Comparator<ParkingSpace> DISTANCE = new Comparator<ParkingSpace>()
		{
			@Override
			public int compare(ParkingSpace lhs, ParkingSpace rhs)
			{
				double diff = lhs.getAddress().getDistance().getKilometer() - rhs.getAddress().getDistance().getKilometer();

				if (diff > 0)
					return 1;
				else if (diff == 0)
					return 0;
				else
					return -1;
			}
		};
	}
}
