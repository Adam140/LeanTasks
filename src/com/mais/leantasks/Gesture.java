package com.mais.leantasks;

import android.graphics.Paint;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.widget.CheckBox;
import android.widget.ListView;

import com.mais.leantasks.model.Task;
import com.mais.leantasks.sql.Table;

public class Gesture extends SimpleOnGestureListener
 {
    private Table table;
    private ListView listView;
    private final static int PATH_LENGHT = 200;
    

    public Gesture()
    {
        super();
    }
    
    public Gesture(ListView listView, Table table)
    {
    	super();
    	this.table = table;
    	this.listView = listView;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
    	if(Math.abs(e2.getX() - e1.getX()) > PATH_LENGHT){
    		int id = listView.pointToPosition((int) e1.getX(), (int) e1.getY());
    		if(id != -1)
    		{
    			Task task = (Task)listView.getItemAtPosition(id);
	    		CheckBox checkBox = (CheckBox) listView.getChildAt(id).findViewById(R.id.task_check_box);
	    		boolean archived = task.isArchived();
	    		
	    		if(archived)
	    		{
	    			checkBox.setPaintFlags(checkBox.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
	    		}
	    		else
	    		{
	    			checkBox.setPaintFlags(checkBox.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
	    		}
	    		
	    		task.setArchived(!archived);
	    		table.tasks.update(task);
	    		
//	    		TaskArrayAdapter adapter = (TaskArrayAdapter) listView.getAdapter();
//	    		table.tasks.delete((Task) task);
//	    		adapter.remove(task);
	    		
	    		return true;
    		}
    		
    	}
        return super.onFling(e1, e2, velocityX, velocityY);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {

        return super.onSingleTapConfirmed(e);
    }

}
