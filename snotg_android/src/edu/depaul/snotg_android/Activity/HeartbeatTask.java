package edu.depaul.snotg_android.Activity;

import static edu.depaul.snotg_android.SnotgAndroidConstants.EMPTY_JSON_STRING;
import static edu.depaul.snotg_android.SnotgAndroidConstants.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;
import edu.depaul.snotg_android.SnotgAndroidConstants;
import edu.depaul.snotg_android.json.HeartbeatUpdateJson;
import edu.depaul.snotg_android.util.JsonUtility;

public class HeartbeatTask extends AsyncTask<URL, Integer, Long> {
	
	private static String jsonUserLocation = null;
	
	@Override
	protected Long doInBackground(URL... params) {
		//Log.i("Heartbeat", "In Heartbeat....");
		double lat = 0d;
		double lon = 0d;
		
		while (true) {
			lat = edu.depaul.snotg_android.Map.MapMe.getLatitude();
			lon = edu.depaul.snotg_android.Map.MapMe.getLongitude();
			HeartbeatUpdateJson json = new HeartbeatUpdateJson(lat, lon, STATE_USERNAME);
			jsonUserLocation = JsonUtility.sendRequest(URI_PATH_HEARTBEAT, getQueryParams(json));
			Log.i("Heartbeat", new Date().toString() + ", Latitude: " + lat + ", Longitude: " + lon + " for:" + STATE_USERNAME + " --- Returned:  " + jsonUserLocation);

			try {
				Thread.sleep(HEARTBEAT_INTERVAL);
			} catch (InterruptedException e) {; } // do nothing
		}
	}
	
	public static String getJSONuserLocation(){
		return jsonUserLocation;
	}
	
	


    protected void onPostExecute(Long result) {
        //showDialog("Downloaded " + result + " bytes");
    }

    
	private String buildJson(HeartbeatUpdateJson json) {
		JSONObject jObj = new JSONObject();
		try {
			jObj.put("userName", json.getUserName());
			jObj.put("latit", json.getLatit());
			jObj.put("longit", json.getLongit());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Log.i("Heartbeat", jObj.toString());
		
		if (jObj != null)
			return jObj.toString();
		else
			return EMPTY_JSON_STRING;
	}
	
	private ArrayList<ArrayList<String>> getQueryParams(HeartbeatUpdateJson json) {
		ArrayList<ArrayList<String>> pairs = new ArrayList<ArrayList<String>>();
		
		ArrayList<String> pair = new ArrayList<String>();
		pair.add(0,"user_heartbeat");
		pair.add(1,"true");
		pairs.add(pair);
		
		pair = new ArrayList<String>();
		pair.add(0,"request_json");
		String jsonText = buildJson(json);
		pair.add(1,jsonText);
		pairs.add(pair);

		return pairs;
	}
}
