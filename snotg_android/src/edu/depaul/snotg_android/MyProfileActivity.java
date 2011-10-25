package edu.depaul.snotg_android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;

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
		Log.i(INPUT_METHOD_SERVICE, "Michalak Button clicked");
		//Toast.makeText(this, "Btn Clicked.  Looking up data....",Toast.LENGTH_SHORT).show();
		 
		/*LinearLayout ll = new LinearLayout(this);
		tv = new TextView(this);*/
        
        Intent intentIn = getIntent();
        //Long id = Long.parseLong(intentIn.getStringExtra("notesid")); to get a param
        String user = intentIn.getStringExtra("name");
        //TODO this url must be externalized in order to migrate environments
        String url = "http://snotgdepaul1.appspot.com/user";
        String profile = getData(user, url);
        /*tv.setText(answer);
        ll.addView(tv);
        ll.setOrientation(LinearLayout.VERTICAL);
        setContentView(ll);*/
        TextView t=(TextView)findViewById(R.id.GAE_ping_return); 
        Log.i("GAE", profile);
        t.setText(profile);
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
		catch (ClientProtocolException e) 
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}//catch
		
    	return result.toString();
    }
}
