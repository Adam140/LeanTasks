package com.mais.leantasks.model;

import java.util.Date;

public class Task {

	private long id;
	private String text;
	private Date date;
	private boolean checked;
	private boolean archived;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getDateString() {
		return date.toString();
	}
	public void setDate(String date) {
		this.date = new Date(date);
	}
	public boolean getChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public int getCheckedInt() {
		if(checked)
			return 1;
		else
			return 0;
	}
	public void setChecked(int checked) {
		this.checked = checked == 1 ? true : false;
	}
	public boolean getArchived() {
		return archived;
	}
	public void setArchived(boolean archived) {
		this.archived = archived;
	}
	public int getArchivedInt() {
		if(archived)
			return 1;
		else
			return 0;
	}
	public void setArchived(int archived) {
		this.archived = archived == 1 ? true : false;
	}
	
	@Override
	public String toString() {
		return "Task [id=" + id + ", text=" + text + ", date=" + date
				+ ", checked=" + checked + ", archived=" + archived + "]";
	}
	
	
}
