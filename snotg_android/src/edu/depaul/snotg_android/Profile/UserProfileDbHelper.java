package edu.depaul.snotg_android.Profile;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UserProfileDbHelper extends SQLiteOpenHelper {
	
	private static final String TAG = UserProfileDbHelper.class.getSimpleName();
	private static final String PROFILE_TABLE_NAME = "PROFILE";
	private static final int VERSION = 2;
	private static final String createProfileTableSql = "CREATE TABLE " + PROFILE_TABLE_NAME + "( _id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, description TEXT, shout TEXT, avatar BLOB );";
	private static final String insertProfileSql = "INSERT INTO " + PROFILE_TABLE_NAME + "(name, description, shout, avatar) VALUES(?,?,?,?)";
	private static final String getProfilesSql = "SELECT _id, name, description, shout, avatar FROM " + PROFILE_TABLE_NAME;
	private static final String updateProfileSql = "UPDATE " + PROFILE_TABLE_NAME + " SET name=?, description=?, shout=?, avatar=? WHERE _id=?";
	
	public UserProfileDbHelper( Context context ) {
		super( context, PROFILE_TABLE_NAME, null, VERSION );
	}
	
	public UserProfile[] getUserProfiles() {		
		SQLiteDatabase db = getReadableDatabase();
		Cursor c = db.rawQuery(getProfilesSql, null);
		int numProfiles = c.getCount();
		UserProfile[] profiles = new UserProfile[numProfiles];	
		for( int i=0; i<numProfiles; i++) {
			UserProfile p = new UserProfile();
			c.moveToPosition(i);
			p.setUserKey( c.getLong(0) );
			p.setProfileName( c.getString(1) );
			p.setDescription( c.getString(2) );
			p.setShout( c.getString(3) );
			p.setAvatar( c.getBlob(4) );
			profiles[i] = p;
		}
		c.close();
		db.close();
		return profiles;
	}
	
	public void createUserProfile(UserProfile p) {		
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL(insertProfileSql, new Object[]{ p.getProfileName(), p.getDescription(), p.getShout(), p.getAvatar() } );
	}
	
	public void updateUserProfile(UserProfile p) {
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL( updateProfileSql, new Object[]{ p.getProfileName(), p.getDescription(), p.getShout(), p.getAvatar(), p.getUserKey()} );
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i( TAG, "Creating profile DB" );
		createProfileTable( db );
	}
	
	private void createProfileTable(SQLiteDatabase db) {
		Log.i( TAG, "Creating profile table" );
		db.execSQL( createProfileTableSql );
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
