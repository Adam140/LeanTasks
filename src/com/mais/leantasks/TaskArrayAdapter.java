package com.mais.leantasks;

import java.util.List;

import android.content.Context;
import android.graphics.Paint;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.mais.leantasks.model.Task;

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
	    boolean taskArchived = task.isArchived();
	    if(convertView == null) {
	        taskView = new LinearLayout(getContext());
	        LayoutInflater inflater = 
	            (LayoutInflater)getContext()
	            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        inflater.inflate(resource, taskView, true);
	    } else {
	        taskView = (LinearLayout)convertView;
	    }
	    
	    CheckBox taskCheckBox = (CheckBox)taskView.findViewById(R.id.task_check_box);
	    
	    taskCheckBox.setChecked(taskChecked);
	    if(taskArchived)
	    	taskCheckBox.setPaintFlags(taskCheckBox.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
	    else
	    	taskCheckBox.setPaintFlags(taskCheckBox.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
	    
	    EditText editText = (EditText)taskView.findViewById(R.id.task_edit_text);
	    editText.setText(taskText);
	    
	    return taskView;
	}
		
}
