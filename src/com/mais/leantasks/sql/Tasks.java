package com.mais.leantasks.sql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.mais.leantasks.model.Task;

/**
 * Class implements all CRUD action on table TASKS 
 * @author Adam
 *
 */
public class Tasks implements CRUD {

	private final static String TAG = "DBOperation: " + Tasks.class.getName();
	
	private SQLiteDatabase database;

	private String[] allColumns = { DBHelper.TASK_ID, DBHelper.TASK_TEXT,
			DBHelper.TASK_CREATED_DATE, DBHelper.TASK_UPDATED_DATE,
			DBHelper.TASK_CHECKED, DBHelper.TASK_ARCHIVED };


	public Tasks(SQLiteDatabase database)
	{
		this.database = database;
	}
	/**
	 * INSERT INTO ...
	 * @param object Task
	 * @return ID - primary key
	 */
	@Override
	public long create(Object object) {
		Task task = (Task) object;
		ContentValues values = new ContentValues();
		values.put(DBHelper.TASK_TEXT, task.getText());
		values.put(DBHelper.TASK_CREATED_DATE, task.getCreatedDate().toString());
		values.put(DBHelper.TASK_UPDATED_DATE, task.getUpdatedDate().toString());
		values.put(DBHelper.TASK_CHECKED, task.getCheckedInt());
		values.put(DBHelper.TASK_ARCHIVED, task.getArchivedInt());

		long insertId = database.insert(DBHelper.TABLE_TASKS, null, values);

		task.setId(insertId);
		Log.d(TAG, task + " created!");

		return insertId;
	}

	/**
	 * DELETE FROM ... WHERE task_ID = task.id
	 * @param object Task
	 */
	@Override
	public void delete(Object object) {
		Task task = (Task) object;
		long id = task.getId();
		System.out.println("Task deleted with id: " + id);
		database.delete(DBHelper.TABLE_TASKS, DBHelper.TASK_ID + " = " + id, null);
		
		Log.d(TAG, task + " deleted!");
	}
	/**
	 * DELETE FROM ... WHERE task_ID = id
	 * @param id long
	 */
	@Override
	public void delete(long id) {
		Task task = new Task();
		task.setId(id);
		
		delete(task);
	}

	/**
	 * UPDATE ... SET ... WHERE task_ID = task.id
	 * @param object Task
	 */
	@Override
	public void update(Object object) {
		Task task = (Task) object;
		ContentValues values = new ContentValues();
		values.put(DBHelper.TASK_TEXT, task.getText());
		values.put(DBHelper.TASK_UPDATED_DATE, new Date().toString());
		values.put(DBHelper.TASK_CHECKED, task.getCheckedInt());
		values.put(DBHelper.TASK_ARCHIVED, task.getArchivedInt());

		database.update(DBHelper.TABLE_TASKS, values, DBHelper.TASK_ID + " = " + task.getId(), null);
		
		Log.d(TAG, task + " updated!");
	}

	/**
	 * SELECT * FROM TASKS
	 * @return List<Task>
	 */
	@Override
	public List<Task> selectAll() {
		List<Task> tasks = new ArrayList<Task>();

		Cursor cursor = database.query(DBHelper.TABLE_TASKS, allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Task task = cursorToTask(cursor);
			tasks.add(task);
			cursor.moveToNext();
			Log.d(TAG, task + " selected!");
		}
		// Make sure to close the cursor
		cursor.close();
		
		Log.d(TAG, "Selected " + tasks.size() + " tasks");
		return tasks;
	}

	/**
	 * SELECT * FROM TASKS WHERE task_ID = id
	 * @param id ID - primary key
	 * @return Task
	 */
	@Override
	public Task select(long id) {
		Task task = new Task();

		Cursor cursor = database.query(DBHelper.TABLE_TASKS, allColumns,
				DBHelper.TASK_ID + " = " + id, null, null, null, null);

		cursor.moveToFirst();

		while (!cursor.isAfterLast()) {
			task = cursorToTask(cursor);
			cursor.moveToNext();
		}

		cursor.close();
		Log.d(TAG, task + " selected!");

		return task;
	}

	/**
	 * SELECT * FROM TASKS WHERE where
	 * @param where String -  SQL WHERE clause (excluding the WHERE itself).  @see android.database.sqlite.SQLiteDatabase.query
	 * @return List<Task>
	 */
	@Override
	public List<Task> select(String where) {
		Log.d(TAG, "SELECT " + Arrays.toString(allColumns) + " FROM " + DBHelper.TABLE_USERS + " WHERE " + where );

		List<Task> tasks = new ArrayList<Task>();
		Cursor cursor = database.query(DBHelper.TABLE_TASKS, allColumns, where, null, null, null, null); 

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Task task = cursorToTask(cursor);
			tasks.add(task);
			cursor.moveToNext();
			Log.d(TAG, task + " selected!");
		}

		cursor.close();
		Log.d(TAG, "Selected " + tasks.size() + " tasks");
		return tasks;
	}

	/**
	 * Convert cursor to Task object
	 * @param cursor Cursor
	 * @return Task
	 */
	private Task cursorToTask(Cursor cursor) {
		Task Task = new Task();
		Task.setId(cursor.getLong(0));
		Task.setText(cursor.getString(1));
		Task.setCreatedDate(cursor.getString(2));
		Task.setUpdatedDate(cursor.getString(3));
		Task.setChecked(cursor.getInt(4));
		Task.setArchived(cursor.getInt(5));
		return Task;
	}
}
