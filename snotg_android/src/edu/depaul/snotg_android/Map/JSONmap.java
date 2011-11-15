package edu.depaul.snotg_android.Map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;
import edu.depaul.snotg_android.R;
import edu.depaul.snotg_android.Activity.MyProfileActivity;

public class JSONmap {
	
	
	
	public static String getData(String user, String url)
    {
    	DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		StringBuilder result = new StringBuilder();
		
		try 
		{
			HttpResponse response = httpClient.execute(request,new BasicHttpContext());
			HttpEntity entity = response.getEntity();

			InputStream is = entity.getContent();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line;
			while ((line = br.readLine()) != null)
				result.append(line);
		} 
		catch (ClientProtocolException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
    	return result.toString();
    }
    
    public HashMap readJsonReturnArray(String jsonReturn)
    {
    	HashMap<String, String> ret = new HashMap<String, String>();
    	try {
			JSONArray jsonArray = new JSONArray(jsonReturn);
			Log.i(MyProfileActivity.class.getName(),
					"Number of entries " + jsonArray.length());
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				//ret.put("username", (String)jsonObject.get("username"));
				//ret.put("longitude", (Double)jsonObject.get("longitude"));
				//ret.put("lastupdated", (String)jsonObject.get("lastupdated"));
				//ret.put("latitude", (Double)jsonObject.get("longitude"));
				Log.i(MyProfileActivity.class.getName(), jsonObject.getString("date"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}   
		return ret;
	}
    
    public static HashMap readJsonReturnObj(String jsonReturn)
    {
    	HashMap<String,userLocationObj> ret = new HashMap<String,userLocationObj>();
    	try {
    		
    		JSONArray jArray = new JSONArray(jsonReturn);
    		
    		System.out.println("******JARRAY********"+jArray.length());
    		for(int i=0;i<jArray.length();i++){
    			JSONObject json_data = jArray.getJSONObject(i);
    			    
    			userLocationObj user = new userLocationObj();
    			
    			user.setUsername(json_data.getString("username"));
    			user.setLon(json_data.getDouble("longitude"));
    			user.setLat(json_data.getDouble("latitude"));
    			user.setLastUpdated(json_data.getString("lastupdated"));
    			
    			//ret.put("ID", i);
    			ret.put(json_data.getString("key"),user);
    			Log.i("Log_tag",json_data.getString("username"));
    		}
    		
    		Set set = ret.entrySet();
    		Iterator i = set.iterator();
    		System.out.println("Size of Location Array: " + ret.size());
    		while(i.hasNext()){
    			Map.Entry me = (Map.Entry)i.next();
    			System.out.print(me.getKey() + ": ");
    			System.out.println(me.getValue());
    		}
    		
		} catch (Exception e) {
			e.printStackTrace();
		}   
		return ret;
	}
    
    
}
