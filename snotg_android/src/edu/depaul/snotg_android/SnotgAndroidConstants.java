<<<<<<< HEAD
package edu.depaul.snotg_android;

public class SnotgAndroidConstants {

	public static final long HEARTBEAT_INTERVAL = 10 * 1000; // sec * 1000
	
	// These are used to create an apache http client request.  See JsonUtility
	public static final String URI_BACKEND_HOSTNAME = "10.0.2.2";
	public static final int URI_BACKEND_PORT = 8888;
	public static final String URI_PROTOCOL = "http";
	public static final String URI_PATH_HEARTBEAT = "/user_locations";
	
	
	public static final String EMPTY_JSON_STRING = "[]";

}
=======
package edu.depaul.snotg_android;

public class SnotgAndroidConstants {

	public static final long HEARTBEAT_INTERVAL = 10 * 1000; // sec * 1000
	
	//
	//http://se491snotg.appspot.com = our online site.
	// These are used to create an apache http client request.  See JsonUtility
	public static final String URI_BACKEND_HOSTNAME = "se491snotg.appspot.com";
	public static final int URI_BACKEND_PORT = 80;
	public static final String URI_PROTOCOL = "http";
	public static final String URI_PATH_HEARTBEAT = "/user_locations";
	
	
	public static final String EMPTY_JSON_STRING = "[]";

}
>>>>>>> Changed heartbeat to talk to gas instead of local host.  Set main chat client user coded in as se491snotg.
