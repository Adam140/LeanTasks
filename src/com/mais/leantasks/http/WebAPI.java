package com.mais.leantasks.http;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.mais.leantasks.model.Task;

public class WebAPI {
	static final String ADRESS = "http://app-serviceleantasks.rhcloud.com/";

	static final String USER_EXISTS = ADRESS + "api/user/exists/"; // GET /api/user/exists/{username}
	static final String REGISTER = ADRESS + "api/user/register"; // POST /api/user/register
	static final String AUTH = ADRESS + "api/user/auth/"; // GET api/user/auth/{username}/{password}
	static final String GET_ALL = ADRESS + "api/task/getAll/"; // GET /api/task/getAll/{username}/{hash}/{date}
	static final String SYNC = ADRESS + "api/task/sync"; // POST /api/task/sync

	/**
	 * Check if a user defined by the {username} already exists. Returns true if
	 * exists.
	 * 
	 * @param username
	 * @return true/false
	 * @throws Exception
	 */
	public static boolean isExists(String username) throws Exception {
		if (username == null || "".equals(username))
			throw new Exception("Invalid argument");

		String URL = USER_EXISTS + username;
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(URL);
		HttpResponse responseGet = client.execute(get);
		HttpEntity resEntityGet = responseGet.getEntity();

		String result = EntityUtils.toString(resEntityGet);

		if ("true".equals(result))
			return true;
		else if ("false".equalsIgnoreCase(result))
			return false;
		else
			throw new Exception("Not expected value returned from web service");
	}

	/**
	 * Register new user. - returns false if username is already in use. -
	 * returns true Created if the account is succesfully registered.
	 * 
	 * @param login
	 *            string
	 * @param password
	 *            string
	 * @return true/false
	 * @throws Exception
	 */
	public static boolean createUser(String login, String password) throws Exception {
		JSONObject json = new JSONObject();
		json.put("username", login);
		json.put("password", password);

		String URL = REGISTER;
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(URL);

		StringEntity se = new StringEntity(json.toString());
		httpost.setEntity(se);

		httpost.setHeader("Accept", "application/json");
		httpost.setHeader("Content-type", "application/json");

		// Handles what is returned from the page
		HttpResponse response = httpclient.execute(httpost);

		if (response.getStatusLine().getStatusCode() == 201)
			return true;
		else
			return false;
	}
	
	/**
	 * 
	 * @param login
	 * @param hash
	 * @param date
	 * @return List of the tasks obtained from the webservice.
	 */
	public static List<Task> getAllTasks(String login, String hash, String date) throws Exception {
		
		String URL = GET_ALL + login + "/" + hash + "/" + date;
		
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(URL);
		HttpResponse responseGet = client.execute(get);
		HttpEntity resEntityGet = responseGet.getEntity();
		
		
		return new ArrayList<Task>();
	}

}
