package edu.depaul.snotg_android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class AndroidMapActivity extends Activity implements OnClickListener{
	
	public double lat;
	public double lon;
	
	Context context;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmap);
        
        //Just setting the context for the toasting info for development
        context = getApplicationContext();
        
		//Toast just for dev information
		String textdev = "Push the Track Present Location - Garry";
		int duration = Toast.LENGTH_LONG;
		Toast toast = Toast.makeText(context, textdev, duration);
		toast.show();
        
        //Click listeners for the buttons
        View firstButton = findViewById(R.id.geocode_button);
        firstButton.setOnClickListener(this);
        View secondButton = findViewById(R.id.latlong_button);
        secondButton.setOnClickListener(this);
        View thirdButton = findViewById(R.id.presentLocation_button);
        thirdButton.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v){
    	
    	switch(v.getId()){
    	case R.id.geocode_button:
    		Log.i("Button","Button 1 Pushed");
    		Intent j = new Intent(this, ShowTheMap.class);
    		startActivity(j);
    	break;
    	
    	case R.id.latlong_button:
    		
    		//Read the lat and lon from the fields
    		EditText latText = (EditText) findViewById(R.id.lat_input);
    		EditText lonText = (EditText) findViewById(R.id.lon_input);
    		String latString = latText.getText().toString();
    		String lonString = lonText.getText().toString();
    		// Check to see if both entries aren't null
    		if(latString.compareTo("") != 0 && lonString.compareTo("") != 0){
    			lat = Double.parseDouble(latString);
    			lon = Double.parseDouble(lonString);
    			Log.i("Button","Button 2 Pushed");
        		Intent k = new Intent(this, ShowTheMap.class);
        		ShowTheMap.putLatLong(lat, lon);
        		startActivity(k);
    		}    		
    	break;
    	
    	case R.id.presentLocation_button:
    		Log.i("Button","Button 3 pushed");
    		Intent m = new Intent(this, MapMe.class);
    		startActivity(m);
    	break;
    	}
    }
}