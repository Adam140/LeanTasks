package com.mais.leantasks.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;


public class SyncTasksList {

	@SerializedName("username")
	private String username;
	
	@SerializedName("hash")
	private String hash;
	
	@SerializedName("tasks")
	private List<TaskJSON_DTO> tasks;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public List<TaskJSON_DTO> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {

		this.tasks = new ArrayList<TaskJSON_DTO>();
		for(Task task : tasks) {
			this.tasks.add(new TaskJSON_DTO(task, getUsername()));
		}
		
	}
	
}
