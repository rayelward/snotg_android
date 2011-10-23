package edu.depaul.snotg_android;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

public class MyProfileActivity  extends Activity {

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
	}

}
