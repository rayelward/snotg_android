package edu.depaul.snotg_android.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class NearbyListActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        
		//List view Placeholder.
		TextView tv = new TextView(this);
	    tv.setText("This is a List view placeholder.");
	    setContentView(tv);
	}
}
