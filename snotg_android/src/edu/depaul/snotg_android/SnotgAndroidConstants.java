package edu.depaul.snotg_android;

public class SnotgAndroidConstants {

	public static final long HEARTBEAT_INTERVAL = 60 * 1000; // sec * 1000
	
	// These are used to create an apache http client request.  See JsonUtility
	public static final String URI_BACKEND_HOSTNAME = "se491snotg-1.appspot.com";
	public static final int URI_BACKEND_PORT = 80;
	public static final String URI_PROTOCOL = "http";
	public static final String URI_PATH_HEARTBEAT = "/user_locations";
	
	
	public static final String EMPTY_JSON_STRING = "[]";

	// State Variables used be the app to hold user state
	public static String STATE_USERNAME = "";
}