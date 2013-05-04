package com.mais.leantasks.model;

import java.util.List;

public class Task {

	private long id;
	private String text;
	private String createdDate;
	private String updatedDate;
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

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public int getCheckedInt() {
		if (checked)
			return 1;
		else
			return 0;
	}

	public void setChecked(int checked) {
		this.checked = checked == 1 ? true : false;
	}

	public boolean isArchived() {
		return archived;
	}

	public void setArchived(boolean archived) {
		this.archived = archived;
	}

	public int getArchivedInt() {
		if (archived)
			return 1;
		else
			return 0;
	}

	public void setArchived(int archived) {
		this.archived = archived == 1 ? true : false;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", text=" + text + ", created_date=" + createdDate + ", updated_date=" + updatedDate
				+ ", checked=" + checked + ", archived=" + archived + "]";
	}

	public static String[] listToArray(List<Task> list) {
		String[] values = new String[list.size()];

		for (int i = 0; i < list.size(); i++) {
			Task task = list.get(i);

			values[i] = task.getText();
		}

		return values;
	}

}
