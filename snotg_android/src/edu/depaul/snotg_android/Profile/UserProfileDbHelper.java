package edu.depaul.snotg_android.Profile;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UserProfileDbHelper extends SQLiteOpenHelper {
	
	private static final String TAG = UserProfileDbHelper.class.getSimpleName();
	private static final String PROFILE_TABLE_NAME = "PROFILE";

	public UserProfileDbHelper(Context context) {
		super(context, PROFILE_TABLE_NAME, null, 1);
	}
	
	public UserProfile[] getUserProfiles() {
		String getProfilesSql = "SELECT _id, name, description, shout FROM " + PROFILE_TABLE_NAME;
		SQLiteDatabase db = getReadableDatabase();
		Cursor c = db.rawQuery(getProfilesSql, null);
		int numProfiles = c.getCount();
		UserProfile[] profiles = new UserProfile[numProfiles];	
		for( int i=0; i<numProfiles; i++) {
			UserProfile p = new UserProfile();
			c.moveToPosition(i);
			p.setUserKey(c.getLong(0));
			p.setProfileName(c.getString(1));
			p.setDescription(c.getString(2));
			p.setShout(c.getString(3));
			profiles[i] = p;
		}
		c.close();
		db.close();
		return profiles;
	}
	
	public void createUserProfile(UserProfile p) {
		String insertProfileSql = "INSERT INTO " + PROFILE_TABLE_NAME + "(name, description, shout) VALUES(?,?,?)";
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL(insertProfileSql, new Object[]{ p.getProfileName(), p.getDescription(), p.getShout()});
	}
	
	public void updateUserProfile(UserProfile p) {
		String updateProfileSql = "UPDATE " + PROFILE_TABLE_NAME + " SET name=?, description=?, shout=? WHERE _id=?";
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL(updateProfileSql, new Object[]{ p.getProfileName(), p.getDescription(), p.getShout(), p.getUserKey()});
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i(TAG, "Creating profile DB");
		createProfileTable(db);
	}
	
	private void createProfileTable(SQLiteDatabase db) {
		String createSql = "CREATE TABLE " + PROFILE_TABLE_NAME + "(" +
							" _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
							" name TEXT, " +
							" description TEXT, " +
							" shout TEXT);";
		db.execSQL(createSql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i(TAG, "Upgrading profile DB");
		dropProfileTable(db);
		createProfileTable(db);
	}
	
	private void dropProfileTable(SQLiteDatabase db) {
		String dropSql = "DROP TABLE IF EXISTS " + PROFILE_TABLE_NAME;
		db.execSQL(dropSql);
	}
	
	public void closeItUp() {
		close();
	}

}
