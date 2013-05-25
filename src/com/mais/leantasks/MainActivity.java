package com.mais.leantasks;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import com.mais.leantasks.model.Task;
import com.mais.leantasks.sql.Table;

public class MainActivity extends Activity{

	static final private int DIALOG_EDIT_TASK = 0;
	private Table table;
	private ListView listTasks;
	private List<Task> tasks;
	private TaskArrayAdapter taskArrayAdapter;

//	private GestureDetector  detector = new GestureDetector (this, new Gesture());
	
	private Task currentTask;
	private EditText editTextTask;
	
	private OnItemClickListener clickOnTask = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> tasks, View view, int position, long id) {
			Task task = (Task) tasks.getItemAtPosition(position);
			boolean checked = !task.isChecked();
			((CheckBox) view.findViewById(R.id.task_check_box)).setChecked(checked);
			task.setChecked(checked);
			task.setUpdatedDate(new Date().toString());
			table.tasks.update(task);
		}
	};
	
	private OnItemLongClickListener editOnTask = new OnItemLongClickListener() {
		@Override
		public boolean onItemLongClick(AdapterView<?> tasks, View view, int position, long id) {
			currentTask= (Task) tasks.getItemAtPosition(position);
			showDialog(DIALOG_EDIT_TASK);
			return true;
		}
	};
	
	private DialogInterface.OnClickListener dialogButtonClick = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			if(which == dialog.BUTTON_POSITIVE)
			{
				if(currentTask != null && editTextTask != null)
				{
					currentTask.setText(editTextTask.getText().toString());
					currentTask.setUpdatedDate(new Date().toString());
					table.tasks.update(currentTask);
				}
			}
		}
	};
	
	private OnTouchListener deleteGesture = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
//			detector.onTouchEvent(event);
			return false;
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar ab = getActionBar();
		ab.setDisplayUseLogoEnabled(true);
		ab.setDisplayShowTitleEnabled(false);
		setContentView(R.layout.activity_main);

		table = new Table(this);

		tasks = table.tasks.selectAll();

		listTasks = (ListView) findViewById(R.id.list_view_tasks);
		taskArrayAdapter = new TaskArrayAdapter(this, R.layout.task, tasks);
		taskArrayAdapter.setTable(table);

		listTasks.setClickable(true);
		listTasks.setItemsCanFocus(true);
		listTasks.setOnItemClickListener(clickOnTask);
		listTasks.setOnItemLongClickListener(editOnTask);
		listTasks.setAdapter(taskArrayAdapter);
		listTasks.setOnTouchListener(deleteGesture);
		
		taskArrayAdapter.notifyDataSetChanged();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

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
			InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			
			break;
		}
	}
	
	private Dialog createCustomAlertDialog() {
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.edit_task, (ViewGroup)findViewById(R.id.dialog_edit_task));
		dialogBuilder.setView(layout);
	    dialogBuilder.setTitle(getResources().getString(R.string.edit_task));
	    dialogBuilder.setNegativeButton(R.string.cancel, dialogButtonClick);
	    dialogBuilder.setPositiveButton(R.string.modify, dialogButtonClick);
	    
		return dialogBuilder.create();
	}
	
	protected Dialog onCreateDialog(int id) {
		return createCustomAlertDialog();
	}
	

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		if(currentTask != null)
		{
			if(editTextTask == null)
				editTextTask = (EditText) dialog.findViewById(R.id.edit_task_field);
		    editTextTask.setText(currentTask.getText());
		}
	}
}
