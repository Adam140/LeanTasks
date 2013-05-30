package com.mais.leantasks.asyncTask;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mais.leantasks.R;
import com.mais.leantasks.http.WebAPI;
import com.mais.leantasks.model.User;
import com.mais.leantasks.security.Encrypt;
import com.mais.leantasks.sql.Table;

public class RegisterTask extends AsyncTask<String, Integer, Void> {

	private ProgressBar progressBar;
	private TextView textInfo;
	private Activity activity;

	public RegisterTask(ProgressBar progressBar, TextView textInfo, Activity activity) {
		super();
		this.progressBar = progressBar;
		this.textInfo = textInfo;
		this.activity = activity;
	}

	@Override
	protected Void doInBackground(String... params) {
		if (params.length < 2) {
			publishProgress(R.string.empty_field);
			return null;
		}

		String login = params[0];
		String password = Encrypt.md5(params[1]);

		if(login == null || password == null)
		{
			publishProgress(R.string.empty_field);
			return null;
		}
		
		try {
			if (WebAPI.isExists(login)) {
				publishProgress(R.string.not_available_login);
				return null;
			}
			
			if(!WebAPI.createUser(login, password))
			{
				publishProgress(R.string.not_available_login);
				return null;
			}
			User user = new User();
			user.setName(login);
			user.setPassword(password);
			user.setLoggedIn(1);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
			user.setLastSync(sdf.format(new Date()));
			
			Table table = Table.getInstance(activity);
			table.users.selectAll();
			table.users.create(user);
			
		} catch (Exception e1) {
			publishProgress(R.string.service_problem);
			return null;
		}

		activity.finish();

		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		progressBar.setVisibility(ProgressBar.INVISIBLE);
	}

	@Override
	protected void onPreExecute() {
		progressBar.setVisibility(ProgressBar.VISIBLE);
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		String text = activity.getResources().getString(values[0]);
		textInfo.setText(text);
	}

}
