package com.mais.leantasks.sql;

import java.util.ArrayList;
import java.util.List;

import com.mais.leantasks.model.Task;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class TasksDataSource {

		  // Database fields
		  private SQLiteDatabase database;
		  private DatabaseHelper dbHelper;
		  private String[] allColumns = { DatabaseHelper.TASK_ID, DatabaseHelper.TASK_TEXT, DatabaseHelper.TASK_DATE, DatabaseHelper.TASK_CHECKED,
			      DatabaseHelper.TASK_ARCHIVED };

		  public TasksDataSource(Context context) {
		    dbHelper = new DatabaseHelper(context);
		  }

		  public void open() throws SQLException {
		    database = dbHelper.getWritableDatabase();
		  }

		  public void close() {
		    dbHelper.close();
		  }
		  
		  public long createTask(Task task)
		  {
			  ContentValues values = new ContentValues();
			  values.put(DatabaseHelper.TASK_TEXT, task.getText());
			  values.put(DatabaseHelper.TASK_DATE, task.getDateString());
			  values.put(DatabaseHelper.TASK_CHECKED, task.getCheckedInt());
			  values.put(DatabaseHelper.TASK_ARCHIVED, task.getArchivedInt());
			  
			  long insertId = database.insert(DatabaseHelper.TABLE_TASKS, null, values);
			  
			  return insertId;
		  }

		  public void deleteTask(Task task) {
		    long id = task.getId();
		    System.out.println("Task deleted with id: " + id);
		    database.delete(DatabaseHelper.TABLE_TASKS, DatabaseHelper.TASK_ID + " = " + id, null);
		  }
		  
		  public void updateTask(Task task)
		  {
			  ContentValues values = new ContentValues();
			  values.put(DatabaseHelper.TASK_TEXT, task.getText());
			  values.put(DatabaseHelper.TASK_DATE, task.getDateString());
			  values.put(DatabaseHelper.TASK_CHECKED, task.getCheckedInt());
			  values.put(DatabaseHelper.TASK_ARCHIVED, task.getArchivedInt());
			  
			  database.update(DatabaseHelper.TABLE_TASKS, values, DatabaseHelper.TASK_ID + " = " + task.getId(), null);
		  }

		  public List<Task> getAllTasks() {
		    List<Task> Tasks = new ArrayList<Task>();

		    Cursor cursor = database.query(DatabaseHelper.TABLE_TASKS,
		        allColumns, null, null, null, null, null);

		    cursor.moveToFirst();
		    while (!cursor.isAfterLast()) {
		      Task Task = cursorToTask(cursor);
		      Tasks.add(Task);
		      cursor.moveToNext();
		    }
		    // Make sure to close the cursor
		    cursor.close();
		    return Tasks;
		  }

		  private Task cursorToTask(Cursor cursor) {
		    Task Task = new Task();
		    Task.setId(cursor.getLong(0));
		    Task.setText(cursor.getString(1));
		    Task.setDate(cursor.getString(2));
		    Task.setChecked(cursor.getInt(3));
		    Task.setArchived(cursor.getInt(2));
		    return Task;
		  }
}
