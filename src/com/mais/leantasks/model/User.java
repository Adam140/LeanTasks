package com.mais.leantasks.model;

public class User {

	private long id;
	private String name;
	private String password;
	private boolean loggedIn;
	private String lastSync;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getLoggedInInt() {
		if (loggedIn)
			return 1;
		else
			return 0;
	}
	
	public boolean isLoggedIn() {
		return loggedIn;
	}
	public void setLoggedIn(int loggedIn) {
		this.loggedIn = loggedIn == 1 ? true : false;;
	}
	public String getLastSync() {
		return lastSync;
	}
	public void setLastSync(String lastSync) {
		this.lastSync = lastSync ;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", password=" + password + ", loggedIn=" + loggedIn + ", lastSync=" + lastSync + "]";
	}

}
