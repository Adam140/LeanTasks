package com.mais.leantasks.asyncTask;

import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;

import com.mais.leantasks.http.WebAPI;
import com.mais.leantasks.model.Task;
import com.mais.leantasks.sql.Table;

public class SyncTasksTask extends AsyncTask<String, Integer, Void> {

	private Activity activity;
	
	public SyncTasksTask(Activity activity) {
		this.activity = activity;
	}
	
	@Override
	protected Void doInBackground(String... params) {
		
		String username = params[0];
		String hash = params[1];
		
		Table table = Table.getInstance(activity);
		List<Task> tasks = table.tasks.selectAll();
		
		try {
			WebAPI.syncTasks(username, hash, tasks);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
