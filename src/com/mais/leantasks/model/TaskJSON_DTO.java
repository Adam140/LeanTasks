package com.mais.leantasks.model;

import com.google.gson.annotations.SerializedName;

/**
 * Task Data Transfer Object with dates formatted as integer arrays.
 * @author Kuba
 */
public class TaskJSON_DTO {
	@SerializedName("id")
	private long id;
	
	@SerializedName("text")
	private String text = "";
	
	@SerializedName("user")
	private InnerUser user;
	
	@SerializedName("createdDate")
	private int[] createdDate;
	
	@SerializedName("updatedDate")
	private int[] updatedDate;
	
	@SerializedName("checked")
	private boolean checked;
	
	@SerializedName("archived")
	private boolean archived;
	
	public TaskJSON_DTO(Task task, String username) {
		id = task.getId();
		text = task.getText();
		user = new InnerUser(username);
		
		createdDate = convertDateToIntArr(task.getCreatedDate());
		updatedDate = convertDateToIntArr(task.getUpdatedDate());
		
		checked = task.isChecked();
		archived = task.isArchived();
	}
	
	private int[] convertDateToIntArr(String date) {
		String[] divided = date.replaceAll("\\-", " ").split(" ");
		int[] converted = new int[divided.length+1];
		for(int i = 0; i < divided.length; i++) {
			converted[i] = Integer.parseInt(divided[i]);
		}
		return converted;
	}
	
	@Override
	public String toString() {
		return "Task [id=" + id + ", text=" + text + ", created_date=" + createdDate + ", updated_date=" + updatedDate
				+ ", checked=" + checked + ", archived=" + archived + "]";
	}
	
	public class InnerUser {
		
		public InnerUser(String username) {
			this.username = username;
		}
		
		@SerializedName("username")
		String username;
	}
	
}
