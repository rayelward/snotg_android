package edu.depaul.snotg_android.Activity;

import static edu.depaul.snotg_android.SnotgAndroidConstants.EMPTY_JSON_STRING;
import static edu.depaul.snotg_android.SnotgAndroidConstants.URL_HEARTBEAT;
import edu.depaul.snotg_android.Map.*;

import java.net.URL;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;
import edu.depaul.snotg_android.util.JsonUtility;

public class HeartbeatTask extends AsyncTask<URL, Integer, Long> {
	
	private double lat = edu.depaul.snotg_android.Map.MapMe.getLatitude();
	private double lon = edu.depaul.snotg_android.Map.MapMe.getLongitude();

	@Override
	protected Long doInBackground(URL... params) {
		Log.i("Heartbeat", "In Heartbeat....");
		
		String jsonText = buildJson();
		String jsonUserLocation = null;
		
		while (true) {
			lat = edu.depaul.snotg_android.Map.MapMe.getLatitude();
			lon = edu.depaul.snotg_android.Map.MapMe.getLongitude();
			//jsonUserLocation = JsonUtility.sendRequest(jsonText, URL_HEARTBEAT);
			Log.i("Heartbeat", new Date().toString() + ", Latitude: " + lat + ", Longitude: " + lon + " --- Returned:  " + jsonUserLocation);
			
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {; } // do nothing
		}
	}


    protected void onPostExecute(Long result) {
        //showDialog("Downloaded " + result + " bytes");
    }

    
	public String buildJson() {
		JSONObject jObj = new JSONObject();
		try {
			jObj.put("userName", "mike");
			jObj.put("latit", lat);
			jObj.put("longit", lon);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Log.i("JSON", jObj.toString());
		
		if (jObj != null)
			return jObj.toString();
		else
			return EMPTY_JSON_STRING;
	}
}
