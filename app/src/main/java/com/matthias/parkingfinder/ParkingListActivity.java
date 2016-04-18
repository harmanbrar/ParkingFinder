package com.matthias.parkingfinder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.*;

/**
 * Created by Matthias on 16-02-28.
 */
public class ParkingListActivity extends AppCompatActivity
{
	// Widgets on Layout
	private ListView            parkingListView;
	private TextView            textView_sortByCategory;
	private TextView            textView_sortOrder;

	// data for list
	private List<ParkingSpace>  parkingListData;
	private Address             currentUserLocation;

	// constant for bundle
	static final String         KEY_PARKING_LIST_DATA       = "PARKING_LIST_DATA";
	static final String         KEY_CURRENT_USER_LOCATION   = "CURR_USER_LOCATION";

	// constant format for textView.setText()
	private static final String FORMAT_SORT_CATEGORY        = "Arrange By %s ▾";
	private static final String FORMAT_SORT_ORDER           = "%s ▾";


	// temp var
	private final boolean       USING_STUB_DB = true;

	// set of constant Strings for dialog results
	private enum SortOption
	{
		STRING_DISTANCE("Distance"), STRING_COST("Cost"),
		STRING_ASCENDING("Ascending"), STRING_DESCENDING("Descending"),
		STRING_CANCEL("Cancel");

		private final String text;

		SortOption(final String text)
		{ this.text = text; }

		@Override
		public String toString()
		{ return text; }

		// for showSortCategoryOptionDialogBox().items
		private static final CharSequence[] getSortCategories()
		{
			int numItems = 3;

			CharSequence[] items = new CharSequence[numItems];
			items[0] = STRING_DISTANCE.toString();
			items[1] = STRING_COST.toString();
			items[2] = STRING_CANCEL.toString();

			return items;
		}

		// for showSortOrderOptionDialogBox().items
		private static final CharSequence[] getSortOrders()
		{
			int numItems = 3;

			CharSequence[] items = new CharSequence[numItems];
			items[0] = STRING_ASCENDING.toString();
			items[1] = STRING_DESCENDING.toString();
			items[2] = STRING_CANCEL.toString();

			return items;
		}
	}


	private final static String KEY_DATA_FILTER_OPTION = "com.matthias.parkingfinder.ParkingSpaceDetailActivity.FilterOption";
//	private final static String KEY_DATA_PARKING_LIST_SIZE = "com.matthias.parkingfinder.ParkingSpaceDetailActivity.ParkingListSize";
//	private final static String KEY_DATA_PARKING_SPACE = "com.matthias.parkingfinder.ParkingSpaceDetailActivity.ParkingData";
//	private final static String KEY_DATA_THUMBNAIL = "com.matthias.parkingfinder.ParkingSpaceDetailActivity.Thumbnail";
	private final static String KEY_DATA_CURRENT_USER_LOCATION = "com.matthias.parkingfinder.ParkingSpaceDetailActivity.UserLocation";

	static void startActivity(Context context, FilterOption filterOption)
	{


		Intent intent = new Intent(context, ParkingListActivity.class);
		intent.putExtra(KEY_DATA_FILTER_OPTION, filterOption);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		context.startActivity(intent);

		//
//		intent.putExtra(KEY_DATA_PARKING_LIST_SIZE, parkingSpaces.size());
//
//		for (int i = 0; i < parkingSpaces.size(); i++)
//		{
//			intent.putExtra(KEY_DATA_PARKING_SPACE + i, parkingSpaces.get(i));
//			intent.putExtra(KEY_DATA_THUMBNAIL + i, parkingSpaces.get(i).getThumbnail());
//		}


//		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	}


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		System.out.println("DEBUG: onCreate() started:");

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parking_list);

		// get the data from intent
		Intent intent = this.getIntent();
		FilterOption filterOption = (FilterOption) intent.getSerializableExtra(KEY_DATA_FILTER_OPTION);
		currentUserLocation = filterOption.getCurrentUserLocation();

		if (filterOption.isStub())
		{
			Database db = new Database();
			parkingListData = db.getStubList(getApplicationContext(), MapsActivity.getMap());
		}
		else
		{
			// real work ...
			Database db = new Database();
			parkingListData = db.getFilteredList(getApplicationContext(), filterOption);
		}


//		System.out.println("DEBUG: onCreate(): fetching list size to receive ... ");
//
//		int listSizeToReceive = intent.getIntExtra(KEY_DATA_PARKING_LIST_SIZE, -1);
//
//		System.out.println("DEBUG: onCreate(): fetching list ... ");
//
//		// data is to be received
//		for (int i = 0; i < listSizeToReceive; i++)
//		{
//			ParkingSpace parkingSpace = (ParkingSpace) intent.getSerializableExtra(KEY_DATA_PARKING_SPACE + i);
//			Bitmap thumbnail = intent.getParcelableExtra(KEY_DATA_THUMBNAIL + i);
//			parkingSpace.setThumbnail(thumbnail);
//			parkingSpaces.add(parkingSpace);
//		}
//
//		System.out.println("DEBUG: onCreate() fetching user address ... ");
//
//		currentUserLocation = (Address) intent.getSerializableExtra(KEY_DATA_CURRENT_USER_LOCATION);
//
//
//		System.out.println("DEBUG: onCreate() data fetching process done:");
//		System.out.printf("DEBUG: listSizeToReceive: %d\n", listSizeToReceive);
//		System.out.printf("DEBUG: size of list: %d, first parking name: %s\n", parkingSpaces.size(), parkingSpaces.get(0).getName());


		// find buttons
		textView_sortByCategory = (TextView) findViewById(R.id.textView_ParkingList_arrangeBy);
		textView_sortOrder      = (TextView) findViewById(R.id.textView_ParkingList_sortOrder);

		// assign click listeners to buttons
		textView_sortByCategory.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				showSortCategoryOptionDialogBox();
			}
		});

		textView_sortOrder.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				showSortOrderOptionDialogBox();
			}
		});

		// initialise the list
		parkingListView = (ListView) findViewById(R.id.listView_ParkingList_parkingList);
		parkingListView.setAdapter(new ParkingListAdapter(this, parkingListData, currentUserLocation));

		// sort with distance by default
		textView_sortByCategory.setText(String.format(FORMAT_SORT_CATEGORY, SortOption.STRING_DISTANCE.toString()));
		textView_sortOrder.setText(String.format(FORMAT_SORT_ORDER, SortOption.STRING_ASCENDING.toString()));
		sortList();
	}

	// ================================================================
	// Methods for Sort Option Dialog
	// ================================================================

	private void showSortCategoryOptionDialogBox()
	{
		// Distance from me == Distance from my destination, because myLocation == Destination
		final CharSequence[] items = SortOption.getSortCategories();

		AlertDialog.Builder builder = new AlertDialog.Builder(ParkingListActivity.this);
		builder.setTitle("Arrange By");
		builder.setItems(items, new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int item)
			{
				if (!items[item].equals(SortOption.STRING_CANCEL.toString()))
				{
					// update the text of the arrange option button
					textView_sortByCategory.setText(String.format(FORMAT_SORT_CATEGORY, items[item]));
					sortList();
				}
			}
		});

		builder.show();
	}


	private void showSortOrderOptionDialogBox()
	{
		// Distance from me == Distance from my destination, because myLocation == Destination
		final CharSequence[] items = SortOption.getSortOrders();

		AlertDialog.Builder builder = new AlertDialog.Builder(ParkingListActivity.this);
		builder.setTitle("Arrange By");
		builder.setItems(items, new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int item)
			{
				if (!items[item].equals(SortOption.STRING_CANCEL.toString()))
				{
					textView_sortOrder.setText(String.format(FORMAT_SORT_ORDER, items[item].toString()));
					sortList();
				}
			}
		});

		builder.show();
	}


	// ================================================================
	// Methods for Dialog Listeners and Sort Options
	// ================================================================

	private void sortList()
	{
		// do sort operation with specified order - this.parkingListData will be updated
		if (isSortByDistance())
			sortListByDistance();
		else if (isSortByCost())
			sortListByPrice();

		if (isDescending())
			Collections.reverse(parkingListData);       // ascending sort by default

		// prompt listview that item has changed
		parkingListView.setAdapter(new ParkingListAdapter(this, parkingListData, currentUserLocation));
		((ParkingListAdapter) parkingListView.getAdapter()).notifyDataSetChanged();
	}

	private void sortListByDistance()
	{ Collections.sort(parkingListData, ParkingSpace.Comparators.DISTANCE); }

	private void sortListByPrice()
	{ Collections.sort(parkingListData, ParkingSpace.Comparators.PRICE); }

	boolean isSortByDistance()
	{ return textView_sortByCategory.getText().toString().contains(SortOption.STRING_DISTANCE.toString()); }

	boolean isSortByCost()
	{ return textView_sortByCategory.getText().toString().contains(SortOption.STRING_COST.toString()); }

	boolean isDescending()
	{ return textView_sortOrder.getText().toString().contains(SortOption.STRING_DESCENDING.toString()); }

	// ================================================================
	// Methods for stub DB
	// ================================================================

}
