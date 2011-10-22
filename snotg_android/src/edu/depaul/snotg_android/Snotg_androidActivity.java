package edu.depaul.snotg_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class Snotg_androidActivity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        //Just getting into my map activity from here with the login click for now
        View loginButton = findViewById(R.id.button1);
        loginButton.setOnClickListener(this);

        
    }

	@Override
	public void onClick(View v) {
		
		//go from profile to the main menu!
		Intent m = new Intent(this, MainMenuActivity.class);
		startActivity(m);		
	}
}