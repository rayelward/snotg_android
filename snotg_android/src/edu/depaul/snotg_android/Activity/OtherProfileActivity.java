package edu.depaul.snotg_android.Activity;

import edu.depaul.snotg_android.R;
import edu.depaul.snotg_android.Chat.XMPPClient;
import edu.depaul.snotg_android.Profile.UserProfile;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class OtherProfileActivity extends Activity {
	
	private static final String TAG = "OtherProfileActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.other_profile);
		
		Bundle b = getIntent().getExtras();
		UserProfile p = (UserProfile) b.get("profile");
		
		TextView shoutText = (TextView) findViewById( R.id.profile_otherShoutText );
		shoutText.setText(p.getShout());
		
		TextView descriptionText = (TextView) findViewById( R.id.profile_otherDescriptionText );
		descriptionText.setText(p.getDescription());		
		
		View chatButton = findViewById( R.id.profile_otherChatButton );
		chatButton.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i( TAG, "clicked chat button" );
				Intent chat = new Intent( getBaseContext(), XMPPClient.class);
				//Intent chat = new Intent(this, XMPPClient.class);	
				startActivity(chat);				
			}
		});
		
	}

	@Override
	protected void onStart() {
		super.onStart();
	}
	
	
	
	

}
