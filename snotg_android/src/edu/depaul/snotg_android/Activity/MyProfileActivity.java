package edu.depaul.snotg_android.Activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.depaul.snotg_android.Profile.UserProfile;
//import edu.depaul.snotg_android.R.id;
//import edu.depaul.snotg_android.R.layout;

import android.app.Activity;
//import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
//import android.widget.ArrayAdapter;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.Spinner;
import android.widget.TextView;
//import android.widget.Toast;

public class MyProfileActivity  extends Activity  implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);
     
        View pingButton = findViewById (R.id.profile_pingButton );
		pingButton.setOnClickListener(this);
		
		View editButton = findViewById( R.id.profile_editButton );
		editButton.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i( "MyProfileActivity", "clicked edit button" );
				setEditMode( true );				
			}			
		});
		
		View cancelButton = findViewById( R.id.profile_cancelButton );
		cancelButton.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i( "MyProfileActivity", "clicked cancel button" );
				setEditMode( false );				
			}				
		});
		
		View submitButton = findViewById( R.id.profile_submitButton );
		submitButton.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i( "MyProfileActivity", "clicked submit button" );
				setEditMode( false );	
				boolean profileUpdateSuccessful = updateProfile();
				Log.i( "MyProfileActivity", "profile update successful? " + profileUpdateSuccessful);
			}				
		});
	}
	
	private void setEditMode( boolean editable ) {
		View shout = findViewById( R.id.profile_shoutText );
		shout.setEnabled( editable );
		View desc = findViewById( R.id.profile_descText );
		desc.setEnabled( editable );	
		View edit = findViewById( R.id.profile_editButton );
		edit.setVisibility( editable ? View.INVISIBLE : View.VISIBLE);
		View cancel = findViewById( R.id.profile_cancelButton );
		cancel.setVisibility( editable ? View.VISIBLE : View.INVISIBLE );
		View submit = findViewById( R.id.profile_submitButton );
		submit.setVisibility( editable ? View.VISIBLE : View.INVISIBLE );
	}
	
	private boolean retrieveProfile() {
		//TODO: get this from phone context???
		String userName = "jeffrey.w.anderson@gmail.com";
		String userKey = "12345";
		String cmd = "retrieveProfile";
		String url = this.getString( R.string.profile_url ) + "userKey=" + userKey + "&cmd=" + cmd + "&userName=" + userName;
		String retrieveProfileResponse = getData( userKey, url );
		Log.i( "MyProfileActivity", retrieveProfileResponse );
		try {
			UserProfile up = new UserProfile( retrieveProfileResponse );
			Log.i( "MyProfileActivity", up.toString() );
		} catch( JSONException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private boolean updateProfile() {
		//TODO: get this from phone context???
		String userName = "jeffrey.w.anderson@gmail.com";
		String userKey = "12345";
		String cmd = "updateProfile";
		String url = this.getString( R.string.profile_url ) + "userKey=" + userKey + "&cmd=" + cmd + "&userName=" + userName;
		String updateProfileResponse = getData( userKey, url );
		Log.i( "MyProfileActivity", updateProfileResponse );
		try {
			UserProfile up = new UserProfile( updateProfileResponse );
			Log.i( "MyProfileActivity", up.toString() );
		} catch( JSONException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		//Context context = getApplicationContext();
		Log.i("MyProfileActivity", "Michalak Button clicked"); 
		//Toast.makeText(this, "Btn Clicked.  Looking up data....",Toast.LENGTH_SHORT).show();		 
		/*LinearLayout ll = new LinearLayout(this);
		tv = new TextView(this);*/        
        Intent intentIn = getIntent();
        //Long id = Long.parseLong(intentIn.getStringExtra("notesid")); to get a param
        String user = intentIn.getStringExtra("name");
        String url = this.getString( R.string.url );       
        String profile = getData(user, url); 
        TextView t=(TextView)findViewById( R.id.profile_pingText ); 
        Log.d("MyProfileActivity", profile);
        HashMap<String, String> ret = readJsonReturnObj(profile);
        t.setText(ret.get("msg").toString() + ret.get("ts").toString());
	}
	
	public void writeJson() {
		JSONObject object = new JSONObject();
		try {
			object.put("name", "Jack Hack");
			object.put("score", new Integer(200));
			object.put("current", new Double(152.32));
			object.put("nickname", "Hacker");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Log.i("JSON", object.toString());
	}
	
    public String getData(String user, String url) {
    	DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		StringBuilder result = new StringBuilder();		
		try {
			HttpResponse response = httpClient.execute(request,new BasicHttpContext());
			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line;
			while ((line = br.readLine()) != null) {
				result.append(line);
			}
		} 
		catch (ClientProtocolException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}		
    	return result.toString();
    }
    
    public HashMap<String, String> readJsonReturnArray(String jsonReturn) {
    	HashMap<String, String> ret = new HashMap<String, String>();
    	try {
			JSONArray jsonArray = new JSONArray(jsonReturn);
			Log.i(MyProfileActivity.class.getName(), "Number of entries " + jsonArray.length());
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				ret.put("msg", (String)jsonObject.get("msg"));
				ret.put("ts", (String)jsonObject.get("ts"));
				//Log.i(MyProfileActivity.class.getName(), jsonObject.getString("date"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}   
		return ret;
	}
    
    public HashMap<String, String> readJsonReturnObj(String jsonReturn) {
    	HashMap<String, String> ret = new HashMap<String, String>();
    	try {
    		JSONObject jsonObj = new JSONObject(jsonReturn);
			Log.i(MyProfileActivity.class.getName(), "Number of entries " + jsonObj.length());
			ret.put("msg", (String)jsonObj.get("msg"));
			ret.put("ts", (String)jsonObj.get("ts"));
			//Log.i(MyProfileActivity.class.getName(), jsonObject.getString("date"));
		} catch (Exception e) {
			e.printStackTrace();
		}   
		return ret;
	}

}
=======
package edu.depaul.snotg_android.Activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.depaul.snotg_android.R;
import edu.depaul.snotg_android.R.array;
import edu.depaul.snotg_android.R.id;
import edu.depaul.snotg_android.R.layout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MyProfileActivity  extends Activity  implements OnClickListener {

	private TextView tv;
	
    private static final String[] Users = new String[]{
    	"On-line", "Off-line"  };
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);
        ImageView image = (ImageView) findViewById(R.id.avatar_image);
       
        Spinner spinner = (Spinner) findViewById(R.id.drop_status);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.arr_online_status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        
        View mapButton = findViewById(R.id.ProfileTest);
		mapButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Context context = getApplicationContext();
		Log.i("MyProfileActivity", "Michalak Button clicked");
		//Toast.makeText(this, "Btn Clicked.  Looking up data....",Toast.LENGTH_SHORT).show();
		 
		/*LinearLayout ll = new LinearLayout(this);
		tv = new TextView(this);*/
        
        Intent intentIn = getIntent();
        //Long id = Long.parseLong(intentIn.getStringExtra("notesid")); to get a param
        String user = intentIn.getStringExtra("name");
        //TODO this url must be externalized in order to migrate environments
        String url = "http://10.0.2.2:8888/user";
        String profile = getData(user, url);
 
        TextView t=(TextView)findViewById(R.id.GAE_ping_return); 
        Log.d("MyProfileActivity", profile);
        HashMap<String, String> ret = readJsonReturnObj(profile);
        t.setText(ret.get("msg").toString() + ret.get("ts").toString());
        String message = ret.get("msg").toString();
        String ts = ret.get("ts").toString();
        Log.i("Return Info:", message);
        Log.i("Return TS: ", ts);
	}
	
	public void writeJson() {
		JSONObject object = new JSONObject();
		try {
			object.put("name", "Jack Hack");
			object.put("score", new Integer(200));
			object.put("current", new Double(152.32));
			object.put("nickname", "Hacker");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Log.i("JSON", object.toString());
	}
	
    public String getData(String user, String url)
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
				ret.put("msg", (String)jsonObject.get("msg"));
				ret.put("ts", (String)jsonObject.get("ts"));
				//Log.i(MyProfileActivity.class.getName(), jsonObject.getString("date"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}   
		return ret;
	}
    
    public HashMap readJsonReturnObj(String jsonReturn)
    {
    	HashMap<String, String> ret = new HashMap<String, String>();
    	try {
    		JSONObject jsonObj = new JSONObject(jsonReturn);
			Log.i(MyProfileActivity.class.getName(), 
					"Number of entries " + jsonObj.length());
			ret.put("msg", (String)jsonObj.get("msg"));
			ret.put("ts", (String)jsonObj.get("ts"));
			//Log.i(MyProfileActivity.class.getName(), jsonObject.getString("date"));
		} catch (Exception e) {
			e.printStackTrace();
		}   
		return ret;
	}

}

