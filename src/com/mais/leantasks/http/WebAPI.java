package com.mais.leantasks.http;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.mais.leantasks.model.SyncTasksList;
import com.mais.leantasks.model.Task;
import com.mais.leantasks.model.TaskJSON_DTO;

public class WebAPI {
	static final String ADRESS = "http://app-serviceleantasks.rhcloud.com/";

	static final String USER_EXISTS = ADRESS + "api/user/exists/"; // GET
																	// /api/user/exists/{username}
	static final String REGISTER = ADRESS + "api/user/register"; // POST
																	// /api/user/register
	static final String AUTH = ADRESS + "api/user/auth/"; // GET
															// api/user/auth/{username}/{password}
	static final String GET_ALL = ADRESS + "api/task/getAll/"; // GET
																// /api/task/getAll/{username}/{hash}/{date}
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

		if (resEntityGet != null) {
			String retSrc = EntityUtils.toString(resEntityGet);
			Gson gson = new Gson();
			
			JsonParser parser = new JsonParser();
		    JsonArray Jarray = parser.parse(retSrc).getAsJsonArray();

		    ArrayList<Task> lcs = new ArrayList<Task>();

		    for(JsonElement obj : Jarray )
		    {
		        Task task = gson.fromJson( obj , Task.class);
		        lcs.add(task);
		    }
			
//			Type typeOfT = new TypeToken<List<Task>>(){}.getType();
//			Task[] tasks = gson.fromJson(retSrc, Task[].class);
		}
		
		
//		if (resEntityGet != null) {
//			String retSrc = EntityUtils.toString(resEntityGet);
//			// parsing JSON
//			JSONObject result = new JSONObject(retSrc); // Convert String to
//														// JSON Object
//
//			JSONArray tokenList = result.getJSONArray("tasks");
//		}

		return new ArrayList<Task>();
	}

	/**
	 * Sends the tasks list to the WS for synchronization purposes.
	 * @param username
	 * @param hash
	 * @param tasks
	 * @return boolean True on success, False on failure.
	 * @throws Exception
	 */
	public static boolean syncTasks(String username, String hash, List<Task> tasks) throws Exception {
		String URL = SYNC;

		Gson gson = new Gson();
		SyncTasksList sct = new SyncTasksList();
		sct.setUsername(username);
		sct.setHash(hash);
		sct.setTasks(tasks);
		
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(URL);
		String json = gson.toJson(sct);

		StringEntity se = new StringEntity(json);
		httpost.setEntity(se);

		httpost.setHeader("Accept", "application/json");
		httpost.setHeader("Content-type", "application/json");

		// Handles what is returned from the page
		HttpResponse response = httpclient.execute(httpost);

		if (response.getStatusLine().getStatusCode() == 200)
			return true;
		else
			return false;
	}

	/**
	 * Checks if a network is available. Moved from CreateAccount activity, as
	 * it might be of wider usefulness.
	 * 
	 * @param activity
	 * @return
	 */
	public static boolean isNetworkAvailable(Activity activity) {
		ConnectivityManager cm = (ConnectivityManager) activity
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		// if no network is available networkInfo will be null
		// otherwise check if we are connected
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		}
		return false;
	}

}
