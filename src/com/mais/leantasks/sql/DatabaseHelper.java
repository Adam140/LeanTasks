package com.mais.leantasks.sql;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

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
	public static final String TASK_DATE = "task_date";
	public static final String TASK_CHECKED = "task_checked";
	public static final String TASK_ARCHIVED = "task_archived";
	
	public static final String USR_ID = "usr_ID";
	public static final String USR_NAME = "usr_name";
	public static final String USR_PASSWORD = "usr_password";

	public DatabaseHelper(Context context) {
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
				+ TASK_DATE + " TEXT,"
				+ TASK_CHECKED + " INTEGER," 
				+ TASK_ARCHIVED + " INTEGER)";
		db.execSQL(CREATE_TASKS_TABLE);
		
		String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
				+ USR_ID + " INTEGER PRIMARY KEY,"
				+ USR_NAME + " TEXT,"
				+ USR_PASSWORD + " TEXT)";
		db.execSQL(CREATE_USERS_TABLE);
		}
		catch(SQLException e)
		{
			System.out.println("OnCreate exception \n" + e);
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
