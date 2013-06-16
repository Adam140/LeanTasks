package com.mais.leantasks;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.mais.leantasks.asyncTask.GetTasksTask;
import com.mais.leantasks.http.WebAPI;
import com.mais.leantasks.model.Task;
import com.mais.leantasks.security.Encrypt;
import com.mais.leantasks.sql.Table;

public class MainActivity extends Activity {

	private Table table;
	private ListView listView;
	private List<Task> tasks;
	private TaskArrayAdapter taskArrayAdapter;
	private ProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar ab = getActionBar();
		ab.setDisplayUseLogoEnabled(true);
		ab.setDisplayShowTitleEnabled(false);
		setContentView(R.layout.activity_main);

		// won't need to be called every time
//		getNewTasksFromWS();
		
		table = Table.getInstance(this);
		tasks = table.tasks.selectAll();
		listView = (ListView) findViewById(R.id.list_view_tasks);
		
		taskArrayAdapter = new TaskArrayAdapter(this, R.layout.task, tasks);

		//listView.setClickable(true);
		//listView.setItemsCanFocus(true);
		//listView.setOnItemClickListener(clickOnTask);
		// listTasks.setOnItemLongClickListener(editOnTask);
		listView.setAdapter(taskArrayAdapter);

		taskArrayAdapter.notifyDataSetChanged();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * Adding new task
	 * 
	 * @param view
	 */
	@SuppressLint("SimpleDateFormat")
	public void onClick(View view) {

		switch (view.getId()) {
		case R.id.button_add:
			Task task = new Task();
			EditText text = (EditText) findViewById(R.id.edit_text_task);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
			task.setText(text.getText().toString());
			task.setCreatedDate(sdf.format(new Date()));
			task.setUpdatedDate(sdf.format(new Date()));
			task.setArchived(false);
			task.setChecked(false);

			long id = table.tasks.create(task);
			task.setId(id);

			this.tasks.add(task);
			text.setText("");
			text.clearFocus();
			InputMethodManager inputManager = (InputMethodManager) this
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputManager.hideSoftInputFromWindow(this.getCurrentFocus()
					.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

			break;
		}
	}
	
	/**
	 * This function will be used to invoke task retrieval from the cloud.
	 * TODO Use real user data and store tasks in the DB.
	 */
	public void getNewTasksFromWS() {
		
		if (WebAPI.isNetworkAvailable(this)) {
		
			progressBar = (ProgressBar)findViewById(R.id.progressBar);
			
			table = Table.getInstance(this);
			
			// mock data for testing purposes
			String name = "admin";
			String pass = Encrypt.md5("admin");
			String date = "2013-06-01-12-00";
			
			GetTasksTask getTasks = new GetTasksTask(progressBar, this);
			getTasks.execute(name, pass, date);
			
			tasks = getTasks.getTasks();
		} else {
			System.out.println("nie ma");
		}
		
	}

//	/**
//	 * Checked or uncheck task
//	 */
//	private OnItemClickListener clickOnTask = new OnItemClickListener() {
//		@Override
//		public void onItemClick(AdapterView<?> tasks, View view, int position,
//				long id) {
//			Task task = (Task) tasks.getItemAtPosition(position);
//			boolean checked = !task.isChecked();
//			((CheckBox) view.findViewById(R.id.task_check_box))
//					.setChecked(checked);
//			task.setChecked(checked);
//			task.setUpdatedDate(new Date().toString());
//			table.tasks.update(task);
//		}
//	};
	
//	/**
//	 * Modifying existing task
//	 */
//	private OnItemLongClickListener editOnTask = new OnItemLongClickListener() {
//		@Override
//		public boolean onItemLongClick(AdapterView<?> tasks, View view,
//				int position, long id) {
//			currentTask = (Task) tasks.getItemAtPosition(position);
//			showDialog(DIALOG_EDIT_TASK);
//			return true;
//		}
//	};

//	@Override
//	protected void onPrepareDialog(int id, Dialog dialog) {
//		if (currentTask != null) {
//			if (editTextTask == null)
//				editTextTask = (EditText) dialog
//						.findViewById(R.id.edit_task_field);
//			editTextTask.setText(currentTask.getText());
//		}
//	}
//
//	private DialogInterface.OnClickListener dialogButtonClick = new DialogInterface.OnClickListener() {
//		@Override
//		public void onClick(DialogInterface dialog, int which) {
//			if (which == dialog.BUTTON_POSITIVE) {
//				if (currentTask != null && editTextTask != null){
//					currentTask.setText(editTextTask.getText().toString());
//					currentTask.setUpdatedDate(new Date().toString());
//					table.tasks.update(currentTask);
//					taskArrayAdapter.notifyDataSetChanged();
//
//				}
//			}
//		}
//	};
	
}
