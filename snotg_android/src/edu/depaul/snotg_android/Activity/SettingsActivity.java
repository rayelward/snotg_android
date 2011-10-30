package edu.depaul.snotg_android.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SettingsActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        
		//Settings Placeholder.
		TextView tv = new TextView(this);
	    tv.setText("This is a Settings placeholder.");
	    setContentView(tv);
	}
}
