package edu.depaul.snotg_android.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;

import static edu.depaul.snotg_android.SnotgAndroidConstants.*;

public class JsonUtility {

	/**
	 * 
	 * @param jsonText
	 * @param url - this is path part of a url as with a RESTful service.  This is used
	 * with the apache's api URIUtils.createURI(...)
	 * @return String response
	 */
    public static String sendRequest(String uriPath, ArrayList<ArrayList<String>> pairs)
    {
    	DefaultHttpClient httpClient = new DefaultHttpClient();
    	List<NameValuePair> qparams = buildQueryStringParamList(pairs);

    	URI uri = null;
    	String sUri = null;
    	//TODO Fix URI connection refused exception
		/*
		 * URI began getting refused connection.  Was working, but stopped and couldn't figure out why
		 * TEMP just to piece togeter following url:   http://10.0.2.2:8888/user_locations?get_user_locs
		try {
			uri = URIUtils.createURI(URI_PROTOCOL, URI_BACKEND_HOSTNAME, URI_BACKEND_PORT, uriPath, 
				    URLEncodedUtils.format(qparams, "UTF-8"), null);
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		StringBuilder sb = new StringBuilder();
		sb.append(URI_PROTOCOL).append("://").append(URI_BACKEND_HOSTNAME).append(":").append(URI_BACKEND_PORT).append(URI_PATH_HEARTBEAT);
		sb.append("?").append(URLEncodedUtils.format(qparams, "UTF-8"));

		HttpGet request = new HttpGet(sb.toString());
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
       
	
	/**
	 * List of name-value pairs where pairs are in a second list, 
	 * where i=0 is the name and i=1 is the value 
	 * @return
	 */
	public static List<NameValuePair> buildQueryStringParamList(ArrayList<ArrayList<String>> pairs) {
		ArrayList<NameValuePair> qparams = new ArrayList<NameValuePair>();
		
		for (ArrayList<String> pair : pairs) {
			qparams.add(new BasicNameValuePair(pair.get(0), pair.get(1)));
		}
		return qparams;
	}
}
