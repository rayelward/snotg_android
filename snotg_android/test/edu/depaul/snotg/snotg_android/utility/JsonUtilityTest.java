package edu.depaul.snotg.snotg_android.utility;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;


public class JsonUtilityTest {


    

	public static void main (String [] args) {
    	List<NameValuePair> qparams = new ArrayList<NameValuePair>();
    	qparams.add(new BasicNameValuePair("user_heartbeat", "true"));
    	qparams.add(new BasicNameValuePair("json_req", "THIS IS JSON TEXT"));
    	URI uri = null;
			try {
				uri = URIUtils.createURI("http", "10.0.2.2", 8888, "/user_locations", 
					    URLEncodedUtils.format(qparams, "UTF-8"), null);
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		System.out.println(URLEncodedUtils.format(qparams, "UTF-8"));
	}

}

