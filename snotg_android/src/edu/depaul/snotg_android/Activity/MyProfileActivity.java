package edu.depaul.snotg_android.Activity;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import edu.depaul.snotg_android.Profile.UserProfile;
import edu.depaul.snotg_android.Profile.UserProfileDbHelper;
//import edu.depaul.snotg_android.R.id;
//import edu.depaul.snotg_android.R.layout;

import android.app.Activity;
//import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
//import android.widget.ArrayAdapter;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.Spinner;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
//import android.widget.Toast;
import edu.depaul.snotg_android.Chat.XMPPClient;

public class MyProfileActivity  extends Activity {

	private static final String TAG = "MyProfileActivity";
	private static final int TAKE_PIC = 7453912;
	
	private UserProfileDbHelper dbHelper;
	private UserProfile currentProfile;
	private EditText description;
	private EditText shout;
	private TextView name;
	private ImageView avatarImage;
	private Uri picFileUri;
	private Button pingButton;
	private Button editButton;
	private Button submitButton;
	private Button cancelButton;
	private Button chatButton;
	private Button otherButton;

	private static Uri getOutputImageFileUri() {
	      return Uri.fromFile( getOutputImageFile() );
	}
	
	private static File getOutputImageFile() {
		String mediaState = Environment.getExternalStorageState();
		if( mediaState.equals(Environment.MEDIA_MOUNTED) ) {
			Log.i(TAG, "media mounted");
		} else if( mediaState.equals(Environment.MEDIA_SHARED) ) {
			Log.i(TAG, "media shared");
		} else {
			Log.i(TAG, "media state: " + mediaState );
		}
	    File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES ), "snotg");
	    if (! mediaStorageDir.exists()){
	        if (! mediaStorageDir.mkdirs() ){
	            Log.d( TAG, "failed to create directory: " + mediaStorageDir);
	            return null;
	        }
	    }
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format( new Date() );
	    File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_"+ timeStamp + ".jpg");
	    return mediaFile;
	}


	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
        setContentView( R.layout.my_profile );  
        populateFields();        
        populateProfile();        
        setListeners();

	}	

	@Override
	protected void onDestroy() {
		super.onDestroy();
		dbHelper.closeItUp();
	} 
	
	private void populateFields() {
		dbHelper = new UserProfileDbHelper( getBaseContext() );
		description = (EditText) findViewById( R.id.profile_descText );
		shout = (EditText) findViewById( R.id.profile_shoutText );
		name = (TextView) findViewById( R.id.profile_nameText );
		avatarImage = (ImageView) findViewById( R.id.profile_avatarImage );
		editButton = (Button) findViewById( R.id.profile_editButton );
		cancelButton = (Button) findViewById( R.id.profile_cancelButton );
		submitButton = (Button) findViewById( R.id.profile_submitButton );
		pingButton = (Button) findViewById (R.id.profile_pingButton );
		chatButton = (Button) findViewById( R.id.profile_chatButton );
		otherButton = (Button) findViewById( R.id.profile_otherProfileButton );
	}
	
	private void setListeners() {
        avatarImage.setOnClickListener(new AvatarImageOnClickListener() );      			
		editButton.setOnClickListener( new EditButtonOnClickListener() );			
		cancelButton.setOnClickListener( new CancelButtonOnClickListener() );			
		submitButton.setOnClickListener( new SubmitButtonOnClickListener() );
		pingButton.setOnClickListener( new PingButtonOnClickListener() );
		chatButton.setOnClickListener( new ChatButtonOnClickListener() );		
		otherButton.setOnClickListener( new OtherButtonOnClickListener() );
	}
	
	private void populateProfile() {
		Log.i( TAG, "populating profile" );
		currentProfile = fetchLocalProfile();
		if( currentProfile == null ) {
			currentProfile = fetchRemoteProfile();
			if( currentProfile == null ) {
				currentProfile = new UserProfile();
				setHints();
			}
		} 	
	}
	
	private UserProfile fetchLocalProfile() {
		Log.i( TAG, "fetching local profile" );
		UserProfile[] profiles = dbHelper.getUserProfiles();
		Log.i( TAG, "# profiles in local DB: " + profiles.length );
		if( profiles.length==0 ) {
			return null;		
		} else {
			UserProfile p = profiles[0];			
			description.setText( p.getDescription() );			
			shout.setText( p.getShout() );
			name.setText( p.getProfileName() );
			avatarImage.setImageBitmap( BitmapFactory.decodeByteArray( p.getAvatar(), 0, p.getAvatar().length) );
			return p;
		}
	}
	
	private UserProfile fetchRemoteProfile() {
		Log.i( TAG, "fetching remote profile");
		//TODO: implement this method
		return null;		
	}
	
	private void setHints() {
		description.setHint( R.string.profile_descriptionHint );
		shout.setHint( R.string.profile_shoutHint );
		name.setHint( R.string.profile_nameHint );	
	}
	
	private void revertAnyChanges() {
		description.setText(currentProfile.getDescription());
		name.setText(currentProfile.getProfileName());
		shout.setText(currentProfile.getShout());
	}
	
	private void setEditMode( boolean editable ) {
		avatarImage.setEnabled( editable );
		shout.setEnabled( editable );
		description.setEnabled( editable );	
		editButton.setEnabled( !editable );
		cancelButton.setEnabled( editable );
		submitButton.setEnabled( editable );
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.i(TAG, "requestCode: " + requestCode + " --- resultCode: " + resultCode);
		Log.i(TAG, "picFileUri: " + picFileUri );
		if( data != null ) {
			Log.i(TAG, " --- Intent: " + data.getDataString() );
		} else {
			Log.i(TAG, "Intent is null");
		}
		if( requestCode==TAKE_PIC ) {
			if( resultCode==RESULT_OK ) {
				//Log.i(TAG, "data.toURI(): " + data.toURI());
				//Log.i(TAG, "data.getData(): " + data.getData());
				avatarImage.setImageURI( picFileUri );
			} else if( resultCode==RESULT_CANCELED ) {
				Log.i( TAG, "user cancelled request to capture image" );
			}
		}
	}

	private boolean retrieveProfile() {
		//TODO: get this from phone context???
		String userName = "jeffrey.w.anderson@gmail.com";
		String userKey = "12345";
		String cmd = "retrieveProfile";
		String url = this.getString( R.string.profile_retrieve_url ) + "userKey=" + userKey + "&cmd=" + cmd + "&userName=" + userName;
		String retrieveProfileResponse = getData( userKey, url );
		Log.i( TAG, retrieveProfileResponse );
		try {
			UserProfile up = new UserProfile( retrieveProfileResponse );
			Log.i( TAG, up.toString() );
		} catch( JSONException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private boolean updateProfile() {
		currentProfile.setDescription( description.getText().toString() );
		currentProfile.setProfileName( name.getText().toString() );
		currentProfile.setShout( shout.getText().toString() );
		currentProfile.setAvatar( getAvatarImageAsByteArray() );
		Log.i(TAG, currentProfile.toString());
		if( currentProfile.getUserKey() == 0 ) {
			dbHelper.createUserProfile( currentProfile );
		} else {
			dbHelper.updateUserProfile( currentProfile );
		}		
		//TODO: get this from phone context???
//		String userName = "jeffrey.w.anderson@gmail.com";
//		String userKey = "12345";
//		String cmd = "updateProfile";
//		String url = this.getString( R.string.profile_retrieve_url ) + "userKey=" + userKey + "&cmd=" + cmd + "&userName=" + userName;
//		String updateProfileResponse = getData( userKey, url );
//		Log.i( TAG, updateProfileResponse );
//		try {
//			UserProfile up = new UserProfile( updateProfileResponse );
//			Log.i( TAG, up.toString() );
//		} catch( JSONException e) {
//			e.printStackTrace();
//		}
		return true;
	}
	
	public byte[] getAvatarImageAsByteArray() {
		Bitmap bitmap = ((BitmapDrawable) avatarImage.getDrawable()).getBitmap();
		return getByteArray( bitmap );	
	}
	
	public byte[] getByteArray(Bitmap bitmap) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, 0, bos);
		return bos.toByteArray();
	}
	
	public Bitmap getBitmap(byte[] bitmap) {
	    return BitmapFactory.decodeByteArray(bitmap , 0, bitmap.length);
	}

	
	private class AvatarImageOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			Log.i(TAG, "clicked avatar image");
			if( avatarImage.isEnabled() ) {
				picFileUri = getOutputImageFileUri();
				Intent captureImage = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
				captureImage.putExtra( MediaStore.EXTRA_OUTPUT, picFileUri );
				startActivityForResult( captureImage, TAKE_PIC );
			}
		} 
	}
	
	private class EditButtonOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			Log.i( TAG, "clicked edit button" );
			setEditMode( true );				
		}
	}
	
	private class CancelButtonOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			Log.i( TAG, "clicked cancel button" );
			setEditMode( false );
			revertAnyChanges();
		}
	}
	
	
	private class SubmitButtonOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			Log.i( TAG, "clicked submit button" );
			setEditMode( false );	
			boolean profileUpdateSuccessful = updateProfile();
			Log.i( TAG, "profile update successful? " + profileUpdateSuccessful);			
		}
	}
	
	private class PingButtonOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			Log.i(TAG, "clicked ping button");        
	        Intent intentIn = getIntent();
	        String user = intentIn.getStringExtra("name");
	        String url = MyProfileActivity.this.getString( R.string.url );       
	        String profile = getData(user, url); 
	        TextView t=(TextView)findViewById( R.id.profile_pingText ); 
	        Log.d(TAG, profile);
	        HashMap<String, String> ret = readJsonReturnObj(profile);
	        t.setText(ret.get("msg").toString() + ret.get("ts").toString());
		}
	}
	
	private class ChatButtonOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			Log.i( TAG, "clicked chat button" );
			Intent chat = new Intent( getBaseContext(), XMPPClient.class);
			startActivity(chat);				
		}
	}
	
	private class OtherButtonOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			Log.i( TAG, "clicked other button" );
			Intent other = new Intent( getBaseContext(), OtherProfileActivity.class );
			UserProfile up = new UserProfile();
			up.setShout( "Help me with my SE450 homework!" );
			up.setDescription( "I'm a grad student at DePaul in the school of Computing and Digital Media" );
			Bundle b = new Bundle();
			b.putSerializable( "profile", up );
			other.putExtras( b );
			startActivity( other );				
		}
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
