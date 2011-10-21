package edu.depaul.snotg_android;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class MyUsersOverlay extends ItemizedOverlay {
	
	private ArrayList<OverlayItem> myOverlays;
	
	public MyUsersOverlay(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
		myOverlays = new ArrayList<OverlayItem>();
		populate();
		
	}
	
	public void addOverlay(OverlayItem overlay){
		myOverlays.add(overlay);
		populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return myOverlays.get(i);
	}
	
	//remove an item if necessary
	public void removeItem(int i){
		myOverlays.remove(i);
		populate();
	}
	
	//What do we want to say when the users are clicked on?
	@Override
	protected boolean onTap(int i){
		GeoPoint  gpoint = myOverlays.get(i).getPoint();
        double lat = gpoint.getLatitudeE6()/1e6;
        double lon = gpoint.getLongitudeE6()/1e6;
        String toast = "Name: "+myOverlays.get(i).getTitle();
        toast += "\nStatus: "+myOverlays.get(i).getSnippet();
        Toast.makeText(MapMe.context, toast, Toast.LENGTH_LONG).show();
        return(true);
	}

	@Override
	public int size() {
		return myOverlays.size();
	}

}
