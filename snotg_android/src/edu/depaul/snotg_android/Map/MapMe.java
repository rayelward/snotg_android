package edu.depaul.snotg_android.Map;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import edu.depaul.snotg_android.R;
import edu.depaul.snotg_android.R.drawable;
import edu.depaul.snotg_android.R.id;
import edu.depaul.snotg_android.R.layout;

public class MapMe extends MapActivity implements LocationListener, OnClickListener{
	
	//user context
	static Context context;
	
	//Fake data to test the users data
	//Reference Depauls Coordinates is (41.9249247,-87.6550303)
	private OverlayItem [] userItem = {
            new OverlayItem( new GeoPoint((int)(41.9249390*1e6),(int) (int)(-87.656*1e6)), "Kunal", "Going for a 900 mile run!"), 
            new OverlayItem( new GeoPoint((int)(41.926*1e6),(int) (int)(-87.6550303*1e6)), "Michael", "I'm so hungry"),
            new OverlayItem( new GeoPoint((int)(41.9249370*1e6),(int) (int)(-87.654*1e6)), "Milad", "Android Development is better than iOS!"),
            new OverlayItem( new GeoPoint((int)(41.923*1e6),(int) (int)(-87.657*1e6)), "Jeff", "In class...I need some help!")
	};
	
	private static double lat;
	private static double lon;
	private MapController mapControl;
	private MapView mapView;
	LocationManager locman;
	Location loc;
	String provider = LocationManager.GPS_PROVIDER;
	String TAG = "GPStest";
	Bundle locBundle;
	int numberSats = -1;
	float satAccuracy = 2000;
	private float bearing;
	private double altitude;
	private float speed;
	private String currentProvider;
	private List<Overlay> mapOverlays;
	
	//Overlay stuff
	private MyMyLocationOverlay myLocationOverlay;

	
	//Button to trigger the overLay of the other nearby users :)
	private Button nearbyUsersButton;
	
	//How often do we want to update the location?	
	long GPSupdateInterval; //millisec
	float GPSmoveInterval; //In meters
	
	//Profile Overlay
	private List<Overlay> userOverlays;
	private Drawable usermarker;
	private ProfileOverlay itemizedOverlay1;
	private boolean usersDisplayed = false;
	
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mapme);
		
	
		// Get application context for later use
        context = getApplicationContext();
        
		GPSupdateInterval = 5000;
		GPSmoveInterval = 1;
		
		//Set up location manager for determining the present location of the phone
		locman = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		
		//Listener for GPS STATUS
		final GpsStatus.Listener onGpsStatusChange = new GpsStatus.Listener(){
			public void onGpsStatusChanged(int event){
				switch(event){
				case GpsStatus.GPS_EVENT_STARTED:
					//Started
					break;
					
				case GpsStatus.GPS_EVENT_FIRST_FIX:
					//First Fix
					Toast.makeText(MapMe.this, "Sweet GPS got it's first fix", Toast.LENGTH_LONG).show();
					break;
				case GpsStatus.GPS_EVENT_STOPPED:
					//Stopped
					break;
				case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
					//Satellite update
					break;
				}
				
				GpsStatus status = locman.getGpsStatus(null);
				
				Iterable<GpsSatellite> satlist = status.getSatellites();
				
					
				}
			};
			
		locman.addGpsStatusListener(onGpsStatusChange);
		locman.requestLocationUpdates(provider,GPSupdateInterval,GPSmoveInterval,this);
		Log.i(TAG,locman.toString());
		
		//Add controls to map
		mapView = (MapView) findViewById(R.id.mv2);
		mapView.setSatellite(false);
		mapView.setTraffic(false);
		mapView.setBuiltInZoomControls(true);
		int maxZoom = mapView.getMaxZoomLevel();
		int initZoom = (int)(0.90*(double)maxZoom);
		mapControl = mapView.getController();
		mapControl.setZoom(initZoom);
		
		//Set up compass and dot
		List<Overlay> overlays = mapView.getOverlays();
		myLocationOverlay = new MyMyLocationOverlay(this,mapView);
		overlays.add(myLocationOverlay);
		
		//Set up overlay like above for the sat info!
		//displayOverlay = new DisplayOverlay();
		//mapOverlays = mapView.getOverlays();
		//mapOverlays.add(displayOverlay);
		
		//Show the button here, the other nearby users
		nearbyUsersButton = (Button)findViewById(R.id.doAccess);
		nearbyUsersButton.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				setOverlay1();
			}

			
		});
	
	}
	
	public void setOverlay1() {
		int userLength = userItem.length;
        // Create itemizedOverlay2 if it doesn't exist and display all three items
        if(! usersDisplayed){
        mapOverlays = mapView.getOverlays();	
        usermarker = this.getResources().getDrawable(R.drawable.usermarker); 
        itemizedOverlay1 = new ProfileOverlay(usermarker,mapView); 
        // Display all three items at once
        for(int i=0; i<userLength; i++){
            itemizedOverlay1.addOverlay(userItem[i]);
        }
        mapOverlays.add(itemizedOverlay1);
        usersDisplayed = !usersDisplayed;
        // Remove each item successively with button clicks
        } else {			
            itemizedOverlay1.removeItem(itemizedOverlay1.size()-1);
            if((itemizedOverlay1.size() < 1))  usersDisplayed = false;
        }    
        // Added symbols will be displayed when map is redrawn so force redraw now
        mapView.postInvalidate(); 
		
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	public void onLocationChanged(Location location) {
		centerOnLocation();		
	}

	@Override
	public void onProviderDisabled(String provider) {
		locman.removeUpdates(this);
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		locman.requestLocationUpdates(provider,GPSupdateInterval,GPSmoveInterval,this);		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		centerOnLocation();
		
	}
	
	private void centerOnLocation(){
		loc = locman.getLastKnownLocation(provider);
		if(loc != null){
			lat = loc.getLatitude();
			lon = loc.getLongitude();
			GeoPoint newPoint = new GeoPoint((int)(lat*1e6),(int)(lon*1e6));
			mapControl.animateTo(newPoint);
			getSatelliteData();
			
			//Push the data into the sat overlay!
			//if(displayOverlay != null){
				//displayOverlay.putSatStuff(lat,lon,satAccuracy,bearing,altitude,speed,currentProvider,numberSats);
				
			//}
		}

	}
	
	private void getSatelliteData(){
		if(loc != null){
			
			//Num of satellites used for the fix
			locBundle = loc.getExtras();
			if(locBundle != null){
				numberSats = locBundle.getInt("satellites",-1);
				
			}
			
			satAccuracy = loc.getAccuracy();
			bearing = loc.getBearing();
			altitude = loc.getAltitude();
			speed = loc.getSpeed();
		}
	}
	
	public void onPause(){
		super.onPause();
		Log.i(TAG,"***** Map App is pausing: removing gps update requests to save power");
		myLocationOverlay.disableCompass();
		myLocationOverlay.disableMyLocation();
		locman.removeUpdates(this);
	}
	
	public void onResume(){
		super.onResume();
		Log.i(TAG,"**** Map App is restarting: resuming gps");
		locman.requestLocationUpdates(provider,GPSupdateInterval,GPSmoveInterval,this);
		myLocationOverlay.enableCompass();
		myLocationOverlay.enableMyLocation();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
