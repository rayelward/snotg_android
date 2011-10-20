package edu.depaul.snotg_android;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

public class MyMapLocationActivity extends MapActivity {
	//testing commit from GARRY
	private MapView mapView;
	private MyLocationOverlay myLocationOverlay;
	

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// main.xml contains a MapView
		setContentView(R.layout.my_location_map);

		// extract MapView from layout
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);

		// create an overlay that shows our current location
		myLocationOverlay = new MyLocationOverlay(this, mapView);
		
		// add this overlay to the MapView and refresh it
		mapView.getOverlays().add(myLocationOverlay);
		mapView.postInvalidate();

		// call convenience method that zooms map on our location
		
		myLocationOverlay.runOnFirstFix(new Runnable() {
			public void run() {
				zoomToMyLocation();
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		// when our activity resumes, we want to register for location updates
		myLocationOverlay.enableMyLocation();
		myLocationOverlay.runOnFirstFix(new Runnable() {
			public void run() {
				zoomToMyLocation();
			}
		});
	}

	@Override
	protected void onPause() {
		super.onPause();
		// when our activity pauses, we want to remove listening for location
		// updates
		myLocationOverlay.disableMyLocation();
	}

	/**
	 * This method zooms to the user's location with a zoom level of 10.
	 */
	private void zoomToMyLocation() {
		GeoPoint myLocationGeoPoint = myLocationOverlay.getMyLocation();
		if (myLocationGeoPoint != null) {
			mapView.getController().animateTo(myLocationGeoPoint);
			mapView.getController().setZoom(15);
			// send myLocationGeoPoint with my data to the server.
		} else {
			Toast.makeText(this, "Cannot determine location", Toast.LENGTH_LONG)
					.show();
		}
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}