package edu.depaul.snotg_android.Map;

import android.content.Context;

import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

public class MyMyLocationOverlay extends MyLocationOverlay {
		
	private Context context;
	
	public MyMyLocationOverlay(Context context, MapView mapView) {
		super(context, mapView);
		this.context = context;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean dispatchTap(){
		return true;
	}

}
