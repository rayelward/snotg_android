package edu.depaul.snotg_android.Profile;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;


public class UserProfile implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long userKey;
    private String profileName;
    private String description;
    private String shout; 
    private byte[] avatar;

    public UserProfile() { 
    	userKey = 0L;
    	profileName = "";
    	description = "";
    	shout = "";
    	avatar = new byte[0];
    }
    
    public UserProfile(String json) throws JSONException {
    	JSONObject obj = new JSONObject( json );
    	JSONObject userProfile = obj.getJSONObject("userProfile");
    	this.shout = userProfile.getString("shout");
    	this.description = userProfile.getString("description");
    	this.profileName = userProfile.getString("profileName");
    	this.userKey = userProfile.getLong("userKey");
    }

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getShout() {
		return shout;
	}

	public void setShout(String status) {
		this.shout = status;
	}

	public Long getUserKey() {
		return userKey;
	}

	public void setUserKey(Long userKey) {
		this.userKey = userKey;
	}
	
	public byte[] getAvatar() {
		return avatar;
	}
	
	public void setAvatar(byte[] avatar) {
		this.avatar = avatar;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("UserProfile[");
		sb.append("\n  userKey: "); sb.append( userKey );
		sb.append("\n  profileName: "); sb.append( profileName );
		sb.append("\n  shout: "); sb.append( shout );
		sb.append("\n  description: "); sb.append( description );
		sb.append("\n  avatar: "); sb.append( "a byte[]" );
		sb.append("\n]");
		return sb.toString();
	}

}
