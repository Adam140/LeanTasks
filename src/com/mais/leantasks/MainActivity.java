package com.mais.leantasks;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;

import com.mais.leantasks.model.Task;
import com.mais.leantasks.sql.Table;

public class MainActivity extends Activity {

	private Table table;
	private ListView listView;
	private List<Task> tasks;
	private TaskArrayAdapter taskArrayAdapter;

	private Task currentTask;
	private EditText editTextTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar ab = getActionBar();
		ab.setDisplayUseLogoEnabled(true);
		ab.setDisplayShowTitleEnabled(false);
		setContentView(R.layout.activity_main);

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
	
	@Override
		public void onBackPressed() {
	}

}
