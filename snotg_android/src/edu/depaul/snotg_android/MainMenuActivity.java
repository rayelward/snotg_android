package edu.depaul.snotg_android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class MainMenuActivity extends Activity implements OnClickListener {
	Context context;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmenu);

		
		View profileButton = findViewById(R.id.profile_button);
		profileButton.setOnClickListener(this);
		View mapButton = findViewById(R.id.map_button);
		mapButton.setOnClickListener(this);
		View listButton = findViewById(R.id.list_button);
		listButton.setOnClickListener(this);
		View settingsButton = findViewById(R.id.settings_button);
		settingsButton.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		context = getApplicationContext();
		switch (v.getId()) {
		
		case R.id.profile_button:
			startActivity(new Intent(context, MyProfileActivity.class));
			//Toast.makeText(context, "TODO! Profile", Toast.LENGTH_SHORT).show();
			break;
		case R.id.map_button:
			startActivity(new Intent(context, AndroidMapActivity.class));
			break;
		case R.id.list_button:
			//TODO, once list is created this can take you there.
			Toast.makeText(context, "TODO! List", Toast.LENGTH_SHORT).show();
			break;
		case R.id.settings_button:
			//TODO, once settings is created this can take you there.
			Toast.makeText(context, "TODO! Settings", Toast.LENGTH_SHORT).show();
			break;
		}
	}
}
