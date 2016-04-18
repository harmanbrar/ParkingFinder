package com.matthias.parkingfinder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ParkingSpaceDetailActivity extends AppCompatActivity
{
	private final static String KEY_DATA_PARKING_SPACE = "com.matthias.parkingfinder.ParkingSpaceDetailActivity.ParkingData";
	private final static String KEY_DATA_THUMBNAIL     = "com.matthias.parkingfinder.ParkingSpaceDetailActivity.Thumbnail";

	static void startActivity(Context context, ParkingSpace parkingSpace)
	{
		Intent intent = new Intent(context, ParkingSpaceDetailActivity.class);
		intent.putExtra(KEY_DATA_PARKING_SPACE, parkingSpace);
		intent.putExtra(KEY_DATA_THUMBNAIL, parkingSpace.getThumbnail());
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parking_space_detail);

		Intent intent       = this.getIntent();
		ParkingSpace data   = (ParkingSpace) intent.getSerializableExtra(KEY_DATA_PARKING_SPACE);
		Bitmap thumbnail    = intent.getParcelableExtra(KEY_DATA_THUMBNAIL);

		// work on stuff ...
		TextView textView   = (TextView) findViewById(R.id.textView);
		TextView textView1  = (TextView) findViewById(R.id.textView2);
		ImageView imageView = (ImageView) findViewById(R.id.imageView);

		// change stuff ...
		textView.setText(data.getName());
		textView1.setText(data.getAddress().toString());
		imageView.setImageBitmap(thumbnail);
//		imageView.setImageBitmap(data.getThumbnail());  // will be null because of transiency,
	}
}
