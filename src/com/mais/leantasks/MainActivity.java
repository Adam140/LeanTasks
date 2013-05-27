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
import android.graphics.Paint;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import com.mais.leantasks.model.Task;
import com.mais.leantasks.sql.Table;

public class MainActivity extends Activity implements SensorListener {

	static final private int DIALOG_EDIT_TASK = 0;
	private Table table;
	private ListView listTasks;
	private List<Task> tasks;
	private TaskArrayAdapter taskArrayAdapter;

	private Gesture gesture;

	private Task currentTask;
	private EditText editTextTask;

	private SensorManager sensorMgr;
	private long lastUpdate = -1;
	private float x, y, z;
	private float last_x, last_y, last_z;
	private static final int SHAKE_THRESHOLD = 1200;

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
		gesture = new Gesture(listTasks, table);

		final GestureDetector gestureDetector = new GestureDetector(gesture);

		View.OnTouchListener gestureListener = new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				boolean rv = gestureDetector.onTouchEvent(event);
				if (rv) {
					event.setAction(MotionEvent.ACTION_CANCEL);
				}
				return false;
			}
		};
		listTasks.setOnTouchListener(gestureListener);
		listTasks.setClickable(true);
		listTasks.setItemsCanFocus(true);
		listTasks.setOnItemClickListener(clickOnTask);
		listTasks.setOnItemLongClickListener(editOnTask);
		listTasks.setAdapter(taskArrayAdapter);

		taskArrayAdapter.notifyDataSetChanged();
		
		// start motion detection
		sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
		boolean accelSupported = sensorMgr.registerListener(this,
			SensorManager.SENSOR_ACCELEROMETER,
			SensorManager.SENSOR_DELAY_UI);
	
		if (!accelSupported) {
		    // on accelerometer on this device
		    sensorMgr.unregisterListener(this,
	                SensorManager.SENSOR_ACCELEROMETER);
		}

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
			InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

			break;
		}
	}

	/**
	 * Dialog box for modifying existing task
	 * 
	 * @return
	 */
	private Dialog createCustomAlertDialog() {
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.edit_task, (ViewGroup) findViewById(R.id.dialog_edit_task));
		dialogBuilder.setView(layout);
		dialogBuilder.setTitle(getResources().getString(R.string.edit_task));
		dialogBuilder.setNegativeButton(R.string.cancel, dialogButtonClick);
		dialogBuilder.setPositiveButton(R.string.modify, dialogButtonClick);

		return dialogBuilder.create();
	}

	/**
	 * Checked or uncheck task
	 */
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
	/**
	 * Modifying existing task
	 */
	private OnItemLongClickListener editOnTask = new OnItemLongClickListener() {
		@Override
		public boolean onItemLongClick(AdapterView<?> tasks, View view, int position, long id) {
			currentTask = (Task) tasks.getItemAtPosition(position);
			showDialog(DIALOG_EDIT_TASK);
			return true;
		}
	};

	protected Dialog onCreateDialog(int id) {
		return createCustomAlertDialog();
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		if (currentTask != null) {
			if (editTextTask == null)
				editTextTask = (EditText) dialog.findViewById(R.id.edit_task_field);
			editTextTask.setText(currentTask.getText());
		}
	}

	private DialogInterface.OnClickListener dialogButtonClick = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			if (which == dialog.BUTTON_POSITIVE) {
				if (currentTask != null && editTextTask != null) {
					currentTask.setText(editTextTask.getText().toString());
					currentTask.setUpdatedDate(new Date().toString());
					table.tasks.update(currentTask);
					taskArrayAdapter.notifyDataSetChanged();
					
				}
			}
		}
	};

	@Override
	public void onAccuracyChanged(int sensor, int accuracy) {
	}

	@Override
	public void onSensorChanged(int sensor, float[] values) {
		if (sensor == SensorManager.SENSOR_ACCELEROMETER) {
		    long curTime = System.currentTimeMillis();
		    // only allow one update every 100ms.
		    if ((curTime - lastUpdate) > 100) {
			long diffTime = (curTime - lastUpdate);
			lastUpdate = curTime;
	 
			x = values[SensorManager.DATA_X];
			y = values[SensorManager.DATA_Y];
			z = values[SensorManager.DATA_Z];
	 
			float speed = Math.abs(x+y+z - last_x - last_y - last_z)
	                              / diffTime * 10000;
			if (speed > SHAKE_THRESHOLD) {
				for(int i = 0; i < taskArrayAdapter.getCount(); i++)
				{
					Task task = (Task)taskArrayAdapter.getItem(i);
					CheckBox checkBox = (CheckBox) listTasks.getChildAt(i).findViewById(R.id.task_check_box);
					checkBox.setPaintFlags(checkBox.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
					
					if(task.isArchived())
					{
						taskArrayAdapter.remove(task);
						taskArrayAdapter.notifyDataSetChanged();
			    		table.tasks.delete((Task) task);
			    		i--;
					}
				}
	    		
			}
			last_x = x;
			last_y = y;
			last_z = z;
		    }
		}		
	}

}
