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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import com.mais.leantasks.model.Task;
import com.mais.leantasks.sql.Table;

import de.timroes.swipetodismiss.SwipeDismissList;
import de.timroes.swipetodismiss.SwipeDismissList.UndoMode;
import de.timroes.swipetodismiss.SwipeDismissList.Undoable;

public class MainActivity extends Activity {

	static final private int DIALOG_EDIT_TASK = 0;
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

		table = new Table(this);
		tasks = table.tasks.selectAll();
		listView = (ListView) findViewById(R.id.list_view_tasks);
		
		taskArrayAdapter = new TaskArrayAdapter(this, R.layout.task, tasks);

		//listView.setClickable(true);
		//listView.setItemsCanFocus(true);
		//listView.setOnItemClickListener(clickOnTask);
		// listTasks.setOnItemLongClickListener(editOnTask);
		listView.setAdapter(taskArrayAdapter);

		taskArrayAdapter.notifyDataSetChanged();
		
		SwipeDismissList.OnDismissCallback callback = new SwipeDismissList.OnDismissCallback() {
		    // Gets called whenever the user deletes an item.
		    public SwipeDismissList.Undoable onDismiss(AbsListView listView, final int position) {
		        // Get your item from the adapter (mAdapter being an adapter for MyItem objects)
		        final Task deletedItem = taskArrayAdapter.getItem(position);
		        // Delete item from adapter
		        taskArrayAdapter.remove(deletedItem);
		        // Return an Undoable implementing every method
		        return new SwipeDismissList.Undoable() {

		            // Method is called when user undoes this deletion
		            public void undo() {
		                // Reinsert item to list
		            	taskArrayAdapter.insert(deletedItem, position);
		            }

		            // Return an undo message for that item
		            public String getTitle() {
		                return deletedItem.toString() + " deleted";
		            }

		            // Called when user cannot undo the action anymore
		            public void discard() {
		                // Use this place to e.g. delete the item from database
		                //finallyDeleteFromSomeStorage(deletedItem);
		            }
		        };
		    }
		};
		
		UndoMode mode = UndoMode.SINGLE_UNDO;
		SwipeDismissList swipeList = new SwipeDismissList(listView, callback, mode);

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
