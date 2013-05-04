package com.mais.leantasks;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.mais.leantasks.model.Task;
import com.mais.leantasks.sql.Table;
import com.mais.leantasks.sql.Tasks;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity {

	private Table table;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar ab = getActionBar();
		ab.setDisplayUseLogoEnabled(true);
		ab.setDisplayShowTitleEnabled(false);
		setContentView(R.layout.activity_main);
		
		table = new Table(this);

		List<Task> values = table.tasks.selectAll();
		
		ListView list = (ListView) findViewById(R.id.listViewTasks);
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
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
			task.setText(text.getText().toString());
			task.setCreatedDate(sdf.format(new Date()));
			task.setUpdatedDate(sdf.format(new Date()));
			task.setArchived(false);
			task.setChecked(false);
			
			long id = table.tasks.create(task);
			task.setId(id);
			
			break;
		}
	}

}
