package com.mais.leantasks;

import java.util.Date;
import java.util.List;

import com.mais.leantasks.model.Task;
import com.mais.leantasks.sql.TasksDataSource;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity {

	private TasksDataSource taskDataSource;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar ab = getActionBar();
		ab.setDisplayUseLogoEnabled(true);
		ab.setDisplayShowTitleEnabled(false);
		setContentView(R.layout.activity_main);
		
		taskDataSource = new TasksDataSource(this);
		taskDataSource.open();

//		List<Task> values = taskDataSource.getAllTasks();
//		ListView list = (ListView) findViewById(R.id.listViewTasks);
//		
//		ArrayAdapter<Task> adapter = new ArrayAdapter<Task>(this,
//				android.R.layout.simple_list_item_1, values);
//		list.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onClick(View view) {

		switch (view.getId()) {
		case R.id.button_add:
			Task task = new Task();
			EditText text = (EditText) findViewById(R.id.edit_text_task);
			task.setText(text.getText().toString());
			task.setDate(new Date());
			task.setArchived(false);
			task.setChecked(false);
			
			long id = taskDataSource.createTask(task);
			task.setId(id);
			
			break;
		}
	}

}
