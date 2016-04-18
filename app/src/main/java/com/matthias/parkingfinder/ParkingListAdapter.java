package com.matthias.parkingfinder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.List;

/**
 * Created by Matthias on 16-02-28.
 */
public class ParkingListAdapter extends BaseAdapter
{
	private Context             context;
	private List<ParkingSpace>  data;
	private Address             currentUserLocation;
	private static LayoutInflater inflater = null;

	public ParkingListAdapter(Context context, List<ParkingSpace> data, Address currentUserLocation)
	{
		this.context = context;
		this.data = data;
		this.currentUserLocation = currentUserLocation;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount()
	{ return data.size(); }

	@Override
	public Object getItem(int position)
	{ return data.get(position); }

	@Override
	public long getItemId(int position)
	{ return position; }

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View view = convertView;
		if (view == null)
			view = inflater.inflate(R.layout.listitem_parking_list, null);

		// fetch data tag for this row
		final ParkingSpace datum = data.get(position);

		// get views for the row
		LinearLayout detailLayout   = (LinearLayout) view.findViewById(R.id.linearLayout_ParkingListItem_itemDetailLinearLayout);
		ImageView    thumbnail      = (ImageView) view.findViewById(R.id.imageView_ParkingListItem_thumbnail);
		ImageView    navigationBtn  = (ImageView) view.findViewById(R.id.imageView_ParkingListItem_navigation);
		TextView     parkingName    = (TextView)  view.findViewById(R.id.textView_ParkingListItem_name);
		TextView     parkingAddr    = (TextView)  view.findViewById(R.id.textView_ParkingListItem_address);
		TextView     distanceInKM   = (TextView)  view.findViewById(R.id.textView_ParkingListItem_distanceInKM);
		TextView     distanceInTime = (TextView)  view.findViewById(R.id.textView_ParkingListItem_distanceInTime);
		TextView     pricePerHour   = (TextView)  view.findViewById(R.id.textView_ParkingListItem_price);

		// assign data to views
		thumbnail.setImageBitmap(datum.getThumbnail());
		parkingName.setText(datum.getName());
		parkingAddr.setText(datum.getAddress().toString());     // street Number + street Addr + zip code
		distanceInKM.setText(currentUserLocation.getDistanceInKmStringFrom(datum.getAddress()));
		distanceInTime.setText(currentUserLocation.getDrivingTime(datum.getAddress()).toString());
		pricePerHour.setText(datum.getPriceString());

		// assign click listeners
		detailLayout.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				ParkingSpaceDetailActivity.startActivity(context, datum);
			}
		});

		navigationBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				GoogleMapAPI.openNavigationToAddress(datum.getAddress());

				String prompt = String.format("Navigation to %s is not yet implemented.", datum.getAddress().toString());
				Toast.makeText(context, prompt, Toast.LENGTH_SHORT).show();
			}
		});

		return view;
	}
}
