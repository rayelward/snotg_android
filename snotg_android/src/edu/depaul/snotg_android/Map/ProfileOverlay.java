package edu.depaul.snotg_android.Map;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

import edu.depaul.snotg_android.Activity.MyProfileActivity;
import edu.depaul.snotg_android.Activity.Snotg_androidActivity;


public class ProfileOverlay extends BalloonItemizedOverlay<OverlayItem>{
	
	private ArrayList<OverlayItem> profileList = new ArrayList<OverlayItem>();
	private Context c;
	

	//Constructor for the ProfileOverlay class
	public ProfileOverlay(Drawable image, MapView mapView) {
		super(boundCenter(image), mapView);
		c = mapView.getContext();
	}
	
	public void drawTop(Drawable image){
		return;
	}
	
	public void addOverlay(OverlayItem overlay){
		profileList.add(overlay);
		populate();
	}

	//Creates an item into the profileList Overlay array
	@Override
	protected OverlayItem createItem(int i) {
		return profileList.get(i);
	}

	//This returns the size of the profileList Overlays
	@Override
	public int size() {
		return profileList.size();
	}
	
	//remove an item if necessary
	public void removeItem(int i){
		profileList.remove(i);
		populate();
	}
	
	@Override
	protected boolean onBalloonTap(int index, OverlayItem item) {
		Toast.makeText(c, "Retrieving profile for index " + index,
				Toast.LENGTH_LONG).show();
		Intent m = new Intent(c,MyProfileActivity.class);
		c.startActivity(m);
		return true;
	}
	
	
}
