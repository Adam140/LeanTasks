package com.mais.leantasks.asyncTask;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ProgressBar;

import com.mais.leantasks.http.WebAPI;
import com.mais.leantasks.model.Task;

public class GetTasksTask extends AsyncTask<String, Integer, List<Task>> {

	private ProgressBar progressBar;
	private Activity activity;
	private List<Task> tasks;
	
	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public GetTasksTask(ProgressBar progressBar, Activity activity) {
		super();
		this.progressBar = progressBar;
		this.activity = activity;
	}
	
	@Override
	protected List<Task> doInBackground(String... params) {		
		String login = params[0];
		String password = params[1];
		String date = params[2];
		
		List<Task> result = new ArrayList<Task>(); 
		
		try {
			result = WebAPI.getAllTasks(login, password, date);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	protected void onPostExecute(List<Task> result) {
		setTasks(result);
		progressBar.setVisibility(ProgressBar.INVISIBLE);
	}

	@Override
	protected void onPreExecute() {
		progressBar.setVisibility(ProgressBar.VISIBLE);
	}

//	@Override
//	protected void onProgressUpdate(Integer... values) {
//		String text = activity.getResources().getString(values[0]);
//		textInfo.setText(text);
//	}
	
}
