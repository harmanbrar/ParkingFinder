package com.matthias.parkingfinder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * Created by Matthias on 16-03-02.
 */
public class Database
{
	// ================================================================
	// Methods for stub DB
	// ================================================================

	public Database()
	{

	}
	public static ArrayList<ParkingSpace> getStubList(Context context, GoogleMap gMap)
	{
		Bitmap      thumbnail           = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_thumbnail_parkade_default);
		String      parkingName         = "My Fake Parking";
		String      streetName          = "Fake Street";
		int         streetNumber        = 1;
		String      zipCode             = "R3T 2N2";
		double      price               = 3.5;
		double      noFlatRate          = ParkingSpace.NO_FLAT_RATE;
		double      flatRate            = 5;
		TimePeriod  chargingTime;
		TimePeriod  nonParkingTime;
		ParkingSpace.ParkingType type   = ParkingSpace.ParkingType.PARKADE;
		boolean     hasCamera           = true;
		boolean     hasAttendant        = true;
		ArrayList<ParkingSpace> list = new ArrayList<>();
		Address address;
		ParkingSpace foo;
		Distance distance;

		/*New Parking Addition Index 0*/
		LatLng latlng = new LatLng(49.806428, -97.141057);
		Marker marker = gMap.addMarker(new MarkerOptions().position(latlng).title("University Of Manitoba U Lot").snippet("Pay Parking 7:30-4:30pm"));

		chargingTime = new TimePeriod(0730,1630);
		nonParkingTime = new TimePeriod(0, 0);

		distance = new Distance(0, 750, new Time(0, 2));        // could be (0.75)
		address = new Address("Chancellor Meatheson Rd", streetNumber , zipCode, distance);
		foo = new ParkingSpace(thumbnail, "U Lot Parking", address, 1.5, noFlatRate,
		                       chargingTime, nonParkingTime,
		                       ParkingSpace.ParkingType.SURFACE_LOT, hasCamera, hasAttendant, marker);
		list.add(foo);

		/*New Parking Addition Index 1*/
		latlng = new LatLng(49.810946, -97.138281);
		marker = gMap.addMarker(new MarkerOptions().position(latlng).title("University Of Manitoba Q Lot").snippet("Pay Parking 7:30-4:30pm"));

		chargingTime = new TimePeriod(0730,1630);
		nonParkingTime = new TimePeriod(0, 0);
		distance = new Distance(1.4, new Time(0, 3));
		address = new Address("Dysart Rd", streetNumber , zipCode, distance);
		foo = new ParkingSpace(thumbnail, "Q Lot Parking", address, 1.5, noFlatRate,
		                       chargingTime, nonParkingTime,
		                       ParkingSpace.ParkingType.SURFACE_LOT, hasCamera, hasAttendant,marker);
		list.add(foo);

		/*New Parking Addition Index 2*/
		latlng = new LatLng(49.811246, -97.135759);
		marker = gMap.addMarker(new MarkerOptions().position(latlng).title("Science Parking Lot").snippet("Pay Parking 7:30-4:30pm"));

		chargingTime = new TimePeriod(0730,1630);
		nonParkingTime = new TimePeriod(0, 0);
		foo = new ParkingSpace(thumbnail, "Science Building Parking", address, 1.5, flatRate,
		                       chargingTime, nonParkingTime,
		                       ParkingSpace.ParkingType.SURFACE_LOT, hasCamera, hasAttendant, marker);
		list.add(foo);

		/*New Parking Addition Index 3*/
		latlng = new LatLng(49.811486, -97.128108);
		marker = gMap.addMarker(new MarkerOptions().position(latlng).title("University Of Manitoba B Lot").snippet("Pay Parking 7:30-4:30pm"));

		chargingTime = new TimePeriod(0730,1630);
		nonParkingTime = new TimePeriod(0, 0);
		address = new Address("Saunderson St", streetNumber , zipCode, new Distance(0, 650, new Time(0, 3)));
		foo = new ParkingSpace(thumbnail, "B Lot Parking", address, 1.5, flatRate + 1,
		                       chargingTime, nonParkingTime,
		                       ParkingSpace.ParkingType.SURFACE_LOT, hasCamera, hasAttendant, marker);
		list.add(foo);

		/*New Parking Addition Index 4*/
		latlng = new LatLng(49.810897, -97.129281);
		marker = gMap.addMarker(new MarkerOptions().position(latlng).title("University Of Manitoba B Lot Meter").snippet("Pay Parking 7:30-4:30pm"));

		chargingTime = new TimePeriod(0730,1630);
		nonParkingTime = new TimePeriod(0, 0);
		address = new Address("Saunderson St", streetNumber , zipCode, new Distance(0, 650, new Time(0, 3)));
		foo = new ParkingSpace(thumbnail, "B Lot Parking Meter", address, 1.5,  flatRate - 1,
		                       chargingTime, nonParkingTime,
		                       ParkingSpace.ParkingType.SURFACE_LOT, hasCamera, hasAttendant, marker);
		list.add(foo);

		/*New Parking Addition Index 5*/
		latlng = new LatLng(49.809354, -97.128076);
		marker = gMap.addMarker(new MarkerOptions().position(latlng).title("North Drake Center Parking").snippet("Pay Parking 7:30-4:30pm"));

		chargingTime = new TimePeriod(0730,1630);
		nonParkingTime = new TimePeriod(0, 0);
		address = new Address("Saunderson St", streetNumber , zipCode, new Distance(0, 400, new Time(0, 3)));
		foo = new ParkingSpace(thumbnail, "North Drake Center Parking", address, 1.5,  flatRate + 0.5,
		                       chargingTime, nonParkingTime,
		                       ParkingSpace.ParkingType.SURFACE_LOT, hasCamera, hasAttendant, marker);
		list.add(foo);

		/*New Parking Addition Index 6*/
		latlng = new LatLng(49.807932, -97.133475);
		marker = gMap.addMarker(new MarkerOptions().position(latlng).title("University Of Manitoba Eng Lot").snippet("Pay Parking 7:30-4:30pm"));

		chargingTime = new TimePeriod(0730,1630);
		nonParkingTime = new TimePeriod(0, 0);
		address = new Address("Saunderson St", streetNumber , zipCode, new Distance(0, 180, new Time(0, 1)));
		foo = new ParkingSpace(thumbnail, "University Of Manitoba Eng Lot", address, 1.5, flatRate + 0.5,
		                       chargingTime, nonParkingTime,
		                       ParkingSpace.ParkingType.SURFACE_LOT, hasCamera, hasAttendant, marker);
		list.add(foo);

		/*New Parking Addition Index 7*/
		latlng = new LatLng(49.806301, -97.132372);
		marker = gMap.addMarker(new MarkerOptions().position(latlng).title("University Of Manitoba D Lot").snippet("Restricted: 7:30-4:30pm Free: 4:30am to 7:30am"));

		chargingTime = new TimePeriod(0,0);
		nonParkingTime = new TimePeriod(0730, 1630);
		address = new Address("Saunderson St", streetNumber , zipCode, new Distance(0, 650, new Time(0, 3)));
		foo = new ParkingSpace(thumbnail, "University Of Manitoba B Lot", address, 0,  flatRate + 1.5,
		                       chargingTime, nonParkingTime,
		                       ParkingSpace.ParkingType.SURFACE_LOT, hasCamera, hasAttendant, marker);
		list.add(foo);

		/*New Parking Addition Index 8*/
		latlng = new LatLng(49.809554, -97.135847);
		marker = gMap.addMarker(new MarkerOptions().position(latlng).title("University Parkade").snippet("30min: $2| 1 Hour: $4| 24 Hours: $16"));

		chargingTime = new TimePeriod(0,24);
		nonParkingTime = new TimePeriod(0, 0);
		address = new Address("Saunderson St", streetNumber , zipCode, new Distance(1.2, new Time(0, 3)));
		foo = new ParkingSpace(thumbnail, "University Parkade", address, 4, noFlatRate,
		                       chargingTime, nonParkingTime,
		                       ParkingSpace.ParkingType.PARKADE, hasCamera, hasAttendant, marker);
		list.add(foo);

		return list;
	}

	ArrayList<ParkingSpace> getFilteredList(Context context, FilterOption filterOption)
	{
		return new ArrayList<>();
	}
}
