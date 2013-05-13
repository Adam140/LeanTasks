package com.mais.leantasks;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.mais.leantasks.model.Task;
import com.mais.leantasks.sql.Table;

public class TaskArrayAdapter extends ArrayAdapter<Task> {

	private int resource;

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
	    
	    return taskView;
	}
		
}
