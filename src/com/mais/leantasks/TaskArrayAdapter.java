package com.mais.leantasks;

import java.util.List;

import android.content.Context;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.mais.leantasks.model.Task;
import com.mais.leantasks.sql.Table;

public class TaskArrayAdapter extends ArrayAdapter<Task> {

	private int resource;
	private Table table;
	private PopupWindow editTaskWindow;
	private View view;

	public TaskArrayAdapter(Context context, int textViewResourceId, List<Task> objects) {
		super(context, textViewResourceId, objects);
		this.resource = textViewResourceId;
	}
	
	@Override
	public View getView(int position, View convertView, final ViewGroup parent) {
	    LinearLayout taskView;
	    final Task task = getItem(position);
	    
	    String taskText = task.getText();
	    boolean taskChecked = task.isChecked();
//	    boolean taskArchived = task.isArchived();
	    if(convertView == null) {
	        taskView = new LinearLayout(getContext());
	        LayoutInflater inflater = 
	            (LayoutInflater)getContext()
	            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        inflater.inflate(resource, taskView, true);
	    } else {
	        taskView = (LinearLayout)convertView;
	    }
	    
	    final CheckBox taskCheckBox = (CheckBox)taskView.findViewById(R.id.task_check_box);
	    
	    taskCheckBox.setText(taskText);
	    taskCheckBox.setChecked(taskChecked);
	    
//	    taskCheckBox.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				boolean checked = taskCheckBox.isChecked();
//				taskCheckBox.setChecked(checked);
//				task.setChecked(checked);
//				table.tasks.update(task);
//			}
//          });
//	    
//	    taskCheckBox.setOnLongClickListener(new OnLongClickListener() {
//			@Override
//			public boolean onLongClick(View v) {
//				// TODO long click to call some activity
//				
//				taskCheckBox.setText("LONG PRESS");
////				Task task = (Task) taskCheckBox.getTag();
//				editTaskWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
//				// temporary---------------- delete only for test
//				table.tasks.delete((Task) task);
//				remove(task);
//				// --------------------------
//				
//				return true; // true when event was handled -> on click shouldn't be trigger
//			}
//		});
	    
	    // TODO maybe some gesture to delete/archive task?
	    
	    return taskView;
	}

	public void setTable(Table table) {
		this.table = table;
	}
		
}
