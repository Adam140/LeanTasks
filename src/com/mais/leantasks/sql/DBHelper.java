package com.mais.leantasks.sql;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

	private final static String TAG = DBHelper.class.getName();
	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "leanTasks.db";

	// LeanTasks table name
	public static final String TABLE_TASKS = "tasks";
	public static final String TABLE_USERS = "users";

	// Table Columns names
	public static final String TASK_ID = "task_ID";
	public static final String TASK_TEXT = "task_text";
	public static final String TASK_CREATED_DATE = "task_created_date";
	public static final String TASK_UPDATED_DATE = "task_updated_date";
	public static final String TASK_CHECKED = "task_checked";
	public static final String TASK_ARCHIVED = "task_archived";
	public static final String TASK_USERNAME = "task_username";
	
	public static final String USR_ID = "usr_ID";
	public static final String USR_NAME = "usr_name";
	public static final String USR_PASSWORD = "usr_password";
	public static final String USR_LOGGED_IN = "usr_logged_in";
	public static final String USR_LAST_SYNC_DATE = "usr_last_sync_date";


	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		try
		{
		String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASKS + "("
				+ TASK_ID + " INTEGER PRIMARY KEY,"
				+ TASK_TEXT + " TEXT,"
				+ TASK_CREATED_DATE + " TEXT,"
				+ TASK_UPDATED_DATE + " TEXT,"
				+ TASK_USERNAME + " TEXT,"
				+ TASK_CHECKED + " INTEGER," 
				+ TASK_ARCHIVED + " INTEGER)";
		db.execSQL(CREATE_TASKS_TABLE);
		
		String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
				+ USR_ID + " INTEGER PRIMARY KEY,"
				+ USR_NAME + " TEXT,"
				+ USR_PASSWORD + " TEXT," 
				+ USR_LOGGED_IN + " INTEGER,"
				+ USR_LAST_SYNC_DATE + " TEXT)";
		db.execSQL(CREATE_USERS_TABLE);
		}
		catch(SQLException e)
		{
			Log.e(TAG, "OnCreate exception \n" + e);
		}
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

		// Create tables again
		onCreate(db);
	}
}
