package edu.depaul.snotg_android.Map;

import java.util.List;

import android.os.Bundle;
import android.view.Window;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import edu.depaul.snotg_android.R;
import edu.depaul.snotg_android.R.id;
import edu.depaul.snotg_android.R.layout;

public class ShowTheMap extends MapActivity {
	
	private static double lat = 41.9249247;
	private static double lon = -87.6550303;
	private int latE6;
	private int lonE6;
	private MapController mapControl;
	private GeoPoint gp;
	private GeoPoint gp2;
	private MapView mapView;
	
	//Overlay stuff
	private MyMyLocationOverlay myLocationOverlay;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); //suppresses the title bar
		setContentView(R.layout.showthemap);
		
		//Add map controller w/ zoom 
		mapView = (MapView) findViewById(R.id.mv);
		mapView.setSatellite(true);
		mapView.setTraffic(false);
		mapView.setBuiltInZoomControls(true); 
		int maxZoom = mapView.getMaxZoomLevel();
		int initZoom = (int)(0.80*(double)maxZoom);
		mapControl = mapView.getController();
		mapControl.setZoom(initZoom);
		//Android only does microdegrees
		latE6 = (int)(lat*1e6);
		lonE6 = (int)(lon*1e6);
		gp = new GeoPoint(latE6, lonE6);
		mapControl.animateTo(gp);
		
		//Set up compass and dot
		List<Overlay> overlays = mapView.getOverlays();
		myLocationOverlay = new MyMyLocationOverlay(this,mapView);
		overlays.add(myLocationOverlay);
		
		myLocationOverlay.enableCompass();
		myLocationOverlay.enableMyLocation();
	}
	

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	public static void putLatLong(double latitude, double longitude){
		lat = latitude;
		lon = longitude;
	}

}
