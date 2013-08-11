package com.crunchbang.takshak;

import android.os.Bundle;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

public class LocationActivity extends SherlockFragmentActivity {

	//to be update with event locations
	private GoogleMap mMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//TODO markers
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location_layout);

		SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		mMap = fm.getMap();
		Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show();
	}

}
