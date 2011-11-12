package edu.depaul.snotg_android.Activity;

import static edu.depaul.snotg_android.SnotgAndroidConstants.EMPTY_JSON_STRING;
import static edu.depaul.snotg_android.SnotgAndroidConstants.URL_HEARTBEAT;

import java.net.URL;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;
import edu.depaul.snotg_android.util.JsonUtility;

public class HeartbeatTask extends AsyncTask<URL, Integer, Long> {

	@Override
	protected Long doInBackground(URL... params) {
		Log.i("Heartbeat", "In Heartbeat....");
		
		String jsonText = buildJson();
		String jsonUserLocation = null;
		while (true) {
			jsonUserLocation = JsonUtility.sendRequest(jsonText, URL_HEARTBEAT);
			Log.i("Heartbeat", new Date().toString() + " --- Returned:  " + jsonUserLocation);
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
			jObj.put("latit", 55.1234);
			jObj.put("longit", -90.6543);
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
