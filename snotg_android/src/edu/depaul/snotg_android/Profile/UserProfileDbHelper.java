package edu.depaul.snotg_android.Profile;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class UserProfileDbHelper extends SQLiteOpenHelper {
	
	private static final String TAG = UserProfileDbHelper.class.getSimpleName();
	private static final String PROFILE_TABLE_NAME = "PROFILE";

	public UserProfileDbHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i(TAG, "Creating profile DB");
		createProfileTable(db);
	}
	
	private void createProfileTable(SQLiteDatabase db) {
		String createSql = "CREATE TABLE " + PROFILE_TABLE_NAME + "(" +
							" _id INTEGER PRIMARY KEY, " +
							" name TEXT, " +
							" description TEXT, " +
							" shout TEXT);";
		db.execSQL(createSql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i(TAG, "Upgrading profile DB");
		dropProfileTable(db);
	}
	
	private void dropProfileTable(SQLiteDatabase db) {
		String dropSql = "DROP TABLE IF EXISTS " + PROFILE_TABLE_NAME;
		db.execSQL(dropSql);
	}

}
