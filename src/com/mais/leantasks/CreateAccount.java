package com.mais.leantasks;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mais.leantasks.asyncTask.RegisterTask;

public class CreateAccount extends Activity{
	
	private TextView textInfo;
	private ProgressBar progressBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar ab = getActionBar();
		ab.setDisplayUseLogoEnabled(true);
		ab.setDisplayShowTitleEnabled(false);
		setContentView(R.layout.activity_create_account);
		
		progressBar = (ProgressBar)findViewById(R.id.progressBar);
		
		textInfo = (TextView)findViewById(R.id.editTextInfo);
		textInfo.setText("");
		textInfo.setGravity(Gravity.CENTER);
		textInfo.setTextColor(Color.RED);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onClick(View view) {
		switch (view.getId()) {				
			case R.id.buttonCreateAccount:
				// TODO : link with the web service for user creation into the DB
				if(!isNetworkAvailable())
					textInfo.setText("Turn on your network connection");
				else
				{
					String login = ((EditText) findViewById(R.id.editTextLogin)).getText().toString();
					String password = ((EditText) findViewById(R.id.editTextPassword)).getText().toString();
					
					RegisterTask register = new RegisterTask(progressBar, textInfo, this);
					register.execute(login, password);
				}
				break;
		}
	}
	
	public boolean isNetworkAvailable() {
	    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo networkInfo = cm.getActiveNetworkInfo();
	    // if no network is available networkInfo will be null
	    // otherwise check if we are connected
	    if (networkInfo != null && networkInfo.isConnected()) {
	        return true;
	    }
	    return false;
	} 
	
}
