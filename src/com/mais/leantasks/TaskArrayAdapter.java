package com.mais.leantasks;

import java.util.List;
import java.util.logging.Logger;

import android.content.Context;
import android.graphics.Paint;
import android.opengl.Visibility;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.mais.leantasks.model.Task;
import com.mais.leantasks.sql.Table;

public class TaskArrayAdapter extends ArrayAdapter<Task> {

	private int resource;
	private Logger log = Logger.getLogger("TaskArrayAdapter");
	
	public TaskArrayAdapter(Context context, int textViewResourceId, List<Task> objects) {
		super(context, textViewResourceId, objects);
		this.resource = textViewResourceId;
	}
	
	@Override
	public View getView(int position, View convertView, final ViewGroup parent) {
		
	    LinearLayout taskView;
	    final Task task = getItem(position);
	    
	    final String taskText = task.getText();
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
	    taskCheckBox.setOnClickListener(new OnTaskCheckListener(this, task));
	    
	    final EditText editText = (EditText)taskView.findViewById(R.id.task_edit_text);
	    editText.setText(formatTaskText(taskText));
	    final ImageButton removeButton = (ImageButton) taskView.findViewById(R.id.button_remove);
	    removeButton.setOnClickListener(new OnTaskDeleteListener(this, task));
	    
	    final ImageButton confirmButton = (ImageButton) taskView.findViewById(R.id.button_confirm);
	    
	    editText.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){
					EditText focusedEditText = (EditText) v;
					Editable htmlText = focusedEditText.getText();
					String newText = Html.fromHtml(htmlText.toString()).toString();
					focusedEditText.setText(newText);
					removeButton.setVisibility(View.GONE);
		            confirmButton.setVisibility(View.VISIBLE);
		            log.info("remove removed");
				} else {
					log.info("confirm removed");
					removeButton.setVisibility(View.VISIBLE);
		            confirmButton.setVisibility(View.GONE);
				}
			}
		});
	    
//	    editText.addTextChangedListener(new TextWatcher(){
//	        public void afterTextChanged(Editable s) {
//	        	if(editText.isFocused()){
//		            
//	        	}
//	        }
//	        public void beforeTextChanged(CharSequence s, int start, int count, int after){ }
//	        public void onTextChanged(CharSequence s, int start, int before, int count){}
//	    }); 
	    
//	    editText.setOnEditorActionListener(new OnEditorActionListener() {
//			
//			@Override
//			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//				if (actionId == EditorInfo.IME_ACTION_SEARCH ||
//			            actionId == EditorInfo.IME_ACTION_DONE ||
//			            event.getAction() == KeyEvent.ACTION_DOWN &&
//			            event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
//					
//		        	v.setVisibility(View.GONE);
//					ViewGroup row = (ViewGroup) v.getParent();
//					for (int itemPos = 0; itemPos < row.getChildCount(); itemPos++) {
//					    View view = row.getChildAt(itemPos);
//					    log.info("focusChanged: "+itemPos);
//					    if (view instanceof TextView) {
//					         view.setVisibility(View.VISIBLE);
//					    }
//					}
//					
//					return true; // consume.
//			    }
//			    return false; // pass on to other listeners. 
//			}
//		});
//	    
//	    TextView showText = (TextView) taskView.findViewById(R.id.task_show_text);
//	    showText.setText(taskText);
//	    showText.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				v.setVisibility(View.GONE);
//				ViewGroup row = (ViewGroup) v.getParent();
//				for (int itemPos = 0; itemPos < row.getChildCount(); itemPos++) {
//					log.info("clicked: "+itemPos);
//				    View view = row.getChildAt(itemPos);
//				    log.info(view.getClass().toString());
//				    if (view instanceof EditText) {
//				    	log.info("LAL");
//				        view.setVisibility(View.VISIBLE);
//				        view.requestFocus();
//				    }
//				}
//			}
//		});
	    
	    return taskView;
	}
	
	private Spanned formatTaskText(String taskText){
		String taskTextArray[] = taskText.split(" ", 2);
	    String firstWord;
	    String theRest;
	    
	    if(taskTextArray.length == 0){
	    	firstWord = "";
	    	theRest = "";
	    } else if(taskTextArray.length == 1){
	    	firstWord = taskTextArray[0];
	    	theRest = "";
	    } else {
	    	firstWord = taskTextArray[0];
		    theRest = taskTextArray[1];
	    }
	    
	    return Html.fromHtml("<b>"+firstWord+"</b> "+theRest);
	    
	}
	
	private class OnTaskCheckListener implements OnClickListener {

		private TaskArrayAdapter adapter;
		private Task task;
		
		public OnTaskCheckListener(TaskArrayAdapter adapter, Task task){
			this.task = task;
			this.adapter = adapter;
		}
		
		@Override
		public void onClick(View v) {
			CheckBox checkBox = (CheckBox)v;
			task.setChecked(checkBox.isChecked());
			Table.getInstance(v.getContext()).tasks.update(task);
		}
		
	}
	
	private class OnTaskDeleteListener implements OnClickListener {

		private TaskArrayAdapter adapter;
		private Task task;
		
		public OnTaskDeleteListener(TaskArrayAdapter adapter, Task task){
			this.task = task;
			this.adapter = adapter;
		}
		
		@Override
		public void onClick(View v) {
			Table.getInstance(v.getContext()).tasks.delete(task);
			adapter.remove(task);
			adapter.notifyDataSetChanged();
		}
		
	}
		
}
