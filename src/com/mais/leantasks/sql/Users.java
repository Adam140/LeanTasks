package com.mais.leantasks.sql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mais.leantasks.model.User;

/**
 * Class implements all CRUD action on table USERS 
 * @author Adam
 *
 */
public class Users implements CRUD{

	private final static String TAG = "DBOperation: " + Users.class.getName();
			
	private SQLiteDatabase database;
	
	private String[] allColumns = { DBHelper.USR_ID, DBHelper.USR_NAME, DBHelper.USR_PASSWORD};

	public Users(SQLiteDatabase database)
	{
		this.database = database;
	}
	/**
	 * INSERT INTO ...
	 * @param user User
	 * @return ID - primary key
	 */
	@Override
	public long create(Object object) {
		User user = (User) object;
		ContentValues values = new ContentValues();
		values.put(DBHelper.USR_NAME, user.getName());
		values.put(DBHelper.USR_PASSWORD, user.getPassword());

		long insertId = database.insert(DBHelper.TABLE_USERS, null, values);
		user.setId(insertId);
		
		Log.d(TAG, user + " created!");

		return insertId;
	}

	/**
	 * DELETE FROM ... WHERE user_ID = user.id
	 * @param user User
	 */
	@Override
	public void delete(Object object) {
		User user = (User) object;
		long id = user.getId();
		database.delete(DBHelper.TABLE_USERS, DBHelper.USR_ID + " = " + id, null);
		
		Log.d(TAG, user + " deleted!");
	}
	
	/**
	 * DELETE FROM ... WHERE user_ID = id
	 * @param user User
	 */
	@Override
	public void delete(long id) {
		User user = new User();
		user.setId(id);
		delete(user);
	}

	/**
	 * UPDATE ... SET ... WHERE user_ID = user.id
	 * @param user User
	 */
	@Override
	public void update(Object object) {
		User user = (User) object;
		ContentValues values = new ContentValues();
		values.put(DBHelper.USR_NAME, user.getName());
		values.put(DBHelper.USR_PASSWORD, user.getPassword());

		database.update(DBHelper.TABLE_USERS, values, DBHelper.USR_ID + " = "
				+ user.getId(), null);
		
		Log.d(TAG, user + " updated!");
	}

	/**
	 * SELECT * FROM USERS
	 * @return List<User>
	 */
	@Override
	public List<User> selectAll() {
		List<User> users = new ArrayList<User>();

		Cursor cursor = database.query(DBHelper.TABLE_USERS, allColumns, null,
				null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			User user = cursorToUser(cursor);
			users.add(user);
			Log.d(TAG, user + " selected!");
			cursor.moveToNext();
		}
		cursor.close();
		
		Log.d(TAG, "Selected " + users.size() + " users");
		return users;
	}

	/**
	 * SELECT * FROM USERS WHERE user_ID = id
	 * @param id ID - primary key
	 * @return User
	 */
	@Override
	public User select(long id) {
		User user = new User();

		Cursor cursor = database.query(DBHelper.TABLE_USERS, allColumns,
				DBHelper.USR_ID + " = " + id, null, null, null, null);

		cursor.moveToFirst();

		while (!cursor.isAfterLast()) {
			user = cursorToUser(cursor);
			cursor.moveToNext();
		}

		cursor.close();

		Log.d(TAG, user + " selected!");
		return user;
	}

	/**
	 * SELECT * FROM USERS WHERE where
	 * @param where String -  SQL WHERE clause (excluding the WHERE itself).  @see android.database.sqlite.SQLiteDatabase.query
	 * @return List<User>
	 */
	@Override
	public List<User> select(String where) {
		Log.d(TAG, "SELECT " + Arrays.toString(allColumns) + " FROM " + DBHelper.TABLE_USERS + " WHERE " + where );
		
		List<User> users = new ArrayList<User>();
		Cursor cursor = database.query(DBHelper.TABLE_USERS, allColumns, where,
				null, null, null, null); 

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			User user = cursorToUser(cursor);
			users.add(user);
			cursor.moveToNext();
			Log.d(TAG, user + " selected!");
		}

		cursor.close();
		Log.d(TAG, "Selected " + users.size() + " users");
		return users;
	}

	/**
	 * Convert cursor to User object
	 * @param cursor Cursor
	 * @return User
	 */
	private User cursorToUser(Cursor cursor) {
		User User = new User();
		User.setId(cursor.getLong(0));
		User.setName(cursor.getString(1));
		User.setPassword(cursor.getString(2));
		return User;
	}
}
